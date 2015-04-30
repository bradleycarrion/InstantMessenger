import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;


@SuppressWarnings("serial")
public class MainChat extends JFrame implements AddFriendDelegate, ChatWindowDelegate {
	private final int HEIGHT = 600;
	private final int WIDTH  = 500;
	
	//gui
	private JPanel mainPanel;
	private JList friendList;
	private JPanel topPanel;
	private JButton addFriend;
	
	//connection to the server
	private XMPPConnection userConnection;
	
	//list of open windows--> do not allow more than one of the same window
	
	private HashMap<String,ChatWindow> chatWindows;
	private Map<String, ImageIcon> imageMap;
	
	
	public MainChat(XMPPConnection c) {
		super();
		//set up frame
		setResizable(false);
		setBounds(0,0,WIDTH,HEIGHT);
		setLayout(null);
		setTitle(c.getUser());
		this.getContentPane().setBackground(Color.BLUE);
		mainPanel = new JPanel() {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon("gonzaga.jpg"));
		contentPane.setLayout(new BorderLayout());
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(new Color(255,255,255,40));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.add(mainPanel);
		
		//init array of open windows
		chatWindows = new HashMap<String,ChatWindow>();
		imageMap    = new HashMap<String, ImageIcon>();
		
		//init connection
		userConnection = c;
		
		//top panel set up
		topPanel = new JPanel() {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		topPanel.setEnabled(true);
		topPanel.setBackground(new Color(255,255,255,40));
		topPanel.setBounds(0, 0, WIDTH, 100);
		mainPanel.add(topPanel);
		
		//add friend button set up
		addFriend = new JButton("add friend") {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor(getBackground());
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		addFriend.setEnabled(true);
		//addFriend.setBackground(new Color(255,255,255,50));
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
		
		//get the users from the server
		Roster roster = userConnection.getRoster();
		roster.addRosterListener(new RosterListener() {
		    public void entriesDeleted(Collection<String> addresses) {}
		    public void entriesUpdated(Collection<String> addresses) {}
		    public void presenceChanged(Presence presence) {
		        updateRoster();
		    }
			@Override
			public void entriesAdded(Collection<String> arg0) {}
		});
		
		//convert to a collection Roster -> Collection
        Collection<RosterEntry> entries = roster.getEntries();
        String[] data = null;
        //array list of the names
        ArrayList<String> aL = new ArrayList<String>();
        

        for (RosterEntry entry : entries) {
        	//add from collection to ArrayList
        	aL.add(entry.getUser());
        	Presence availability = roster.getPresence(entry.getUser()+"/Smack");
        	imageMap.put(entry.getUser(), new ImageIcon(availability.getType().name() + ".png"));
        }
        
        
        //convert ArrayList of names to an Array of strings to be used with JList
        data = aL.toArray(new String[aL.size()]);
        friendList = new JList(data); 
		friendList.setEnabled(true);
        friendList.setCellRenderer(new OnlineListRenderer(imageMap));
        JScrollPane scroll = new JScrollPane(friendList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		scroll.setEnabled(true);
		scroll.setOpaque(false);
		scroll.setBackground(new Color(255,255,255,200));
		scroll.setBounds(0,100,200,HEIGHT - 100);
        //set the data for JList
		mainPanel.add(scroll);
		
		//detects click on name
		friendList.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					final String val = (String)MainChat.this.friendList.getSelectedValue();
					//only allows one open window per friend
					if (SwingUtilities.isLeftMouseButton(arg0) && arg0.getClickCount() == 2) {
						if (!chatWindows.containsKey(val)) {
							ChatWindow win = new ChatWindow(val, userConnection);
							win.setTitle("Chat with: " + val);
							win.delegate = MainChat.this;
							win.setVisible(true);
							chatWindows.put(val + "/Smack" , win);
						
						}
					}
					else if (SwingUtilities.isRightMouseButton(arg0) && val != null) {
						 JPopupMenu menu = new JPopupMenu();
			             JMenuItem item = new JMenuItem("Remove Friend");
			             item.addActionListener(new ActionListener() {
			            	 public void actionPerformed(ActionEvent e) {
			            		 //remove the freind from the roster
			            		 RosterPacket packet = new RosterPacket();
			            		 packet.setType(IQ.Type.SET);
			            		 RosterPacket.Item item  = new RosterPacket.Item(val, null);
			            		 item.setItemType(RosterPacket.ItemType.remove);
			            		 packet.addRosterItem(item);
			            		 userConnection.sendPacket(packet);
			            		 
			            		 //then update the roster
						         removeFriend(val);
			                 }
			             });
			             menu.add(item);
			             menu.show(friendList, 300, 0);
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
		
		//the chat listener,, gets all the messages
		userConnection.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat arg0, boolean arg1) {
				arg0.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Chat arg0, Message arg1) {
						MatrixEncryption m = new MatrixEncryption();
						final String dMessage = m.Decrpyt(arg1.getBody());
						if (!chatWindows.containsKey(arg1.getFrom())) { 
							//if not open a ChatWindow
							ChatWindow win = new ChatWindow(arg1.getFrom(), userConnection);
							win.setTitle("Chat with: " + arg1.getFrom());
							//set the delegate
							//this enables the window to be popped from open windows list
							win.delegate = MainChat.this;
							win.setVisible(true);
							
							win.addMessageToFrame(dMessage, arg1.getFrom());
							chatWindows.put(arg1.getFrom(), win);
						}
						else {
							ChatWindow win = chatWindows.get(arg1.getFrom());
							win.addMessageToFrame(dMessage, arg1.getFrom());
						}
					}
					
				});
			}
    		
    	});
		//listens for a window close to send unavailable presence
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	        	Presence p = new Presence(Presence.Type.unavailable);
	        	userConnection.sendPacket(p);
	        	System.exit(0);
	        }
	    }); 
	}

