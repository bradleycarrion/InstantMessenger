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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class UserPrompt extends JFrame {
    private final int WIDTH  = 400;
    private final int HEIGHT = 250;
    
    private JTextField userName;
    private JPasswordField password;
    private JButton logIn;
    private JButton signUp;
    private JPanel mainPanel;
    
    public UserPrompt() {
        super();
        //the connection to the server
        final XMPPConnection c = new XMPPConnection("67.185.201.165");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = ((int) screenSize.getWidth()) / 2;
        int height = ((int) screenSize.getHeight()) / 2;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        
        // Username TextField
        userName = new JTextField() {
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        userName.setOpaque(false);
        userName.setBackground(new Color(255,255,255,200));
        userName.setBounds(100, 50, 200, 40);
        userName.setText("username");
        userName.setEnabled(false);
        userName.addMouseListener(new MouseListener() {
            @SuppressWarnings("deprecation")
            public void mousePressed(MouseEvent e) {
                password.setEnabled(true);
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
                        userName.setText("username");
                        userName.setEnabled(false);
                    }
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });
        
        // Password TextField
        password = new JPasswordField("password") {
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        password.setOpaque(false);
        password.setBackground(new Color(255,255,255,200));
        password.setBounds(100, 100, 200, 40);
        password.setEnabled(false);
        password.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                if (userName.getText().compareTo("") == 0) {
                    userName.setText("username");
                    userName.setEnabled(false);
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
            
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    //create new thread to perform the login so the gui doesnt get blocked
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uName = userName.getText();
                            String pWord = password.getText();
                            try {
                                c.connect();
                                c.login(uName, pWord);
                                MainChat chatMain = new MainChat(c);
                                chatMain.setVisible(true);
                                UserPrompt.this.dispose();
                            } catch (XMPPException e) {
                                JOptionPane.showMessageDialog(UserPrompt.this, "Invalid Username or Password", "Login Error",  JOptionPane.ERROR_MESSAGE);
                                System.err.println("Couldn't connect to server");
                            }
                        }
                        
                    }).start();
                }
            }
            public void keyReleased(KeyEvent arg0) {}
            public void keyTyped(KeyEvent arg0) {}
            
        });
        mainPanel.add(userName);
        mainPanel.add(password);
        
        signUp = new JButton("Join") {
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        signUp.setOpaque(false);
        signUp.setBackground(new Color(112,138,144,75));
        signUp.setBounds((WIDTH/2)-70, 150, 60, 30);
        signUp.setBorder(BorderFactory.createEtchedBorder());
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JoinWindow join = new JoinWindow(c);
                join.setVisible(true);
                UserPrompt.this.dispose();
            }
            
        });
        
        logIn = new JButton("Login") {
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        
        logIn.setOpaque(false);
        logIn.setBackground(new Color(112,138,144,75));
        logIn.setBounds((WIDTH/2), 150, 60, 30);
        logIn.setBorder(BorderFactory.createEtchedBorder());
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //create new thread to perform the login so the gui doesnt get blocked
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String uName = userName.getText();
                        String pWord = password.getText();
                        try {
                            c.connect();
                            c.login(uName, pWord);
                            MainChat chatMain = new MainChat(c);
                            chatMain.setVisible(true);
                            UserPrompt.this.dispose();
                        } catch (XMPPException e) {
                            JOptionPane.showMessageDialog(UserPrompt.this, "Invalid Username or Password", "Login Error",  JOptionPane.ERROR_MESSAGE);
                            System.err.println("Couldn't connect to server");
                        }
                    }
                    
                }).start();
                
            }
            
        });
        mainPanel.add(logIn);
        mainPanel.add(signUp);
    }
}
