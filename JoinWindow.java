import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * 
 * need to fix creating a user that name has already been taken 
 *
 */

public class JoinWindow extends JFrame {
	private XMPPConnection connection;
	
	//dimensions
	private final int WIDTH  = 400;
	private final int HEIGHT = 350;
	
	//GUI
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField confirmPw;
	private JButton join;
	
	public JoinWindow(XMPPConnection c) {
		super();
		
		//initialize the connection
		connection = c;
		
		//set up frame dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = ((int) screenSize.getWidth()) / 2;
		int height = ((int) screenSize.getHeight()) / 2;
		setBounds(width-(WIDTH/2),height-(HEIGHT/2),WIDTH, HEIGHT);
		setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(100, 30, 200, 40);
		add(userName);
		
		password = new JPasswordField();
		password.setBounds(100, 80, 200, 40);
		add(password);
		
		confirmPw = new JPasswordField();
		confirmPw.setBounds(100, 130, 200, 40);
		add(confirmPw);
		
		join = new JButton("Join");
		join.setBounds(150, 180, 100, 40);
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pw = new String(password.getPassword());
				String cPw = new String(confirmPw.getPassword());
				
				if (cPw.equals(pw) && !(cPw.equals("")) && !(userName.getText().equals(""))) {
					System.out.println("Valid");
					try{
						connection.connect();
						AccountManager manager = connection.getAccountManager();
						manager.createAccount(userName.getText(), pw);
						
						connection.login(userName.getText(), pw);
						
						MainChat mChat = new MainChat(connection);
						mChat.setVisible(true);
						JoinWindow.this.dispose();
					} catch (XMPPException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(JoinWindow.this, "Invalid Username or Password", "Join Error",  JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		add(join);
		
	}
	

}
