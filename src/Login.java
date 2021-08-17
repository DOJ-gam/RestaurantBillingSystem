

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 255, 153));
		frame.setBounds(100, 100, 878, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSenegambiaRestaurant = new JLabel("SENEGAMBIA RESTAURANT");
		lblSenegambiaRestaurant.setBackground(Color.GRAY);
		lblSenegambiaRestaurant.setForeground(Color.BLUE);
		lblSenegambiaRestaurant.setFont(new Font("Dialog", Font.BOLD, 38));
		lblSenegambiaRestaurant.setBounds(146, -11, 671, 88);
		frame.getContentPane().add(lblSenegambiaRestaurant);
		
		JButton btnAdminLogin = new JButton("Admin Login");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				adminLogin Alogin = new adminLogin();
				Alogin.frame.setVisible(true);
			}
		});
		btnAdminLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAdminLogin.setBounds(40, 134, 174, 39);
		frame.getContentPane().add(btnAdminLogin);
		
		JLabel lblAdminImage = new JLabel("");	
		Image adminImg = new ImageIcon(this.getClass().getResource("/img/admin1.png")).getImage(); //use this class, and use its resources in img and get imgage
		lblAdminImage.setIcon(new ImageIcon(adminImg));
		lblAdminImage.setBounds(40, 185, 174, 200);
		frame.getContentPane().add(lblAdminImage);
		
		
		
		JButton btnUserLogin = new JButton("User Login");
		btnUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				userLogin Ulogin = new userLogin();
				Ulogin.frame.setVisible(true);
			}
		});
		btnUserLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		btnUserLogin.setBounds(659, 124, 174, 39);
		frame.getContentPane().add(btnUserLogin);
		
		JLabel lblUserImg = new JLabel("");
		Image userImg = new ImageIcon(this.getClass().getResource("/img/sales.png")).getImage(); //use this class, and use its resources in img and get imgage
		lblUserImg.setIcon(new ImageIcon(userImg));
		lblUserImg.setBounds(659, 175, 174, 200);
		frame.getContentPane().add(lblUserImg);
		
		JLabel lblMainLoginPage = new JLabel("MAIN LOGIN PAGE");
		lblMainLoginPage.setFont(new Font("Dialog", Font.BOLD, 30));
		lblMainLoginPage.setForeground(new Color(204, 51, 0));
		lblMainLoginPage.setBounds(275, 148, 332, 69);
		frame.getContentPane().add(lblMainLoginPage);
	}
}
