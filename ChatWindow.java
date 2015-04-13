import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public class ChatWindow extends JFrame {
	private final int HEIGHT = 700;
	private final int WIDTH  = 600;
	private Chat theChat;
	private JTextArea textArea;
	private JPanel bottomPanel;
	private JTextField messageField;
	
	public ChatWindow(String otherUser, XMPPConnection connection) {
		super();
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		
		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0,0,WIDTH,HEIGHT - 100);
		add(scroll);
		
		theChat = connection.getChatManager().createChat(otherUser, new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message arg1) {
				if (arg1.getBody() != null) {
					textArea.append(arg0.getParticipant() + " : " + arg1.getBody() + "\n");
				}
			}
		});
		bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0,HEIGHT-100, WIDTH, 100);
		add(bottomPanel);
		
		messageField = new JTextField();
		messageField.setBounds(0, 0, WIDTH - 100, 50);
		
		messageField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!messageField.getText().equals("")) {
						try {
							theChat.sendMessage(messageField.getText());
							textArea.append("Me: " + messageField.getText() + "\n");
							messageField.setText("");
						} catch (XMPPException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
		});
		
		bottomPanel.add(messageField);
		
	}

}
