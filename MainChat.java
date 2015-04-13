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
public class MainChat extends JFrame implements AddFriendDelegate {
	private final int HEIGHT = 600;
	private final int WIDTH  = 500;
	
	//gui
	private JList<String> friendList;
	private JPanel topPanel;
	private JButton addFriend;
	
	private XMPPConnection userConnection;
	
	public MainChat(XMPPConnection c) {
		super();
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		
		userConnection = c;
		
		topPanel = new JPanel();
		topPanel.setBounds(0, 0, WIDTH, 100);
		add(topPanel);
		
		addFriend = new JButton("add friend");
		addFriend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddFriendFrame addFriend = new AddFriendFrame();
				addFriend.delegate = MainChat.this;
				addFriend.setVisible(true);
			}
			
		});
		topPanel.add(addFriend);
		
		friendList = new JList<String>();
		friendList.setBounds(0,100,100,HEIGHT - 100);
		Roster roster = userConnection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        String[] data = null;
        ArrayList<String> aL = new ArrayList<String>();
        for (RosterEntry entry : entries) {
        	aL.add(entry.getUser());
        }
        
        data = aL.toArray(new String[aL.size()]);
		friendList.setListData(data);
		add(friendList);
		friendList.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String val = MainChat.this.friendList.getSelectedValue();
					System.out.println(val);
					ChatWindow win = new ChatWindow(val, userConnection);
					win.setVisible(true);
				}
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
	

	@Override
	public void addFriend(String userName, String name) {
		Roster friends = userConnection.getRoster();
		try {
			friends.createEntry(userName, name, null);
			System.out.println("Success");
		} catch (XMPPException e) {
			System.err.println("User not found");
		}
	}
	
	
}
