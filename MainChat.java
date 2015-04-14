import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


@SuppressWarnings("serial")
public class MainChat extends JFrame implements AddFriendDelegate, ChatWindowDelegate {
	private final int HEIGHT = 600;
	private final int WIDTH  = 500;
	
	//gui
	private JList<String> friendList;
	private JPanel topPanel;
	private JButton addFriend;
	
	//connection to the server
	private XMPPConnection userConnection;
	
	//list of open windows--> do not allow more than one of the same window
	private ArrayList<String> openWindows;
	
	public MainChat(XMPPConnection c) {
		super();
		//set up frame
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		
		//init array of open windows
		openWindows = new ArrayList<String>();
		
		//init connection
		userConnection = c;
		
		//top panel set up
		topPanel = new JPanel();
		topPanel.setBounds(0, 0, WIDTH, 100);
		add(topPanel);
		
		//add friend button set up
		addFriend = new JButton("add friend");
		addFriend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//open up the add friend frame
				AddFriendFrame addFriend = new AddFriendFrame();
				//set the delegate to self->> this enables new friends to be added to the roster
				addFriend.delegate = MainChat.this;
				addFriend.setVisible(true);
			}
			
		});
		topPanel.add(addFriend);
		
		
		//initialize the friend list
		friendList = new JList<String>();
		friendList.setBounds(0,100,100,HEIGHT - 100);
		
		//get the users from the server
		Roster roster = userConnection.getRoster();
		
		//convert to a collection Roster -> Collection
        Collection<RosterEntry> entries = roster.getEntries();
        String[] data = null;
        //array list of the names
        ArrayList<String> aL = new ArrayList<String>();
        

        for (RosterEntry entry : entries) {
        	//add from collection to ArrayList
        	aL.add(entry.getUser());
        	
        	//create new chat with the user so messages can "Pop up"
        	Chat theChat = userConnection.getChatManager().createChat(entry.getUser(), new MessageListener() {
    			@Override
    			public void processMessage(Chat arg0, Message arg1) {
    				//Ensures that another window is not open with this user
    				if (!openWindows.contains(entry.getUser())) { 
    					//if not open a ChatWindow
    					ChatWindow win = new ChatWindow(entry.getUser(), userConnection);
    					//set the delegate
    					//this enables the window to be popped from open windows list
    					win.delegate = MainChat.this;
    					win.setVisible(true);
    					win.addMessageToFrame(arg1.getBody(), entry.getUser());
    					openWindows.add(entry.getUser());
    				}
    			}
    		});
        }
        
        //convert ArrayList of names to an Array of strings to be used with JList
        data = aL.toArray(new String[aL.size()]);
        //set the data for JList
		friendList.setListData(data);
		add(friendList);
		
		//detects click on name
		friendList.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String val = MainChat.this.friendList.getSelectedValue();
					//only allows one open window per friend
					if (!openWindows.contains(val)) {
						ChatWindow win = new ChatWindow(val, userConnection);
						win.delegate = MainChat.this;
						win.setVisible(true);
						openWindows.add(val);
					}
				}
				//unused methods from interface
				@Override
				public void mouseEntered(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseReleased(MouseEvent arg0) {}
		});
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * delegate method for AddFriendFrame
	 * allows new friend to be added to the roster
	 * @param userName-> the userName and name-> the actual name
	 */
	@Override
	public void addFriend(String userName, String name) {
		Roster friends = userConnection.getRoster();
		try {
			friends.createEntry(userName, name, null);
		} catch (XMPPException e) {
			System.err.println("User not found");
		}
	}

	/**
	 * delegate method for ChatWindow
	 * allows the Chat window to be popped from the list
	 */
	@Override
	public void didExitWindow(String theOther) {
		openWindows.remove(theOther);
	}
	
}
