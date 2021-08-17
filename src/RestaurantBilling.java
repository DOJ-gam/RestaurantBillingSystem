
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;

public class RestaurantBilling {

	Connection connection = null;
	JFrame frame;
	private JTextField txt_EnterCurrency;	
	private JLabel label_TotalCostOfDrink;
	private JLabel txt_TotalCostOfDrink;
	private JLabel txt_TotalCostOfMeal;
	private JLabel txt_delivery;
	private JLabel txt_Tax;
	private JLabel txt_SubTotal;
	private JLabel txt_Total;
	private JLabel label_Tax;
	private JLabel label_SubTotal;
	private JLabel label_Total;
	private JLabel txt_CurrencyOutput;
	private JComboBox box_ChooseCountry;
	private JComboBox box_ChooseFood;
	private JComboBox box_ChooseDrink;
	private JTextArea receipt_Area;
	private JLabel lblClock;
	private JLabel lbl_food_price;
	private JLabel lbl_drink_price;
	private JComboBox box_quantity_of_food;
	private JComboBox box_quantity_of_drink;
	private JTextArea textArea_confirm_sales;
	private JCheckBox check_Delivery;
	private JCheckBox check_Tax;
	private JButton btn_Submit_1;
	private JButton btn_Submit_1_1;


	
//	Calculator Variables
	private JTextField calculator_display;
	double num1, num2, result;
	String operations, answer;
	
//	Currency Convert(Values of Currency to 1 USD)
	double Gambian_Dalasis = 50.00;
	double Nigerian_Naira = 360.00;
	double Kenyan_Shilling = 101.00;
	double Chinese_Yuan = 6.29;
	double Indian_Rupee = 65.02;
	

//ArrayLists for storing sales temporarily
	private ArrayList<String> foods;
	private ArrayList<Integer> food_quantity;
	private ArrayList<Double> food_price;
	private ArrayList<String> drinks;
	private ArrayList<Integer> drink_quantity;
	private ArrayList<Double> drink_price;

//ArrayLists for storing sales till program closes
	private ArrayList<String> all_foods;
	private ArrayList<Integer> all_food_quantity;
	private ArrayList<Double> all_food_price;
	private ArrayList<String> all_drinks;
	private ArrayList<Integer> all_drink_quantity;
	private ArrayList<Double> all_drink_price;	
	
// Totals
	double total_drinks;
	double total_foods;
	double total_tax;
	double total_delivery;
	double sub_total;
	double total;
	
	private String show_delivery;
	private String showTax;
	private String show_total_drinks;
	private String showSubTotal;
	private String show_total_meals;
	private String showTotal;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurantBilling window = new RestaurantBilling();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
//	Create a method to add clock
//	Method to generate the date and time
	public void datetime() {
		
		//		Create a thread to make sure it continues running
		Thread datetime = new Thread() {
			
			public void run() {
				
				try {		
//					Infinite loop
					for(;;) {
						Calendar cal = new GregorianCalendar();
						
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);				
						lblClock.setText("Time:" +hour+ ":" +minute+ ":" +second+ "	  "  +year+ "/" + month+ "/" +day);
						
//						Not necessary
						sleep(1000);
					}
				} 
				catch (Exception e) {
					
				}
			}
		};
		
		datetime.start();
	}
	
