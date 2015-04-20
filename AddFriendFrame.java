import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddFriendFrame extends JFrame {
	private final int WIDTH  = 400;
	private final int HEIGHT = 250;
	
	private JPanel mainPanel;
	private JButton addFriend;
	public AddFriendDelegate delegate;
	private JTextField userName;
	private JTextField name;
	
	public AddFriendFrame() {
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = ((int) screenSize.getWidth()) / 2;
		int height = ((int) screenSize.getHeight()) / 2;
		setBounds(width-(WIDTH/2),height-(HEIGHT/2),WIDTH, HEIGHT);
		setLayout(null);
		mainPanel = new JPanel() {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bulldog.jpg")));
		contentPane.setLayout(new BorderLayout());
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(new Color(255,255,255,40));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.add(mainPanel);
		
		userName = new JTextField("Username") {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		userName.setEnabled(false);
		userName.setOpaque(false);
		userName.setBackground(new Color(255,255,255,200));
		userName.setBounds(WIDTH/2 - 100, (HEIGHT - 200), 200, 40);
		userName.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				userName.setEnabled(true);
				userName.setText("");
				userName.requestFocus();
				name.setEnabled(true);
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		});
		mainPanel.add(userName);
		
		name = new JTextField("Name") {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		name.setOpaque(false);
		name.setEnabled(false);
		name.setBackground(new Color(255,255,255,200));
		name.setBounds(WIDTH/2 - 100, (HEIGHT - 140), 200, 40);
		name.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				name.setEnabled(true);
				name.setText("");
				name.requestFocus();
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		});
		mainPanel.add(name);
		
		addFriend = new JButton("Add Friend") {
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		addFriend.setOpaque(false);
		addFriend.setBackground(new Color(112,138,144,75));
		addFriend.setBounds(155, 165, 80, 40);
		addFriend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delegate.addFriend(userName.getText(), name.getText());
				delegate.updateRoster();
				AddFriendFrame.this.dispose();
			}
			
		});
		mainPanel.add(addFriend);
		
		
		
	}

}
