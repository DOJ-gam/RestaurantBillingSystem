import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class adminPanel extends JFrame {
	

	Connection connection = null;
	private JPanel contentPane;
	private JTable table_edit_users;
	private JTable table;
	private JTextField txt_firstName;
	private JTextField txt_LastName;
	private JTextField txt_ID;
	private JPasswordField txt_Password;
	private JComboBox box_chooseUserName;
	private JTextField txt_Username;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminPanel frame = new adminPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
//	Create method for ID Checkbox
	public void boxID() {
		
		box_chooseUserName.addItem("Select User");

		try {
			
			String query = "SELECT * FROM user";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
//				variable name
				box_chooseUserName.addItem(rs.getString("username"));
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	Create Metho to reload Table
	public void ReloadTable() {
		try {
			
			String query = "SELECT id, first_name, last_name, username FROM user";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
//			The rs needs a data module in order to populate the result for us(we can use rs2xml)
			
//			we use the table name
			table_edit_users.setModel(DbUtils.resultSetToTableModel(rs));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the frame.
	 */
	public adminPanel() {
		
//		Call Methods
		
		
//		call methods
		
		connection = sqliteConnection.dbConnector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1320, 559);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.BLUE);
		mnFile.setFont(new Font("Dialog", Font.BOLD, 25));
		menuBar.add(mnFile);
		
		JMenuItem mntmReset = new JMenuItem("Reset");
		mntmReset.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmReset);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setForeground(Color.RED);
		mntmExit.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLoad = new JButton("Reload Table");
		btnLoad.setIcon(new ImageIcon(adminPanel.class.getResource("/img/search.png")));
		btnLoad.setBounds(149, 14, 161, 25);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ReloadTable();
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnLoad);
		
		JTabbedPane tab_edit_user = new JTabbedPane(JTabbedPane.TOP);
		tab_edit_user.setBounds(453, 38, 855, 424);
		tab_edit_user.setFont(new Font("Dialog", Font.BOLD, 30));
		contentPane.add(tab_edit_user);
		
		JScrollPane scrollPane = new JScrollPane();
		tab_edit_user.addTab("Edit Users", new ImageIcon(adminPanel.class.getResource("/img/users.png")), scrollPane, null);
		
		table_edit_users = new JTable();
		scrollPane.setViewportView(table_edit_users);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tab_edit_user.addTab("View Sales", new ImageIcon(adminPanel.class.getResource("/img/report.png")), scrollPane_1, null);
		
		table = new JTable();
		scrollPane_1.setRowHeaderView(table);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		tab_edit_user.addTab("Manage Items", new ImageIcon(adminPanel.class.getResource("/img/save.png")), scrollPane_2, null);
		
		table_1 = new JTable();
		scrollPane_2.setColumnHeaderView(table_1);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Dialog", Font.BOLD, 25));
		lblId.setBounds(33, 134, 161, 30);
		contentPane.add(lblId);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Dialog", Font.BOLD, 25));
		lblFirstName.setBounds(33, 185, 161, 30);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 25));
		lblLastName.setBounds(33, 244, 161, 30);
		contentPane.add(lblLastName);
		
		JLabel lblUsernameBox = new JLabel("Username");
		lblUsernameBox.setFont(new Font("Dialog", Font.BOLD, 25));
		lblUsernameBox.setBounds(33, 79, 161, 30);
		contentPane.add(lblUsernameBox);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 25));
		lblPassword.setBounds(33, 341, 161, 30);
		contentPane.add(lblPassword);
		
		txt_firstName = new JTextField();
		txt_firstName.setColumns(10);
		txt_firstName.setBounds(212, 186, 195, 38);
		contentPane.add(txt_firstName);
		
		txt_LastName = new JTextField();
		txt_LastName.setColumns(10);
		txt_LastName.setBounds(212, 236, 195, 38);
		contentPane.add(txt_LastName);
		
		txt_ID = new JTextField();
		txt_ID.setColumns(10);
		txt_ID.setBounds(212, 135, 195, 38);
		contentPane.add(txt_ID);
		
		txt_Password = new JPasswordField();
		txt_Password.setColumns(10);
		txt_Password.setBounds(212, 342, 195, 38);
		contentPane.add(txt_Password);
		
		box_chooseUserName = new JComboBox();
		box_chooseUserName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (box_chooseUserName.getSelectedIndex() == 0) {
					
					txt_ID.setText(null);
					txt_firstName.setText(null);
					txt_LastName.setText(null);
					txt_Username.setText(null);
					txt_Password.setText(null);
				}
				
				try {
					
					String query = "SELECT * FROM  user WHERE username = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, (String)box_chooseUserName.getSelectedItem()); //the unknown username
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						txt_ID.setText(rs.getString("id"));
						txt_firstName.setText(rs.getString("first_name"));
						txt_LastName.setText(rs.getString("last_name"));
						txt_Username.setText(rs.getString("username"));
						txt_Password.setText(rs.getString("password"));
						
					}
					pst.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		box_chooseUserName.setBounds(212, 79, 195, 38);
		contentPane.add(box_chooseUserName);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 25));
		lblUsername.setBounds(33, 290, 161, 30);
		contentPane.add(lblUsername);
		
		txt_Username = new JTextField();
		txt_Username.setColumns(10);
		txt_Username.setBounds(212, 291, 195, 38);
		contentPane.add(txt_Username);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String query = "INSERT INTO user(id, first_name, last_name, username, password) Values(?, ?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txt_ID.getText());
					pst.setString(2, txt_firstName.getText());
					pst.setString(3, txt_LastName.getText());
					pst.setString(4, txt_Username.getText());
					pst.setString(5, txt_Password.getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "User Added Successfully", "Added User to Database", JOptionPane.INFORMATION_MESSAGE);
					
					pst.close();
					
				} 
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
				ReloadTable();
				boxID();
				
			}
		});
		btnAddNew.setIcon(new ImageIcon(adminPanel.class.getResource("/img/addnew.png")));
		btnAddNew.setBounds(33, 437, 117, 25);
		contentPane.add(btnAddNew);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				String query = "UPDATE user set ID = '" +txt_ID.getText()+ "', "
                        + "first_name = '" +txt_firstName.getText()+ "', "
                        + "last_name = '" +txt_LastName.getText()+ "', "
                        + "username = '" +txt_Username.getText()+ "', "
                        + "password = '" +txt_Password.getText()+ "' "
                        + "WHERE ID = '" +txt_ID.getText()+ "' ";
				PreparedStatement pst = connection.prepareStatement(query);
				
				JOptionPane.showMessageDialog(null, "User Successfully Updated", "Updated User in Database", JOptionPane.INFORMATION_MESSAGE);

				pst.execute();
				
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				ReloadTable();
				boxID();
			}
		});
		btnUpdate.setIcon(new ImageIcon(adminPanel.class.getResource("/img/update.png")));
		btnUpdate.setBounds(183, 437, 117, 25);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String query = "DELETE FROM user WHERE ID = '" +txt_ID.getText()+ "' ";
					
					PreparedStatement pst = connection.prepareStatement(query);;			
					pst.execute();
				
					JOptionPane.showMessageDialog(null, "User Successfully Deleted", "Deleted User from Database", JOptionPane.INFORMATION_MESSAGE);
					
					pst.close();
				}
				
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				ReloadTable();
				boxID();
				
			}
		});
		btnDelete.setIcon(new ImageIcon(adminPanel.class.getResource("/img/delete.png")));
		btnDelete.setBounds(324, 437, 117, 25);
		contentPane.add(btnDelete);
		
		JCheckBox chckbxShowPassword = new JCheckBox("show password");
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (chckbxShowPassword.isSelected()) {
					
				}
			}
		});
		chckbxShowPassword.setBounds(233, 406, 154, 23);
		contentPane.add(chckbxShowPassword);
		
//		Call Methods
		ReloadTable();
		boxID();
	}
}