//	ComboBox for Food Stuff
	public void foodBox() {
		
		box_ChooseFood.addItem("Choose Food");

		try {
			
			String query = "SELECT * FROM foods";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
//				variable name
				box_ChooseFood.addItem(rs.getString("name"));
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	ComboBox for Drinks
	public void drinkBox() {
		
		box_ChooseDrink.addItem("Choose Drink");

		try {
			
			String query = "SELECT * FROM drinks";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
//				variable name
				box_ChooseDrink.addItem(rs.getString("name") );
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public RestaurantBilling() {
		initialize();
		datetime();
		foodBox();
		drinkBox();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connection = sqliteConnection.dbConnector();
		frame = new JFrame();
		frame.setBounds(0, 0, 1500, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel left_panel = new JPanel();
		left_panel.setBounds(10, 47, 461, 709);
		left_panel.setBorder(new LineBorder(new Color(0, 0, 0), 10));
		frame.getContentPane().add(left_panel);
		left_panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 31, 438, 652);
		left_panel.add(tabbedPane);
		
		JPanel confirm_sales = new JPanel();
		tabbedPane.addTab("Confrim Sales", new ImageIcon(RestaurantBilling.class.getResource("/img/success.png")), confirm_sales, null);
		confirm_sales.setLayout(null);
		
		textArea_confirm_sales = new JTextArea();
		textArea_confirm_sales.setBounds(0, 0, 433, 570);
		confirm_sales.add(textArea_confirm_sales);
		textArea_confirm_sales.append("\n\n\n\n");
		textArea_confirm_sales.append("***********************************************************************\n"
				                    + "  ITEMS\t\t QUANTITY\t\t PRICE($) \n"
				                    + "************************************************************************\n\n");
		textArea_confirm_sales.setEditable(false);

		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/success.png")));
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
//				
//				System.out.println("Drinks");
//				for (String d : all_drinks) {
//					System.out.println(d);
//				}
//				
//				System.out.println("Foods");
//				for (String f : all_foods) {
//					System.out.println(f);
//				}
				
//				textArea_confirm_sales.append("\n\nFood Total\n"+total_foods);
				
//				total foods
				show_total_meals = String.format("$%.3f", total_foods);		
				txt_TotalCostOfMeal.setText(show_total_meals);
				
//				total drinks
				show_total_drinks = String.format("$%.3f", total_drinks);	
				txt_TotalCostOfDrink.setText(show_total_drinks);
				
//				total delivery
				txt_delivery.setText(show_delivery);
				
//				SubTotal
				sub_total = (total_foods + total_drinks + total_delivery);
				showSubTotal = String.format("$%.3f", sub_total);		
				txt_SubTotal.setText(showSubTotal);
				
//				total tax
				txt_Tax.setText(showTax);
				
//				==========================Total================================		
				total = (sub_total + total_tax);
				showTotal = String.format("$%.3f", total);		
				txt_Total.setText(showTotal);		
				
				
			}
		});
		btnNewButton.setBounds(10, 575, 411, 38);
		confirm_sales.add(btnNewButton);
		
		
		JPanel calculator = new JPanel();
		tabbedPane.addTab("Calculator", new ImageIcon(RestaurantBilling.class.getResource("/img/update.png")), calculator, null);
		calculator.setLayout(null);
		
		calculator_display = new JTextField();
		calculator_display.setHorizontalAlignment(SwingConstants.RIGHT);
		calculator_display.setFont(new Font("Dialog", Font.PLAIN, 60));
		calculator_display.setColumns(10);
		calculator_display.setBounds(0, 12, 435, 59);
		calculator.add(calculator_display);
		
		JButton btn_back_space = new JButton("Del");
		btn_back_space.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (calculator_display.getText().length() > 0) {
					
					String backspace = null;
					
					StringBuilder strb = new StringBuilder(calculator_display.getText());
					strb.deleteCharAt(calculator_display.getText().length()-1);
					backspace = strb.toString();
					calculator_display.setText(backspace);
				}
			}
		});
		btn_back_space.setFont(new Font("Dialog", Font.BOLD, 34));
		btn_back_space.setBounds(5, 83, 100, 100);
		calculator.add(btn_back_space);
		
		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn7.getText();
				calculator_display.setText(input);
			}
		});
		btn7.setFont(new Font("Dialog", Font.BOLD, 70));
		btn7.setBounds(5, 193, 100, 100);
		calculator.add(btn7);
		
		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn4.getText();
				calculator_display.setText(input);
			}
		});
		btn4.setFont(new Font("Dialog", Font.BOLD, 70));
		btn4.setBounds(5, 303, 100, 100);
		calculator.add(btn4);
		
		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn1.getText();
				calculator_display.setText(input);
			}
		});
		btn1.setFont(new Font("Dialog", Font.BOLD, 70));
		btn1.setBounds(5, 413, 100, 100);
		calculator.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn2.getText();
				calculator_display.setText(input);
			}
		});
		btn2.setFont(new Font("Dialog", Font.BOLD, 70));
		btn2.setBounds(115, 413, 100, 100);
		calculator.add(btn2);
		
		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn5.getText();
				calculator_display.setText(input);
			}
		});
		btn5.setFont(new Font("Dialog", Font.BOLD, 70));
		btn5.setBounds(115, 303, 100, 100);
		calculator.add(btn5);
		
		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn8.getText();
				calculator_display.setText(input);
			}
		});
		btn8.setFont(new Font("Dialog", Font.BOLD, 70));
		btn8.setBounds(115, 193, 100, 100);
		calculator.add(btn8);
		
		JButton btn_clear = new JButton("C");
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				calculator_display.setText(null);
			}
		});
		btn_clear.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_clear.setBounds(115, 83, 100, 100);
		calculator.add(btn_clear);
		
		JButton btn_modulus = new JButton("%");
		btn_modulus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				num1 = Double.parseDouble(calculator_display.getText());
				calculator_display.setText("");
				operations = "%";
			}
		});
		btn_modulus.setFont(new Font("Dialog", Font.BOLD, 50));
		btn_modulus.setBounds(225, 83, 100, 100);
		calculator.add(btn_modulus);
		
		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn9.getText();
				calculator_display.setText(input);
			}
		});
		btn9.setFont(new Font("Dialog", Font.BOLD, 70));
		btn9.setBounds(225, 193, 100, 100);
		calculator.add(btn9);
		
		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn6.getText();
				calculator_display.setText(input);
			}
		});
		btn6.setFont(new Font("Dialog", Font.BOLD, 70));
		btn6.setBounds(225, 303, 100, 100);
		calculator.add(btn6);
		
		JButton btn_x = new JButton("X");
		btn_x.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				num1 = Double.parseDouble(calculator_display.getText());
				calculator_display.setText("");
				operations = "X";
			}
		});
		btn_x.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_x.setBounds(335, 303, 100, 100);
		calculator.add(btn_x);
		
		JButton btn_minus = new JButton("-");
		btn_minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				num1 = Double.parseDouble(calculator_display.getText());
				calculator_display.setText("");
				operations = "-";
			}
		});
		btn_minus.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_minus.setBounds(335, 193, 100, 100);
		calculator.add(btn_minus);
		
		JButton btn_plus = new JButton("+");
		btn_plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				num1 = Double.parseDouble(calculator_display.getText());
				calculator_display.setText("");
				operations = "+";
			}
		});
		btn_plus.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_plus.setBounds(335, 83, 100, 100);
		calculator.add(btn_plus);
		
		JButton btn_div = new JButton("/");
		btn_div.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				num1 = Double.parseDouble(calculator_display.getText());
				calculator_display.setText("");
				operations = "/";
			}
		});
		btn_div.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_div.setBounds(335, 413, 100, 100);
		calculator.add(btn_div);
		
		JButton btn_equal = new JButton("=");
		btn_equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String answer;
				
				num2 = Double.parseDouble(calculator_display.getText());
				
				if (operations == "+") {
					
					result = num1 + num2;
					answer = String.format("%.2f", result);
					calculator_display.setText(answer);
				}
				else if (operations == "-") {
					
					result = num1 - num2;
					answer = String.format("%.2f", result);
					calculator_display.setText(answer);
				}
				else if (operations == "X") {
					
					result = num1 * num2;
					answer = String.format("%.2f", result);
					calculator_display.setText(answer);
				}
				else if (operations == "/") {
					
					result = num1 / num2;
					answer = String.format("%.2f", result);
					calculator_display.setText(answer);
				}
				else if (operations == "%") {
					
					result = num1 % num2;
					answer = String.format("%.2f", result);
					calculator_display.setText(answer);
				}



			}
		});
		btn_equal.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_equal.setBounds(335, 523, 100, 100);
		calculator.add(btn_equal);
		
		JButton btn_plus_or_Minus = new JButton("±");
		btn_plus_or_Minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				double plusminus = Double.parseDouble(String.valueOf(calculator_display.getText()));
				plusminus = plusminus * (-1);
				calculator_display.setText(String.valueOf(plusminus));
			}
		});
		btn_plus_or_Minus.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_plus_or_Minus.setBounds(225, 523, 100, 100);
		calculator.add(btn_plus_or_Minus);
		
		JButton btn_dot = new JButton(".");
		btn_dot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn_dot.getText();
				calculator_display.setText(input);
			}
		});
		btn_dot.setFont(new Font("Dialog", Font.BOLD, 70));
		btn_dot.setBounds(115, 523, 100, 100);
		calculator.add(btn_dot);
		
		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn0.getText();
				calculator_display.setText(input);
			}
		});
		btn0.setFont(new Font("Dialog", Font.BOLD, 70));
		btn0.setBounds(5, 523, 100, 100);
		calculator.add(btn0);
		
		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = calculator_display.getText() + btn3.getText();
				calculator_display.setText(input);
			}
		});
		btn3.setFont(new Font("Dialog", Font.BOLD, 70));
		btn3.setBounds(225, 413, 100, 100);
		calculator.add(btn3);
		
		JPanel receipt = new JPanel();
		tabbedPane.addTab("Receipt", new ImageIcon(RestaurantBilling.class.getResource("/img/report.png")), receipt, null);
		receipt.setLayout(null);
		
		receipt_Area = new JTextArea();
		receipt_Area.setBounds(0, 12, 433, 625);
		receipt.add(receipt_Area);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(486, 126, 1002, 369);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 10));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setForeground(Color.BLUE);
		lblMenu.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblMenu.setBounds(39, 12, 125, 36);
		panel_1.add(lblMenu);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setForeground(Color.BLUE);
		lblQty.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblQty.setBounds(367, 12, 125, 36);
		panel_1.add(lblQty);
		
		box_ChooseDrink = new JComboBox();
		box_ChooseDrink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (box_ChooseDrink.getSelectedIndex()==0) {
					lbl_drink_price.setText("0");
				}
				
				try {
					
//					String query = "SELECT * FROM foods WHERE id = ?";
					String query = "SELECT * FROM drinks WHERE name = ?";
					
					PreparedStatement pst = connection.prepareStatement(query);
					
//					String price = (String) box_ChooseFood.getSelectedItem();
//					pst.setInt(1, Integer.parseInt(price) );
					pst.setString(1, (String) box_ChooseDrink.getSelectedItem() );
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						lbl_drink_price.setText(rs.getString("price"));
					}
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		box_ChooseDrink.setFont(new Font("Dialog", Font.PLAIN, 25));
		box_ChooseDrink.setBounds(25, 166, 201, 36);
		panel_1.add(box_ChooseDrink);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(646, 12, 9, 317);
		panel_1.add(separator_3);
		
		JSeparator separator_3_1 = new JSeparator();
		separator_3_1.setBounds(39, 251, 607, 2);
		panel_1.add(separator_3_1);
		
		check_Delivery = new JCheckBox("Delivery");
		check_Delivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (check_Delivery.isSelected()) {
					total_delivery = 4.5;
					show_delivery = String.format("$%.3f", total_delivery); 
				}
				else if(check_Delivery.isSelected()==false) {
					total_delivery = 0.00;
					show_delivery = String.format("$%.3f", total_delivery); 
				}
			}
		});
		check_Delivery.setFont(new Font("Dialog", Font.PLAIN, 20));
		check_Delivery.setBounds(39, 303, 129, 23);
		panel_1.add(check_Delivery);
		
		check_Tax = new JCheckBox("Tax");
		check_Tax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (check_Tax.isSelected()) {
					
//					total_tax = (total_foods * total_drinks * total_delivery) / 100;
					total_tax = sub_total / 100;
					showTax = String.format("$%.3f", total_tax); 
				}
				else if(check_Tax.isSelected() == false) {
					total_tax = 0.00;
					showTax = String.format("$%.3f", total_tax); 
				}
			}
		});
		check_Tax.setFont(new Font("Dialog", Font.PLAIN, 20));
		check_Tax.setBounds(39, 330, 129, 23);
		panel_1.add(check_Tax);
		
