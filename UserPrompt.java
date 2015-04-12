import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class UserPrompt extends JFrame {
	private final int WIDTH  = 500;
	private final int HEIGHT = 140;
	
	private JTextField userName;
	private JTextField password;
	private JButton logIn;
	
	public UserPrompt() {
		super();
		setBounds(0,0,WIDTH, HEIGHT);
		setLayout(null);
		
		userName = new JTextField("Username");
		userName.setBounds(0,10, 200, 30);
		password = new JTextField("Password");
		password.setBounds(0,50, 200, 30);
		add(userName);
		add(password);
		
		logIn = new JButton("Login");
		logIn.setBounds(300,40,75,25);
		logIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String uName    = userName.getText();
				String pWord    = password.getText();
		
				XMPPConnection c = new XMPPConnection("67.185.201.165");
				try {
					c.connect();
					c.login(uName, pWord);
					MainChat chatMain = new MainChat(c);
					chatMain.setVisible(true);
					UserPrompt.this.dispose();
				} catch (XMPPException e) {
					System.err.println("Couldn't connect to server");
				}
			}
			
		});
		add(logIn);
	}
}
