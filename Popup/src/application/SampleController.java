package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

import javax.swing.SwingWorker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.*;

public class SampleController  implements Initializable{

	@FXML TextArea namesList;
	
	Connection c = null;
	Statement stmt = null;
	ResultSet rs = null;
	String path = null;
	String sql;
	Window2Controller w2ctl;
	protected static Queue<String> inQ = new LinkedList<String>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	//	System.out.println("listing DB");
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
	public void openWindow2()
	{
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Window2.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
			Stage secondaryStage = new Stage();
			secondaryStage.setTitle("Window2");
            Scene scene = new Scene(root2,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			secondaryStage.setScene(scene);
			secondaryStage.show();
			// get a handle on this windows controller
			Window2Controller w2ctl = fxmlLoader.getController();
			AppendLines listener = new AppendLinesToParent();			
			w2ctl.setListener(listener);
			SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					// TODO Auto-generated method stub
					int x = 100;
					while (x < 1000) {
					//	Thread.sleep(100);
						if (!inQ.isEmpty()) {
							publish(inQ.remove());
						}
					}
					return null;
				}
				// Can safely update the GUI from this method.
				protected void done() {
				}
				@Override
				// Can safely update the GUI from this method.
				protected void process(List<String> chunks) {
					// Here we receive the values that we publish().
					// They may come grouped in chunks.
					// String mostRecentValue = chunks.get(chunks.size()-1);
					for (int i = 0; i < chunks.size(); i++) {
						namesList.appendText(chunks.get(i));
					}
				}
			};
			worker.execute();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
