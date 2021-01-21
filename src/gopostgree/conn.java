package gopostgree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class conn {
	public conn()
	{
		testDatabase();
	}
	
	 private void testDatabase() {
	        try {
	            Class.forName("org.postgresql.Driver");
	            String url = "jdbc:postgresql://localhost:5432/postgres";
	            String login = "postgres";
	            String password = "123";
	            Connection con = DriverManager.getConnection(url, login, password);
	            try {
	            	
	                Statement stmt = con.createStatement();
	                ResultSet rs = stmt.executeQuery(" SELECT * FROM weather");
	                while (rs.next()) {
	                    String str = rs.getString("city") + ":" + rs.getString(2);
	                    System.out.println("city:" + str);
	                }
	                rs.close();
	                stmt.close();
	                
	            } finally {
	                con.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
