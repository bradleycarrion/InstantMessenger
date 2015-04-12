import java.util.Scanner;

import javax.swing.JFrame;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


@SuppressWarnings("serial")
public class MainChat extends JFrame {
	private final int HEIGHT = 600;
	private final int WIDTH  = 500;
	
	private XMPPConnection userConnection;
	
	public MainChat(XMPPConnection c) {
		super();
		setBounds(0,0,WIDTH,HEIGHT);
		userConnection = c;
		start();
	}
	
	public void start() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Who do you want to chat: ");
		String other = scan.nextLine();
		Chat currentChat = userConnection.getChatManager().createChat(other + "@tech-pc", new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message arg1) {
				System.out.println(arg1.getBody());
			}
			
		});
		
		while (true) {
			String message = scan.nextLine();
			try {
				currentChat.sendMessage(message);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
