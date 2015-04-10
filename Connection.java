import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Connection {
	private String userName;
	private String password;
	private XMPPConnection connection;
	private Chat currentChat;
	
	private ConnectionDelegate delegate;
	
	public Connection(String user, String pw, String server) {
		userName = user;
		password = pw;
		connection = new XMPPConnection(server);
	}
	
	public void connect() {
		
		try {
			connection.connect();
			connection.login(userName, password);
		} catch (XMPPException e) {
			System.err.println("Could not connect to server");
		}
	}
	
	public void startChat(String otherUser) {
		ChatManager chatManager = connection.getChatManager();
		currentChat = chatManager.createChat(otherUser + "@berts-pc", new MessageListener() {
			@Override
			public void processMessage(Chat arg0, Message arg1) {
				delegate.handleMessage(arg0.getParticipant() + ":  " + arg1.getBody());
				//System.out.println(arg1.getBody());
			}
			
		});
	}
	
	public void sendMessage(String message) {
		try {
			currentChat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	public void setDelegate(ConnectionDelegate del) {
		delegate = del;
	}
	
	
}
