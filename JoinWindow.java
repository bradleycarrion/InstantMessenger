import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private final int WIDTH  = 350;
	private final int HEIGHT = 250;
	
	//GUI
	private JPanel mainPanel;
	private JTextField userName;
	private JTextField password;
	private JTextField confirmPw;
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
		setResizable(false);
		mainPanel = new JPanel(){
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon("bulldog.jpg"));
		contentPane.setLayout(new BorderLayout());
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(new Color(255,255,255,40));
		setContentPane(contentPane);
		contentPane.add(mainPanel);

		
		
		userName = new JTextField(){
		protected void paintComponent(Graphics g)
	    {
	        g.setColor( getBackground() );
	        g.fillRect(0, 0, getWidth(), getHeight());
	        super.paintComponent(g);
	    }
		};
		userName.setBounds((WIDTH/2)-100, 30, 200, 40);
		userName.setOpaque(false);
		userName.setBackground(new Color(255,255,255,200));
		userName.setText("Username");
		userName.setEnabled(false);
		userName.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (password.getText().compareTo("") == 0) {
					password.setText("Password");
					password.setEnabled(false);
				}
				if	(confirmPw.getText().compareTo("") == 0){
					confirmPw.setText("Re-Type Pass");
					confirmPw.setEnabled(false);
				}
			    userName.setEnabled(true);
			    userName.setText("");
			    userName.requestFocus();
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		userName.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					password.setEnabled(true);
					password.setText("");
					password.requestFocus();
					if (userName.getText().compareTo("") == 0) {
						userName.setText("Username");
						userName.setEnabled(false);
					}
				}
		    }
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		mainPanel.add(userName);
		
		password = new JTextField(){
		protected void paintComponent(Graphics g)
	    {
	        g.setColor( getBackground() );
	        g.fillRect(0, 0, getWidth(), getHeight());
	        super.paintComponent(g);
	    }
		};
		password.setOpaque(false);
		password.setBounds((WIDTH/2)-100, 80, 200, 40);
		password.setBackground(new Color(255,255,255,200));
		password.setEnabled(false);
		password.setText("Password");
		password.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (userName.getText().compareTo("") == 0) {
					userName.setText("Username");
					userName.setEnabled(false);
				}
				if	(confirmPw.getText().compareTo("") == 0){
					confirmPw.setText("Re-Type Pass");
					confirmPw.setEnabled(false);
				}
			    password.setEnabled(true);
			    password.setText("");
			    password.requestFocus();
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		password.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					confirmPw.setEnabled(true);
					confirmPw.setText("");
					confirmPw.requestFocus();
					if (password.getText().compareTo("") == 0) {
						password.setText("Password");
						password.setEnabled(false);
					}
				}
		    }
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		mainPanel.add(password);
		
		confirmPw = new JTextField(){
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
			};
		confirmPw.setBounds((WIDTH/2)-100, 130, 200, 40);
		confirmPw.setOpaque(false);
		confirmPw.setBackground(new Color(255,255,255,200));
		confirmPw.setEnabled(false);
		confirmPw.setText("Re-Type Pass");
		confirmPw.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (password.getText().compareTo("") == 0) {
					password.setText("Password");
					password.setEnabled(false);
				}
				if	(userName.getText().compareTo("") == 0){
					userName.setText("Username");
					userName.setEnabled(false);
				}
			    confirmPw.setEnabled(true);
			    confirmPw.setText("");
			    confirmPw.requestFocus();
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		confirmPw.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					confirmPw.setEnabled(true);
					confirmPw.setText("Re-Type Pass");
					confirmPw.requestFocus();
					if (password.getText().compareTo("") == 0) {
						password.setText("Password");
						password.setEnabled(false);
					}
				}
		    }
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		mainPanel.add(confirmPw);
		
		join = new JButton("Join")	{
		protected void paintComponent(Graphics g)
	    {
	        g.setColor( getBackground() );
	        g.fillRect(0, 0, getWidth(), getHeight());
	        super.paintComponent(g);
	    }
	};
		join.setBounds((WIDTH/2)-30, 180, 60, 30);
		join.setOpaque(false);
		join.setBackground(new Color(112,138,144,75));
		join.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String pw = new String(password.getText());
				String cPw = new String(confirmPw.getText());
				
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
		mainPanel.add(join);
		
	}
	

}
