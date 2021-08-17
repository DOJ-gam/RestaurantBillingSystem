import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminLogin {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminLogin window = new adminLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JTextField Username;
	private JPasswordField Password;
	
	
//	Create a global variable
	Connection connection = null;

	/**
	 * Create the application.
	 */
	public adminLogin() {
		initialize();
		connection = sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 720, 385);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Username = new JTextField();
		Username.setBounds(464, 105, 244, 35);
		frame.getContentPane().add(Username);
		Username.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setIcon(new ImageIcon(adminLogin.class.getResource("/img/users.png")));
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 25));
		lblUsername.setBounds(269, 105, 169, 40);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setIcon(new ImageIcon(adminLogin.class.getResource("/img/pass.png")));
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 25));
		lblPassword.setBounds(269, 173, 169, 40);
		frame.getContentPane().add(lblPassword);
		
		Password = new JPasswordField();
		Password.setBounds(464, 171, 244, 35);
		frame.getContentPane().add(Password);
		
		JButton btnLogin = new JButton("Login");
		Image loginImg = new ImageIcon(this.getClass().getResource("img/Login.png")).getImage();
		btnLogin.setIcon(new ImageIcon(loginImg));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String query = "select * from admin where username=? and password=?";
					
//					We create a prepared statement so that we can be able to create an object of the connection.
					PreparedStatement pst = connection.prepareStatement(query);
					
//					we created an object of the connection, 'pst'. now we will use it to set the username and password
					
					pst.setString(1, Username.getText()); //we use the indexes as references to the values respectively
					pst.setString(2, Password.getText());
					
					ResultSet rs = pst.executeQuery();
					
//					Now the 'rs' will help to get our data from the db, but in order to do that, it needs a loop, to loop through the different data.
					int count = 0;
					while(rs.next()) {
						
						count = count + 1;
					}
					
					if(count == 1) {
						JOptionPane.showMessageDialog(null, "Valid Username and password");
						frame.dispose();
						adminPanel ap = new adminPanel();
						ap.setVisible(true);
					}
					
					else if(count > 1) {
						JOptionPane.showMessageDialog(null, "Duplicated Usrname and password");
					}
					else {
						JOptionPane.showMessageDialog(null, "Invalid UserName or Password");
					}
					
					rs.close();
					pst.close();
				} 
				catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		btnLogin.setBounds(503, 226, 187, 40);
		frame.getContentPane().add(btnLogin);
		
		JLabel labelUserImage = new JLabel("");
//		Add Image tolabel
		Image userImg = new ImageIcon(this.getClass().getResource("img/admin.png")).getImage(); //use this class, and use its resources in img and get imgage
		labelUserImage.setIcon(new ImageIcon(userImg));
		
		labelUserImage.setBounds(42, 105, 205, 210);
		frame.getContentPane().add(labelUserImage);
		
		JLabel lblAdminLogin = new JLabel("ADMIN LOGIN");
		lblAdminLogin.setForeground(new Color(0, 204, 204));
		lblAdminLogin.setFont(new Font("Dialog", Font.BOLD, 30));
		lblAdminLogin.setBounds(237, 24, 275, 35);
		frame.getContentPane().add(lblAdminLogin);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Login login = new Login();
				login.frame.setVisible(true);
			}
		});
		btnBackToMain.setIcon(new ImageIcon(adminLogin.class.getResource("/img/cancel.png")));
		menuBar.add(btnBackToMain);
	}
}
