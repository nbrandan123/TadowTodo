package tadow;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {
	
	public static void connect() {
    // This method will create a new database if it does not exist, otherwise connects to the existing database
        // connection string
        var url = "jdbc:sqlite:tadow.db";
        
        // Attempt to connect/create database
        try (var conn = DriverManager.getConnection(url)) {
        	if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database connection established.");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
		// Create user table if doesn't exist
		// SQL statement for creating a new table
        var tablesql = "CREATE TABLE IF NOT EXISTS users ("
                + "	id INTEGER PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "	capacity REAL"
                + ");";
        
        // create a new table if it doesn't already exist
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement()) {
               // create a new table
               stmt.execute(tablesql);
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
        
		// Add username and password columns to the users table if they don't already exist
        String sqlUsername = "ALTER TABLE users ADD COLUMN username TEXT;";
        String sqlPassword = "ALTER TABLE users ADD COLUMN password TEXT;";

     try (var conn = DriverManager.getConnection(url);
         var stmt = conn.createStatement()) {
         stmt.execute(sqlUsername);
         stmt.execute(sqlPassword);
         System.out.println("Username and password columns added successfully.");
     } catch (SQLException e) {
    	 if (e.getMessage().contains("duplicate column name")) {
             System.out.println("Username and Password column already exists. Skipping.");
         } else {
             // Something else went wrong, handle or rethrow it
             e.printStackTrace();
         }
     }
    }
	
	public static void signup(String username, String password) {
		// Connect to the database
		var url = "jdbc:sqlite:tadow.db";

        // SQL statement for inserting user
        String udatasql = "INSERT INTO users(name,password) VALUES(?,?)";
        
        try (var conn = DriverManager.getConnection(url);
            var pstmt = conn.prepareStatement(udatasql)) {
        	// insert the new user into the database
        	pstmt.setString(1, username);
        	pstmt.setString(2, password);
        	pstmt.executeUpdate();
        	
        } catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        
	}
	
	public static boolean login(String username, String password) {
		// This method will check if the user exists in the database
		boolean status = false;
		// database connection string
		var url = "jdbc:sqlite:tadow.db";
		
		// SQL statement for checking user
		String lsql = "SELECT * FROM users WHERE name = ? AND password = ?";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(lsql)) {
			// check if the user exists in the database
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			var rs = pstmt.executeQuery();
			
			if (rs.next()) {
				System.out.println("Login successful!");
				status = true;
			} else {
				System.out.println("Login failed!");
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

}
