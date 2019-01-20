package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class Window2Controller implements Initializable{

	@FXML TextArea namesList;
	
	Connection c = null;
	Statement stmt = null;
	ResultSet rs = null;
	String path = null;
	String sql;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("Window2 ready");
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:src/data/popup.db");
			System.out.println("Opened database successfully");
			c.setAutoCommit(false);
			stmt = c.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void listDB()
	{
		System.out.println("W2 listDB");
		sql = "select * from names";
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		namesList.clear();
		try {
			while (rs.next()) {
			//	System.out.println(rs.getString("name"));
				namesList.appendText(rs.getString("name") + "\r\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