//		RESET BUTTON-1
		JButton btn_Reset1 = new JButton("Reset");
		btn_Reset1.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/delete.png")));
		btn_Reset1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				txt_TotalCostOfMeal.setText(null);
				txt_TotalCostOfDrink.setText(null);
				txt_delivery.setText(null);
				txt_Tax.setText(null);
				txt_SubTotal.setText(null);
				txt_Total.setText(null);
				txt_EnterCurrency.setText(null);
				txt_CurrencyOutput.setText(null);
				calculator_display.setText(null);
				box_ChooseDrink.setSelectedItem("Choose a drink");
				box_ChooseCountry.setSelectedItem("Country");		
				receipt_Area.setText(null);
				
				box_ChooseFood.setSelectedIndex(0);
				box_ChooseDrink.setSelectedIndex(0);
				
//				ArrayLists
				all_foods.clear();
				all_food_price.clear();
				all_food_quantity.clear();
				
				
				total_foods = 0;
				total_drinks = 0;
				total_delivery = 0;
				total = 0;
				sub_total = 0;
				total_tax = 0;

				
				
//				for (String f: all_foods){
//					System.out.println(f);
//				}
//				
//				System.out.println(all_foods.size());

			}
		});
		btn_Reset1.setFont(new Font("Dialog", Font.BOLD, 25));
		btn_Reset1.setBounds(192, 303, 442, 30);
		panel_1.add(btn_Reset1);
		
		JLabel lblCurrencyConverter = new JLabel("Currency Converter");
		lblCurrencyConverter.setForeground(Color.BLUE);
		lblCurrencyConverter.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblCurrencyConverter.setBounds(654, 15, 336, 30);
		panel_1.add(lblCurrencyConverter);
		
		box_ChooseCountry = new JComboBox();
		box_ChooseCountry.setModel(new DefaultComboBoxModel(new String[] {"Country", "Gambia", "Nigeria", "Kenya", "China", "India"}));
		box_ChooseCountry.setFont(new Font("Dialog", Font.PLAIN, 25));
		box_ChooseCountry.setBounds(673, 57, 297, 36);
		panel_1.add(box_ChooseCountry);
		
		txt_EnterCurrency = new JTextField();
		txt_EnterCurrency.setText(" ");
		txt_EnterCurrency.setFont(new Font("Dialog", Font.BOLD, 30));
		txt_EnterCurrency.setColumns(10);
		txt_EnterCurrency.setBounds(673, 122, 297, 49);
		panel_1.add(txt_EnterCurrency);
		
		txt_CurrencyOutput = new JLabel(""); 
		txt_CurrencyOutput.setFont(new Font("Dialog", Font.BOLD, 20));
		txt_CurrencyOutput.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_CurrencyOutput.setBounds(673, 204, 297, 38);
		panel_1.add(txt_CurrencyOutput);
		
