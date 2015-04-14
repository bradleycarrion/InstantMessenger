import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class AddFriendFrame extends JFrame {
	private final int WIDTH  = 500;
	private final int HEIGHT = 150;
	
	private JButton add;
	public AddFriendDelegate delegate;
	private JTextField userName;
	private JTextField name;
	
	public AddFriendFrame() {
		super();
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		
		userName = new JTextField("Username");
		userName.setBounds(0,10, 200, 30);
		add(userName);
		
		name = new JTextField("Name");
		name.setBounds(0, 50, 200, 30);
		add(name);
		
		add = new JButton("Add Friend");
		add.setBounds (250, 35, 50, 60);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delegate.addFriend(userName.getText(), name.getText());
				delegate.updateRoster(userName.getText());
				AddFriendFrame.this.dispose();
			}
			
		});
		add(add);
		
		
		
	}

}
