import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MainViewController extends JFrame {

	// GUI Variables
	private JPanel contentPane;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JLabel titleBar;
	private JTextField messageBox;
	private JTextField thisUsernameBox;
	private JTextField otherUsernameBox;
	private JButton sendButton;
	private Font buttonFont;
	private Font titleFont;
	private JTextArea messageDisplay;
	
	// Other Variables
	private String thisUsername;
	private String otherUsername;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainViewController frame = new MainViewController();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainViewController() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Creating the top panel
		topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		// Creating the middle panel
		centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(2, 132, 130));
		
		thisUsernameBox = new RoundedJTextField(10);
		thisUsernameBox.setText("Your Username");
		thisUsernameBox.setHorizontalAlignment(JTextField.CENTER);
		thisUsernameBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		thisUsernameBox.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	        	 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		 thisUsername = thisUsernameBox.getText();
	        		 thisUsernameBox.setEnabled(false);
	        	 }
	         }
	     });
		
		otherUsernameBox = new RoundedJTextField(10);
		otherUsernameBox.setText("Other User");
		otherUsernameBox.setHorizontalAlignment(JTextField.CENTER);
		otherUsernameBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		otherUsernameBox.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	        	 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		 otherUsername = otherUsernameBox.getText();
	        		 otherUsernameBox.setEnabled(false);
	        	 }
	         }
	     });
		
		// Creating the messaging text box
	    messageBox = new RoundedJTextField(45);
	    messageBox.setText("Your message");
	    messageBox.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	    messageBox.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	        	 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		 messageDisplay.append(thisUsername + ": " + messageBox.getText() + "\n" + "-----------------------------------------------------------------------------------------\n");
	 				 messageBox.setText("");
	        	 }
	         }
	     });
	    
	    // Creating the SEND button
	    buttonFont = new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 20);
	    sendButton = new JButton("Send");
	    sendButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
	    sendButton.setFont(buttonFont);	
	    sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageDisplay.append(thisUsername + ": " + messageBox.getText() + "\n" + "----------------------------------------------------------------------------------------\n");
				messageBox.setText("");
			}
		});
	    
	    // Application Title
	    titleFont = new Font(Font.MONOSPACED, 0, 25);
	    titleBar = new JLabel("Instant_Messenger...");
	    titleBar.setForeground(Color.WHITE);
	    titleBar.setFont(titleFont);
	    
	    messageDisplay = new JTextArea(28, 60);
	    messageDisplay.setBorder(BorderFactory.createEtchedBorder());
	    messageDisplay.setEditable(false);
	    
	    // Adding Page Attributes
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(centerPanel, BorderLayout.CENTER);
	    centerPanel.add(messageBox);
	    centerPanel.add(sendButton);
	    centerPanel.add(messageDisplay);
	    topPanel.add(titleBar);
	    topPanel.add(thisUsernameBox);
	    topPanel.add(otherUsernameBox);
	    
		
	}

}