//		Convert Button
		JButton btn_Convert = new JButton("Convert");
		btn_Convert.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/Login.png")));
		btn_Convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				double USA_Dollar = Double.parseDouble(txt_EnterCurrency.getText());
				
				if (box_ChooseCountry.getSelectedItem().equals("Gambia")) {
					double USD_to_Dalasis = USA_Dollar * Gambian_Dalasis;
					String convert = String.format("D %.2f", USD_to_Dalasis);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("Nigeria")) {
					
					double USD_to_Naira = USA_Dollar * Nigerian_Naira;
					String convert = String.format("N %.2f", USD_to_Naira);
					txt_CurrencyOutput.setText(convert);
				}

				if (box_ChooseCountry.getSelectedItem().equals("Kenya")) {
					
					double USD_to_Kenyan_Shilling = USA_Dollar * Kenyan_Shilling;
					String convert = String.format("KS %.2f", USD_to_Kenyan_Shilling);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("China")) {
					
					double USD_to_Chinese_Yuan = USA_Dollar * Chinese_Yuan;
					String convert = String.format("CY %.2f", USD_to_Chinese_Yuan);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("India")) {
					
					double USD_to_Indian_Rupee = USA_Dollar * Indian_Rupee;
					String convert = String.format("INT %.2f", USD_to_Indian_Rupee);
					txt_CurrencyOutput.setText(convert);
				}
			}
		});
		btn_Convert.setFont(new Font("Dialog", Font.BOLD, 20));
		btn_Convert.setBounds(669, 283, 157, 30);
		panel_1.add(btn_Convert);
		
