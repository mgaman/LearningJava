import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
*
* @author sqlitetutorial.net
*/
class SelectApp {
   /**
    * Connect to the test.db database
    * @return the Connection object
    */
   private Connection connect() {
       // SQLite connection string
       String url = "jdbc:sqlite:src/Data/Contacts";
       Connection conn = null;
       try {
           conn = DriverManager.getConnection(url);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return conn;
   }

   
   /**
    * select all rows in the warehouses table
    */
   public void selectAll(){
       String sql = "SELECT * FROM info";
       
       try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
           
           // loop through the result set
           while (rs.next()) {
               System.out.println(rs.getString("name") +  "\t" + 
                                  rs.getString("number"));
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
   public void insert(String name,String number)
   {
	   String sql = "INSERT INTO info(name,number) VALUES(?,?)";
	   try (Connection conn = this.connect();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           pstmt.setString(2, number);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
   public void update(String name, String newnumber) {
       String sql = "UPDATE info SET number = ?  "
               + "WHERE name = ?";

       try (Connection conn = this.connect();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {
           // set the corresponding param
           pstmt.setString(1, newnumber);
           pstmt.setString(2, name);
           // update 
           pstmt.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
   public void delete(String name) {
       String sql = "DELETE FROM info WHERE name = ?";

       try (Connection conn = this.connect();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {

           // set the corresponding param
           pstmt.setString(1, name);
           // execute the delete statement
           pstmt.executeUpdate();

       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
}
public class SQLTest {

	public static void main(String[] args) {
		SelectApp app = new SelectApp();
//		app.insert("saba", "0545919886");
 //       app.insert("safta", "0545289886");
   //     app.insert("naomi", "05256585"); 
    //    app.update("naomi", "99999"); 
        app.delete("naomi");
        app.selectAll();
    }
}
