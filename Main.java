

import javax.swing.JFrame;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		XMPPConnection connection = new XMPPConnection("berts-pc");
		try {
			connection.connect();
			connection.login("test", "apple670");
			
			ChatManager chatManager = connection.getChatManager();
			Chat chat = chatManager.createChat("test2@berts-pc", new MessageListener() {
				@Override
				public void processMessage(Chat arg0, Message arg1) {
					System.out.println(arg1.getBody());
				}
				
			});
			
			chat.sendMessage("Sup");
		} catch (XMPPException e) {
			e.getMessage();
			e.printStackTrace();
			System.err.println("Could not connect to server");
		}
		
	}
	
}
