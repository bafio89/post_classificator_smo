package post_classification.stemming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Version {
		
	public static Connection connection() {
			 
		
		 Connection con = null;
	        Statement st = null;
	        ResultSet rs = null;

	        String url = "jdbc:mysql://localhost:3306/qeanalysis";
	        String user = "qbase";
	        String password = "qbase";
	        try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
	            con = DriverManager.getConnection(url, user, password);
	            

	        } catch (SQLException ex) {
	        	 System.out.println(ex);
	        	 
	        	
 	        }  
	        return con;
	}
}
