
import java.sql.*;
import javax.swing.*;

public class sqliteConnection {

	
	Connection conn = null;
	
	
//	Makes it possible to access the method outside the class without creating an instance of the class(Connection is a data type from sql librsry)
	public static Connection dbConnector() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
			JOptionPane.showMessageDialog(null, "Successfully Connected to Database!");
			return conn;
		} 
		catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
//	public static void main(String[] args) {
//		new sqliteConnection().dbConnector();
//	}
}