/**
 * used to remove a friend from the JList
 * called when a friend is removed
 * @param other friend to remove
 */
	private void removeFriend(String other) {
		Roster roster = userConnection.getRoster();
		
		//convert to a collection Roster -> Collection
        Collection<RosterEntry> entries = roster.getEntries();
        String[] data = null;
        //array list of the names
        ArrayList<String> aL = new ArrayList<String>();
        imageMap.clear();
        
        for (RosterEntry entry : entries) {
        	if (entry.getUser() != other) 
        		aL.add(entry.getUser());
        	Presence availability = roster.getPresence(entry.getUser()+"/Smack");
        	imageMap.put(entry.getUser(), new ImageIcon(availability.getType().name() + ".png"));
        }
        data = aL.toArray(new String[aL.size()]);
        //set the data for JList
        
		friendList.setListData(data);
		friendList.setCellRenderer(new OnlineListRenderer(imageMap));
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
		chatWindows.remove(theOther);
	}
	
	/**
	 * delegate method for add friend delegate to update friend list
	 * after a new friend is added
	 */
	@Override
	public void updateRoster() {
		Roster roster = userConnection.getRoster();
		
		//convert to a collection Roster -> Collection
        Collection<RosterEntry> entries = roster.getEntries();
        String[] data = null;
        //array list of the names
        ArrayList<String> aL = new ArrayList<String>();
        imageMap.clear();
        
        for (RosterEntry entry : entries) {
        	aL.add(entry.getUser());
        	Presence availability = roster.getPresence(entry.getUser()+"/Smack");
        	imageMap.put(entry.getUser(), new ImageIcon(availability.getType().name() + ".png"));
        }
        data = aL.toArray(new String[aL.size()]);
        //set the data for JList
        
		friendList.setListData(data);
		friendList.setCellRenderer(new OnlineListRenderer(imageMap));
	}

}