//		Currency Converter Reset Button
		JButton btn_Reset2 = new JButton("Reset");
		btn_Reset2.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/delete.png")));
		btn_Reset2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txt_CurrencyOutput.setText(null);
				txt_EnterCurrency.setText(null);
				box_ChooseCountry.setSelectedItem("Country");
			}
		});
		btn_Reset2.setFont(new Font("Dialog", Font.BOLD, 20));
		btn_Reset2.setBounds(828, 283, 142, 30);
		panel_1.add(btn_Reset2);
		
		box_ChooseFood = new JComboBox();
		box_ChooseFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (box_ChooseFood.getSelectedIndex()==0) {
					lbl_food_price.setText("0");
				}
				
				try {
					
//					String query = "SELECT * FROM foods WHERE id = ?";
					String query = "SELECT * FROM foods WHERE name = ?";
					
					PreparedStatement pst = connection.prepareStatement(query);
					
//					String price = (String) box_ChooseFood.getSelectedItem();
//					pst.setInt(1, Integer.parseInt(price) );
					pst.setString(1, (String) box_ChooseFood.getSelectedItem() );
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						
						lbl_food_price.setText(rs.getString("price"));
					}
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
		box_ChooseFood.setFont(new Font("Dialog", Font.PLAIN, 25));
		box_ChooseFood.setBounds(25, 80, 201, 36);
		panel_1.add(box_ChooseFood);
		
		box_quantity_of_food = new JComboBox();
		box_quantity_of_food.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		box_quantity_of_food.setFont(new Font("Dialog", Font.PLAIN, 25));
		box_quantity_of_food.setBounds(356, 80, 75, 36);
		panel_1.add(box_quantity_of_food);
		
		box_quantity_of_drink = new JComboBox();
		box_quantity_of_drink.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		box_quantity_of_drink.setFont(new Font("Dialog", Font.PLAIN, 25));
		box_quantity_of_drink.setBounds(356, 166, 75, 36);
		panel_1.add(box_quantity_of_drink);
		
		
		all_foods = new ArrayList<String>();
		all_food_quantity = new ArrayList<Integer>();
		all_food_price = new ArrayList<Double>();		
			
		btn_Submit_1 = new JButton("Add Food");
		btn_Submit_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				if (box_ChooseFood.getSelectedIndex() == 0 || box_quantity_of_food.getSelectedIndex()==0) {
					
					JOptionPane.showMessageDialog(null, "Please Select Food and Quantity of Food before adding", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else {
					
					foods = new ArrayList<String>();
					food_quantity = new ArrayList<Integer>();
					food_price = new ArrayList<Double>();
					
					String food = (String) box_ChooseFood.getSelectedItem();
					foods.add(food);
					
					String quantity = (String) box_quantity_of_food.getSelectedItem();
					food_quantity.add(Integer.parseInt(quantity));
					
					String price = lbl_food_price.getText().toString();
					food_price.add(Double.parseDouble(price));
					
//					Add to the array of all_foods
					all_foods.add(food);
					all_food_quantity.add(Integer.parseInt(quantity));
					all_food_price.add(Double.parseDouble(price));
					
//					Add to total_food
					double t = Double.parseDouble(price) + Integer.parseInt(quantity);
					total_foods += t;

					for (String f : foods) {
						
						textArea_confirm_sales.append(f + "\t\t");
					}
					
					for (Integer q : food_quantity) {
						
						textArea_confirm_sales.append(""+q+"\t\t");
					}	
					
					for (Double p : food_price) {
						
						textArea_confirm_sales.append(""+p+"\n");
					}	
				}
				
			}
		});
		btn_Submit_1.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/addnew.png")));
		btn_Submit_1.setFont(new Font("Dialog", Font.BOLD, 20));
		btn_Submit_1.setBounds(464, 83, 179, 30);
		panel_1.add(btn_Submit_1);
		
		
		all_drinks = new ArrayList<String>();
		all_drink_quantity = new ArrayList<Integer>();
		all_drink_price = new ArrayList<Double>();	
		
		btn_Submit_1_1 = new JButton("Add Drink");
		btn_Submit_1_1.addActionListener(new ActionListener() {

			

			public void actionPerformed(ActionEvent arg0) {
				
				if (box_ChooseDrink.getSelectedIndex() == 0 || box_quantity_of_drink.getSelectedIndex()==0) {
					
					JOptionPane.showMessageDialog(null, "Please Select Drink and Quantity of Drink before adding", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else {
					
					
					drinks = new ArrayList<String>();
					drink_quantity = new ArrayList<Integer>();
					drink_price = new ArrayList<Double>();
					
					String drink = (String) box_ChooseDrink.getSelectedItem();
					drinks.add(drink);
					
					String quantity = (String) box_quantity_of_drink.getSelectedItem();
					drink_quantity.add(Integer.parseInt(quantity));
					
					String price = lbl_drink_price.getText().toString();
					drink_price.add(Double.parseDouble(price));
					
					
//					Add to the array of all_drinks
					all_drinks.add(drink);
					all_drink_quantity.add(Integer.parseInt(quantity));
					all_drink_price.add(Double.parseDouble(price));
					
//					Add to total_drink
					double t = Double.parseDouble(price) + Integer.parseInt(quantity);
					total_drinks += t;
					
					for (String d : drinks) {
					
						textArea_confirm_sales.append(d + "\t\t");
					}
					
					for (Integer q : drink_quantity) {
						
						textArea_confirm_sales.append(""+q+"\t\t");
					}	
					
					for (Double p : drink_price) {
						
						textArea_confirm_sales.append(""+p+"\n");
					}	
				}
			}
		});
		btn_Submit_1_1.setIcon(new ImageIcon(RestaurantBilling.class.getResource("/img/addnew.png")));
		btn_Submit_1_1.setFont(new Font("Dialog", Font.BOLD, 20));
		btn_Submit_1_1.setBounds(464, 172, 179, 30);
		panel_1.add(btn_Submit_1_1);
		
		JLabel lblPrice = new JLabel("Price$");
		lblPrice.setForeground(Color.BLUE);
		lblPrice.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblPrice.setBounds(224, 12, 125, 36);
		panel_1.add(lblPrice);
		
		lbl_food_price = new JLabel("-");
		lbl_food_price.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_food_price.setFont(new Font("Dialog", Font.BOLD, 20));
		lbl_food_price.setBounds(244, 80, 70, 28);
		panel_1.add(lbl_food_price);
		
		lbl_drink_price = new JLabel("-");
		lbl_drink_price.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_drink_price.setFont(new Font("Dialog", Font.BOLD, 20));
		lbl_drink_price.setBounds(244, 166, 70, 28);
		panel_1.add(lbl_drink_price);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(486, 502, 1002, 254);
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 10));
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel label_TotalCostOfMeal = new JLabel("Total Cost of Meal");
		label_TotalCostOfMeal.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_TotalCostOfMeal.setBounds(25, 24, 224, 30);
		panel_1_1.add(label_TotalCostOfMeal);
		
		label_TotalCostOfDrink = new JLabel("Total Cost of Drink");
		label_TotalCostOfDrink.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_TotalCostOfDrink.setBounds(25, 89, 240, 30);
		panel_1_1.add(label_TotalCostOfDrink);
		
		JLabel label_CostOfDelivery = new JLabel("Cost of Delivery");
		label_CostOfDelivery.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_CostOfDelivery.setBounds(25, 168, 224, 30);
		panel_1_1.add(label_CostOfDelivery);
		
		txt_TotalCostOfMeal = new JLabel("");
		txt_TotalCostOfMeal.setFont(new Font("Dialog", Font.BOLD, 20));
		txt_TotalCostOfMeal.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_TotalCostOfMeal.setBounds(298, 24, 297, 38);
		panel_1_1.add(txt_TotalCostOfMeal);
		
		txt_TotalCostOfDrink = new JLabel("");
		txt_TotalCostOfDrink.setFont(new Font("Dialog", Font.BOLD, 20));
		txt_TotalCostOfDrink.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_TotalCostOfDrink.setBounds(298, 89, 297, 38);
		panel_1_1.add(txt_TotalCostOfDrink);
		
		txt_delivery = new JLabel("");
		txt_delivery.setFont(new Font("Dialog", Font.BOLD, 20));
		txt_delivery.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_delivery.setBounds(298, 160, 297, 38);
		panel_1_1.add(txt_delivery);
		
		JSeparator separator_3_2 = new JSeparator();
		separator_3_2.setOrientation(SwingConstants.VERTICAL);
		separator_3_2.setBounds(603, 12, 2, 227);
		panel_1_1.add(separator_3_2);
		
		label_Tax = new JLabel("Tax");
		label_Tax.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_Tax.setBounds(630, 82, 123, 30);
		panel_1_1.add(label_Tax);
		
		label_SubTotal = new JLabel("Sub Total");
		label_SubTotal.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_SubTotal.setBounds(630, 132, 123, 30);
		panel_1_1.add(label_SubTotal);
		
		label_Total = new JLabel("Total");
		label_Total.setFont(new Font("Dialog", Font.PLAIN, 25));
		label_Total.setBounds(630, 187, 123, 30);
		panel_1_1.add(label_Total);
		
		txt_Tax = new JLabel("");
		txt_Tax.setFont(new Font("Dialog", Font.BOLD, 25));
		txt_Tax.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_Tax.setBounds(765, 74, 210, 38);
		panel_1_1.add(txt_Tax);
		
		txt_SubTotal = new JLabel("");
		txt_SubTotal.setFont(new Font("Dialog", Font.BOLD, 25));
		txt_SubTotal.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_SubTotal.setBounds(765, 132, 210, 38);
		panel_1_1.add(txt_SubTotal);
		
		txt_Total = new JLabel("");
		txt_Total.setFont(new Font("Dialog", Font.BOLD, 30));
		txt_Total.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		txt_Total.setBounds(765, 179, 210, 38);
		panel_1_1.add(txt_Total);
		

		lblClock = new JLabel("Clock :");
		lblClock.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClock.setBounds(629, 12, 361, 42);
		panel_1_1.add(lblClock);
		
		JLabel lblNewLabel = new JLabel("SENEGAMBIA RESTAURANT BILLING SYSTEM");
		lblNewLabel.setBounds(496, 45, 992, 73);
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 36));
		frame.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1488, 44);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mnNew);
		
		JMenuItem mntmReceipt = new JMenuItem("Receipt");
		mntmReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				receipt_Area.append("\n\t SENEGAMBIA RESTAURANT: \n\n");
				receipt_Area.append(textArea_confirm_sales.getText());
				tabbedPane.setSelectedComponent(receipt);
				
			}
		});
		mntmReceipt.setFont(new Font("Dialog", Font.BOLD, 20));
		mnNew.add(mntmReceipt);
		
