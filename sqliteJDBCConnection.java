import java.sql.*;
import java.util.*;
 
public class sqliteJDBCConnection {
	private Connection c;
	private Statement stmt;
	
	// set connection to the local database
	public sqliteJDBCConnection() {
		try {
	    	Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:foodTable");
	        c.setAutoCommit(false);
	        stmt = c.createStatement();
		} catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	System.exit(0);
		}
	}
	
	// get price, rating and image src based on the restaurant name and food name
	@SuppressWarnings("finally")
	public List<String> fetchData(String restaurant, String food) {
		List<String> res = new ArrayList<String>();
		try {
	        ResultSet rs = stmt.executeQuery( "SELECT * FROM food"
	        		+ " WHERE restaurant = " + restaurant
	        		+ "AND name = " + food + ";");
	        
	        if (rs.next()) {
	            int price  = rs.getInt("price");
	            int rating = rs.getInt("rating");
	            String image = rs.getString("image");
	            
	            res.add(price + "");
	            res.add(rating + "");
	            res.add(image);
	        }
	        rs.close();
        } catch (Exception e) {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	System.exit(0);
	    } finally {
	    	return res;
	    }
    }
	
	// call this function when user finish searching
	public void doneSearch() {
		try {
			c.close();
			stmt.close();
		} catch (SQLException e) {
        	System.err.println(e.getMessage());
        	System.exit(0);
		}
	}
}