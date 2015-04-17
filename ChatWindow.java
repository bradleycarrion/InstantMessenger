import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public class ChatWindow extends JFrame {
	//dimensions of the window
	private final int HEIGHT = 700;
	private final int WIDTH  = 600;
	
	//the chat with the other user
	private Chat theChat;
	
	//gui 
	private JTextArea textArea;
	private JPanel bottomPanel;
	private JTextArea messageField;
	
	public ChatWindowDelegate delegate;
	
	public ChatWindow(String otherUser, XMPPConnection connection) {
		super();
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		
		//set up the textArea with the scroll
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0,0,WIDTH-20,HEIGHT - 100);
		add(scroll);
		
		
		theChat = connection.getChatManager().createChat(otherUser, null);
		
		//set up the bottom panel
		bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0,HEIGHT-100, WIDTH, 50);
		add(bottomPanel);
		
		//message field set up
		messageField = new JTextArea();
		messageField.setLineWrap(true);
		//scroll that message field sits in set up
		JScrollPane mFieldScroll = new JScrollPane(messageField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mFieldScroll.setBounds(0, 0, bottomPanel.getWidth() - 100, bottomPanel.getHeight());
		messageField.setBounds(0, 0,mFieldScroll.getWidth(), mFieldScroll.getHeight());
		
		//add ENTER listener to send the message
		messageField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!messageField.getText().equals("")) { //don't send if empty message
						try {
							theChat.sendMessage(messageField.getText());
							addMessageToFrame(messageField.getText(), "Me");
							messageField.setText("");
						} catch (XMPPException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
			//unused interface methods
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
		});
		bottomPanel.add(mFieldScroll);
		
		//add window listener to clean up after close
		//call delegate function to remove name from window list
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	        	delegate.didExitWindow(otherUser);
	        }
	    });
		
	}
	
	/**
	 * adds text to the message screen 
	 * @param the message to be added
	 * @param from: the user from whom it was sent
	 */
	public void addMessageToFrame(String message, String from) {
		textArea.append(from + ": " + message + "\n");
	}

}