//		Reset Menu Item
		JMenuItem mntmReset = new JMenuItem("Reset");
		mntmReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txt_TotalCostOfMeal.setText(null);
				txt_TotalCostOfDrink.setText(null);
				txt_delivery.setText(null);
				txt_Tax.setText(null);
				txt_SubTotal.setText(null);
				txt_Total.setText(null);
				txt_EnterCurrency.setText(null);
				txt_CurrencyOutput.setText(null);
				calculator_display.setText(null);
				box_ChooseDrink.setSelectedItem("Choose a drink");
				box_ChooseCountry.setSelectedItem("Country");		
				receipt_Area.setText(null);
			}
		});
		mntmReset.setFont(new Font("Dialog", Font.BOLD, 20));
		mnNew.add(mntmReset);
		
		JMenuItem mntmConvert = new JMenuItem("Convert");
		mntmConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double USA_Dollar = Double.parseDouble(txt_EnterCurrency.getText());
				
				if (box_ChooseCountry.getSelectedItem().equals("Gambia")) {
					double USD_to_Dalasis = USA_Dollar * Gambian_Dalasis;
					String convert = String.format("D %.2f", USD_to_Dalasis);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("Nigeria")) {
					
					double USD_to_Naira = USA_Dollar * Nigerian_Naira;
					String convert = String.format("N %.2f", USD_to_Naira);
					txt_CurrencyOutput.setText(convert);
				}

				if (box_ChooseCountry.getSelectedItem().equals("Kenya")) {
					
					double USD_to_Kenyan_Shilling = USA_Dollar * Kenyan_Shilling;
					String convert = String.format("KS %.2f", USD_to_Kenyan_Shilling);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("China")) {
					
					double USD_to_Chinese_Yuan = USA_Dollar * Chinese_Yuan;
					String convert = String.format("CY %.2f", USD_to_Chinese_Yuan);
					txt_CurrencyOutput.setText(convert);
				}
				
				if (box_ChooseCountry.getSelectedItem().equals("India")) {
					
					double USD_to_Indian_Rupee = USA_Dollar * Indian_Rupee;
					String convert = String.format("INT %.2f", USD_to_Indian_Rupee);
					txt_CurrencyOutput.setText(convert);
				}
			}
		});
		mntmConvert.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmConvert);
		
		JMenuItem mntmTotal = new JMenuItem("Total");
		mntmTotal.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmTotal);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmTotal_1 = new JMenuItem("Total");
		mntmTotal_1.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmTotal_1);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txt_TotalCostOfMeal.setText(null);
				txt_TotalCostOfDrink.setText(null);
				txt_delivery.setText(null);
				txt_Tax.setText(null);
				txt_SubTotal.setText(null);
				txt_Total.setText(null);
				txt_EnterCurrency.setText(null);
				txt_CurrencyOutput.setText(null);
				calculator_display.setText(null);
				box_ChooseDrink.setSelectedItem("Choose a drink");
				box_ChooseCountry.setSelectedItem("Country");		
				receipt_Area.setText(null);
			}
		});
		mntmRefresh.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmRefresh);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mntmExit.setForeground(Color.RED);
		mntmExit.setFont(new Font("Dialog", Font.BOLD, 20));
		mnFile.add(mntmExit);
		
		JMenu mnConvert = new JMenu("Convert");
		mnConvert.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnConvert);
		
		JMenu mnTotal = new JMenu("Total");
		mnTotal.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnTotal);
		
		JMenu mnReset = new JMenu("Reset");
		mnReset.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnReset);
		
		JMenu mnReceipt = new JMenu("Receipt");
		mnReceipt.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnReceipt);
		
		JMenu mnExit = new JMenu("Exit");
		mnExit.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(mnExit);
	}
}
