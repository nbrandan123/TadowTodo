package tadow;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLite {
	
	public static void connect() {
		//																												DATABASE CREATION
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
        // 																												USEER TABLE CREATION
		// Create user table if doesn't exist
		// SQL statement for creating a new table
        var tablesql = "CREATE TABLE IF NOT EXISTS users ("
                + "	id INTEGER PRIMARY KEY,"
                + "	name text NOT NULL,"
                + " password text NOT NULL,"
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
    //    String sqlUsername = "ALTER TABLE users ADD COLUMN username TEXT;";
    //    String sqlPassword = "ALTER TABLE users ADD COLUMN password TEXT;";

    // try (var conn = DriverManager.getConnection(url);
    //     var stmt = conn.createStatement()) {
    //     stmt.execute(sqlUsername);
    //     stmt.execute(sqlPassword);
    //     System.out.println("Username and password columns added successfully.");
    // } catch (SQLException e) {
    //	 if (e.getMessage().contains("duplicate column name")) {
     //        System.out.println("Username and Password column already exists. Skipping.");
     //    } else {
     //        // Something else went wrong, handle or rethrow it
     //        e.printStackTrace();
     //    }
     //}
     // 																												TO DO LIST TABLE CREATION
     // Create a new table for To Do Lists if it doesn't already exist
     // SQL Statement for creating new table
     String sqlLists = "CREATE TABLE IF NOT EXISTS lists ("
			 + "	id INTEGER PRIMARY KEY,"
			 + "	name TEXT NOT NULL,"
			 + " 	description TEXT,"
			 + "	user_id INTEGER NOT NULL,"
			 + "	FOREIGN KEY(user_id) REFERENCES users(id)"
			 + ");";
     
     // Create table if it doesn't already exist
	 try (var conn = DriverManager.getConnection(url);
			 var stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sqlLists);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	
	// }
	 
	// 																												TASK TABLE CREATION
	 // Create a new table for Tasks if it doesn't already exist
	 // SQL Statement for creating new table
	 String sqlTasks = "CREATE TABLE IF NOT EXISTS tasks ("
			 + "	id INTEGER PRIMARY KEY,"
			 + "	title TEXT NOT NULL,"
			 + " 	description TEXT,"
			 + "	due_date TEXT,"
			 + "	status INTEGER NOT NULL,"
			 + "	list_id INTEGER NOT NULL,"
			 + "	FOREIGN KEY(list_id) REFERENCES lists(id)"
			 + ");";
	 // Create table if it doesn't already exist
	 try (var conn = DriverManager.getConnection(url);
			 var stmt = conn.createStatement()) {
		 // create a new table
		 stmt.execute(sqlTasks);
	 }
	 catch (SQLException e) {
		 System.out.println(e.getMessage());
	 }
	 
    }
	
	public static boolean signup(String username, String password) {
		// Connect to the database
		var url = "jdbc:sqlite:tadow.db";
		boolean signupstatus = false;

        // SQL statement for inserting user
        String udatasql = "INSERT INTO users(name,password) VALUES(?,?)";
        
        try (var conn = DriverManager.getConnection(url);
            var pstmt = conn.prepareStatement(udatasql)) {
        	// insert the new user into the database
        	pstmt.setString(1, username);
        	pstmt.setString(2, password);
        	pstmt.executeUpdate();
        	signupstatus = true;
        	
        } catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        return signupstatus;
	}
	
	public static boolean login(String username, String password) {
		// This method will check if the user exists in the database
		boolean loginstatus = false;
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
				loginstatus = true;
				// Set the logged-in user's name in the UserSession
				UserSession.setUserName(username);
			} else {
				System.out.println("Login failed!");
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return loginstatus;
	}
	
	public static boolean removeAccount(String username, String password) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public class UserSession {
	    private static String loggedInUserName;

	    public static void setUserName(String username) {
	        loggedInUserName = username;
	    }

	    public static String getUserName() {
	        return loggedInUserName;
	    }
	}
		
	public static int getUserID(String username) {
		// This method will return the user ID for a given username
		int userID = -1;
		var url = "jdbc:sqlite:tadow.db";
		
		// SQL statement for getting user ID
		String sql = "SELECT id FROM users WHERE name = ?";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			var rs = pstmt.executeQuery();
			
			if (rs.next()) {
				userID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return userID;
	}
	
	public static void addList(TDList list) {
		// This method will add a new list to the database
		var url = "jdbc:sqlite:tadow.db";
		int userID = getUserID(UserSession.getUserName());
		
		// SQL statement for inserting list
		String lsql = "INSERT INTO lists(name,description,user_id) VALUES(?,?,?)";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(lsql)) {
			// insert the new list into the database
			pstmt.setString(1, list.getlTitle());
			pstmt.setString(2, list.getlDesc());
			pstmt.setInt(3, userID); // Assuming user_id is 1 for now, this should be dynamic based on logged-in user
			pstmt.executeUpdate();
			System.out.println("List added successfully!");
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	

	
	public static ArrayList<TDList> getListsForUser(String username) {
		// This method will return a list of TDList objects for a given username
		ArrayList<TDList> lists = new ArrayList<>();
		var url = "jdbc:sqlite:tadow.db";
		int userID = getUserID(username);
		
		// SQL statement for getting lists for a user
		String sql = "SELECT * FROM lists WHERE user_id = ?";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userID);
			var rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TDList list = new TDList(rs.getString("name"));
				list.setlDesc(rs.getString("description"));
				lists.add(list);
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return lists;
	}
	
	public static int getListID(TDList list) {
		// This method will return the list ID for a given TDList object
		int listID = -1;
		var url = "jdbc:sqlite:tadow.db";
		
		// SQL statement for getting list ID
		String sql = "SELECT id FROM lists WHERE name = ?";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, list.getlTitle());
			var rs = pstmt.executeQuery();
			
			if (rs.next()) {
				listID = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return listID;
	}
	
	public static void addTask(Task task, TDList list) {
		// This method will add a new task to the database
		var url = "jdbc:sqlite:tadow.db";
		
		// SQL statement for inserting task
		String tsql = "INSERT INTO tasks(title,description,due_date,status,list_id) VALUES(?,?,?,?,?)";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(tsql)) {
			// insert the new task into the database
			pstmt.setString(1, task.gettTitle());
			pstmt.setString(2, task.gettDesc());
			pstmt.setString(3, task.gettDate().toString());
			pstmt.setInt(4, task.gettStat());
			pstmt.setInt(5, getListID(list));
			pstmt.executeUpdate();
			System.out.println("Task added successfully!");
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static ArrayList<Task> getTasksForList(TDList list) {
		// This method will return a list of Task objects for a given TDList object
		ArrayList<Task> tasks = new ArrayList<>();
		var url = "jdbc:sqlite:tadow.db";
		
		// SQL statement for getting tasks for a list
		String sql = "SELECT * FROM tasks WHERE list_id = ?";
		
		try (var conn = DriverManager.getConnection(url);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, getListID(list));
			var rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Task task = new Task(rs.getString("title"));
				task.settDesc(rs.getString("description"));
				task.settDate(new java.util.Date(rs.getString("due_date")));
				task.settStat(rs.getInt("status"));
				tasks.add(task);
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return tasks;
	}

}
