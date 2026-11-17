package tadow;

<<<<<<< HEAD
import java.sql.Date;
=======
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;

public class SQLite {
	
	private static final String DB_URL = "jdbc:sqlite:tadow.db";
	
	public static void connect() {
		//																												DATABASE CREATION
		// This method will create a new database if it does not exist, otherwise connects to the existing database
        // connection string
        
        // Attempt to connect/create database
        try (var conn = DriverManager.getConnection(DB_URL)) {
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
        try (var conn = DriverManager.getConnection(DB_URL);
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
	 try (var conn = DriverManager.getConnection(DB_URL);
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
	 try (var conn = DriverManager.getConnection(DB_URL);
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
		boolean signupstatus = false;

        // SQL statement for inserting user
        String udatasql = "INSERT INTO users(name,password) VALUES(?,?)";
        
        try (var conn = DriverManager.getConnection(DB_URL);
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
		
		// SQL statement for checking user
		String lsql = "SELECT * FROM users WHERE name = ? AND password = ?";
		
		try {
			HashMap<Integer, Object> args = new HashMap<>();
			args.put(1, username);
			args.put(2, password);
			var rs = queryDatabase(lsql, args);
			
			if (!rs.isEmpty()) {
				System.out.println("Login successful!");
				loginstatus = true;
				// Set the logged-in user's name in the UserSession
				UserSession.setUserName(username);
			} else {
				System.out.println("Login failed!");
			}
			
=======
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SQLite {

	// foreign_keys=on makes SQLite actually enforce the FOREIGN KEY clauses (it is off by default)
	private static final String URL = "jdbc:sqlite:tadow.db?foreign_keys=on";

	private static final String HASH_PREFIX = "pbkdf2$";
	private static final int HASH_ITERATIONS = 65_000;

	/**
	 * Tracks which user is logged in.
	 */
	public static class UserSession {
		private static String loggedInUserName;

		public static void setUserName(String username) {
			loggedInUserName = username;
		}

		public static String getUserName() {
			return loggedInUserName;
		}

		public static void clear() {
			loggedInUserName = null;
		}
	}

	// ------------------------------------------------------------------
	// Setup
	// ------------------------------------------------------------------

	/**
	 * Creates the database and tables if they do not exist, and upgrades
	 * databases created by older versions (adds the columns that hold
	 * list sharing and task repeat info).
	 */
	public static void connect() {
		String usersSql = "CREATE TABLE IF NOT EXISTS users ("
				+ " id INTEGER PRIMARY KEY,"
				+ " name TEXT NOT NULL UNIQUE,"
				+ " password TEXT NOT NULL,"
				+ " capacity REAL"
				+ ");";

		String listsSql = "CREATE TABLE IF NOT EXISTS lists ("
				+ " id INTEGER PRIMARY KEY,"
				+ " name TEXT NOT NULL,"
				+ " description TEXT,"
				+ " shared INTEGER NOT NULL DEFAULT 0,"
				+ " team_name TEXT NOT NULL DEFAULT '',"
				+ " user_id INTEGER NOT NULL,"
				+ " FOREIGN KEY(user_id) REFERENCES users(id)"
				+ ");";

		String tasksSql = "CREATE TABLE IF NOT EXISTS tasks ("
				+ " id INTEGER PRIMARY KEY,"
				+ " title TEXT NOT NULL,"
				+ " description TEXT,"
				+ " due_date TEXT,"
				+ " status INTEGER NOT NULL,"
				+ " is_repeating INTEGER NOT NULL DEFAULT 0,"
				+ " rep_interval TEXT NOT NULL DEFAULT '',"
				+ " list_id INTEGER NOT NULL,"
				+ " FOREIGN KEY(list_id) REFERENCES lists(id)"
				+ ");";

		try (Connection conn = DriverManager.getConnection(URL);
				Statement stmt = conn.createStatement()) {
			var meta = conn.getMetaData();
			System.out.println("The driver name is " + meta.getDriverName());
			System.out.println("Database connection established.");

			stmt.execute(usersSql);
			stmt.execute(listsSql);
			stmt.execute(tasksSql);

			// Upgrade an older tadow.db that was created without these columns
			addColumnIfMissing(conn, "lists", "shared", "INTEGER NOT NULL DEFAULT 0");
			addColumnIfMissing(conn, "lists", "team_name", "TEXT NOT NULL DEFAULT ''");
			addColumnIfMissing(conn, "tasks", "is_repeating", "INTEGER NOT NULL DEFAULT 0");
			addColumnIfMissing(conn, "tasks", "rep_interval", "TEXT NOT NULL DEFAULT ''");
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void addColumnIfMissing(Connection conn, String table, String column, String definition)
			throws SQLException {
		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("PRAGMA table_info(" + table + ")")) {
			while (rs.next()) {
				if (column.equalsIgnoreCase(rs.getString("name"))) {
					return; // already there
				}
			}
		}
		try (Statement st = conn.createStatement()) {
			st.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + definition);
			System.out.println("Upgraded database: added " + table + "." + column);
		}
	}

	// ------------------------------------------------------------------
	// Passwords (salted PBKDF2). Old plain-text passwords still work and are
	// upgraded to a hash the next time that user logs in.
	// ------------------------------------------------------------------

	private static byte[] pbkdf2(String password, byte[] salt, int iterations) throws GeneralSecurityException {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 256);
		return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
	}

	private static String hashPassword(String password) {
		try {
			byte[] salt = new byte[16];
			new SecureRandom().nextBytes(salt);
			byte[] hash = pbkdf2(password, salt, HASH_ITERATIONS);
			return HASH_PREFIX + HASH_ITERATIONS + "$" + Base64.getEncoder().encodeToString(salt) + "$"
					+ Base64.getEncoder().encodeToString(hash);
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Password hashing unavailable", e);
		}
	}

	private static boolean verifyPassword(String password, String stored) {
		if (stored == null) {
			return false;
		}
		if (!stored.startsWith(HASH_PREFIX)) {
			return stored.equals(password); // legacy plain-text row
		}
		String[] parts = stored.split("\\$");
		if (parts.length != 4) {
			return false;
		}
		try {
			int iterations = Integer.parseInt(parts[1]);
			byte[] salt = Base64.getDecoder().decode(parts[2]);
			byte[] expected = Base64.getDecoder().decode(parts[3]);
			return MessageDigest.isEqual(expected, pbkdf2(password, salt, iterations));
		} catch (GeneralSecurityException | IllegalArgumentException e) {
			return false;
		}
	}

	/** The id SQLite gave the row just inserted on this connection. */
	private static int lastInsertId(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
			return rs.next() ? rs.getInt(1) : -1;
		}
	}

	// ------------------------------------------------------------------
	// Users
	// ------------------------------------------------------------------

	public static boolean signup(String username, String password) {
		if (username == null || username.isBlank() || password == null || password.isEmpty()) {
			return false;
		}

		String sql = "INSERT INTO users(name,password) VALUES(?,?)";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username.trim());
			pstmt.setString(2, hashPassword(password));
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// Includes the UNIQUE violation when the username already exists
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean login(String username, String password) {
		if (username == null || password == null) {
			return false;
		}
		String sql = "SELECT id, password FROM users WHERE name = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			int id;
			String stored;
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("Login failed!");
					return false;
				}
				id = rs.getInt("id");
				stored = rs.getString("password");
			}

			if (!verifyPassword(password, stored)) {
				System.out.println("Login failed!");
				return false;
			}

			if (!stored.startsWith(HASH_PREFIX)) {
				try (var up = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
					up.setString(1, hashPassword(password));
					up.setInt(2, id);
					up.executeUpdate();
				}
			}

			System.out.println("Login successful!");
			UserSession.setUserName(username);
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean removeAccount(String username, String password) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
<<<<<<< HEAD
	
	public static class UserSession {
	    private static String loggedInUserName;
=======
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb

	public static int getUserID(String username) {
		int userID = -1;
<<<<<<< HEAD
		
		// SQL statement for getting user ID
		String sql = "SELECT id FROM users WHERE name = ?";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(sql)) {
=======
		if (username == null) {
			return userID;
		}
		String sql = "SELECT id FROM users WHERE name = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					userID = rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return userID;
	}
<<<<<<< HEAD
	
	public static void addList(TDList list) {
		// This method will add a new list to the database
		int userID = getUserID(UserSession.getUserName());
		
		// SQL statement for inserting list
		String lsql = "INSERT INTO lists(name,description,user_id) VALUES(?,?,?)";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(lsql)) {
			// insert the new list into the database
=======

	// ------------------------------------------------------------------
	// Lists
	// ------------------------------------------------------------------

	private static TDList listFromRow(ResultSet rs) throws SQLException {
		TDList list = new TDList(rs.getString("name"));
		list.setId(rs.getInt("id"));
		list.setlDesc(rs.getString("description"));
		list.setShared(rs.getInt("shared") == 1);
		list.setTeamName(rs.getString("team_name"));
		return list;
	}

	/**
	 * Adds a list for the logged-in user and stores the new database id on the list.
	 * @return true if the list was saved
	 */
	public static boolean addList(TDList list) {
		int userID = getUserID(UserSession.getUserName());
		if (userID == -1) {
			System.err.println("No logged-in user; list not added.");
			return false;
		}

		String sql = "INSERT INTO lists(name,description,shared,team_name,user_id) VALUES(?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
			pstmt.setString(1, list.getlTitle());
			pstmt.setString(2, list.getlDesc());
			pstmt.setInt(3, list.isShared() ? 1 : 0);
			pstmt.setString(4, list.getTeamName());
			pstmt.setInt(5, userID);
			pstmt.executeUpdate();
			list.setId(lastInsertId(conn));
			System.out.println("List added successfully!");
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static ArrayList<TDList> getListsForUser(String username) {
		ArrayList<TDList> lists = new ArrayList<>();
		int userID = getUserID(username);
<<<<<<< HEAD
		
		// SQL statement for getting lists for a user
		String sql = "SELECT * FROM lists WHERE user_id = ?";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userID);
			var rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TDList list = new TDList(rs.getString("name"));
				list.setlDesc(rs.getString("description"));
				lists.add(list);
			}
			
=======
		if (userID == -1) {
			return lists;
		}
		String sql = "SELECT * FROM lists WHERE user_id = ? ORDER BY name COLLATE NOCASE, id";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userID);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					lists.add(listFromRow(rs));
				}
			}
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return lists;
	}

	/**
	 * Returns the database id of a list. Uses the id stored on the list when
	 * available; otherwise looks it up by name for the logged-in user only.
	 */
	public static int getListID(TDList list) {
		if (list.getId() > 0) {
			return list.getId();
		}

		int listID = -1;
<<<<<<< HEAD
		
		// SQL statement for getting list ID
		String sql = "SELECT id FROM lists WHERE name = ?";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(sql)) {
=======
		String sql = "SELECT id FROM lists WHERE name = ? AND user_id = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
			pstmt.setString(1, list.getlTitle());
			pstmt.setInt(2, getUserID(UserSession.getUserName()));
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					listID = rs.getInt("id");
					list.setId(listID);
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return listID;
	}
<<<<<<< HEAD
	
	public static void addTask(Task task, TDList list) {
		// This method will add a new task to the database
		
		// SQL statement for inserting task
		String tsql = "INSERT INTO tasks(title,description,due_date,status,list_id) VALUES(?,?,?,?,?)";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(tsql)) {
			// insert the new task into the database
			pstmt.setString(1, task.gettTitle());
			pstmt.setString(2, task.gettDesc());
			pstmt.setString(3, task.gettDate().toString());
			pstmt.setInt(4, task.gettStat());
			pstmt.setInt(5, getListID(list));
			pstmt.executeUpdate();
			System.out.println("Task added successfully!");
			
=======

	/** Saves the name, description and sharing info of an existing list. */
	public static boolean updateList(TDList list) {
		int listID = getListID(list);
		if (listID == -1) {
			return false;
		}
		String sql = "UPDATE lists SET name = ?, description = ?, shared = ?, team_name = ? WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, list.getlTitle());
			pstmt.setString(2, list.getlDesc());
			pstmt.setInt(3, list.isShared() ? 1 : 0);
			pstmt.setString(4, list.getTeamName());
			pstmt.setInt(5, listID);
			return pstmt.executeUpdate() == 1;
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	/** Deletes a list and all of its tasks. */
	public static boolean deleteList(TDList list) {
		int listID = getListID(list);
		if (listID == -1) {
			return false;
		}

		try (Connection conn = DriverManager.getConnection(URL)) {
			conn.setAutoCommit(false);
			try (var delTasks = conn.prepareStatement("DELETE FROM tasks WHERE list_id = ?");
					var delList = conn.prepareStatement("DELETE FROM lists WHERE id = ?")) {
				delTasks.setInt(1, listID);
				delTasks.executeUpdate();
				delList.setInt(1, listID);
				delList.executeUpdate();
				conn.commit();
				return true;
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	// ------------------------------------------------------------------
	// Tasks
	// ------------------------------------------------------------------

	/**
	 * Adds a task to a list and stores the new database id on the task.
	 * @return true if the task was saved
	 */
	public static boolean addTask(Task task, TDList list) {
		int listID = getListID(list);
		if (listID == -1) {
			System.err.println("List not found; task not added.");
			return false;
		}

		String sql = "INSERT INTO tasks(title,description,due_date,status,is_repeating,rep_interval,list_id)"
				+ " VALUES(?,?,?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, task.gettTitle());
			pstmt.setString(2, task.gettDesc());
			pstmt.setString(3, task.gettDate());
			pstmt.setInt(4, task.gettStat());
			pstmt.setInt(5, task.isRep() ? 1 : 0);
			pstmt.setString(6, task.getRepInterval());
			pstmt.setInt(7, listID);
			pstmt.executeUpdate();
			task.setId(lastInsertId(conn));
			System.out.println("Task added successfully!");
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static ArrayList<Task> getTasksForList(TDList list) {
		ArrayList<Task> tasks = new ArrayList<>();
<<<<<<< HEAD
		
		// SQL statement for getting tasks for a list
		String sql = "SELECT * FROM tasks WHERE list_id = ?";
		
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, getListID(list));
			var rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Task task = new Task(rs.getString("title"));
				task.settDesc(rs.getString("description"));
				task.settDate(new java.util.Date(rs.getString("due_date")));
				task.settStat(rs.getInt("status"));
				tasks.add(task);
=======
		int listID = getListID(list);
		if (listID == -1) {
			return tasks;
		}
		// Unfinished tasks first, then completed ones; each group in the order created
		String sql = "SELECT * FROM tasks WHERE list_id = ? ORDER BY (status = 2), id";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, listID);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Task task = new Task(rs.getString("title"));
					task.setId(rs.getInt("id"));
					task.settDesc(rs.getString("description"));
					task.settDate(rs.getString("due_date"));
					task.settStat(rs.getInt("status"));
					task.setRep(rs.getInt("is_repeating") == 1);
					task.setRepInterval(rs.getString("rep_interval"));
					tasks.add(task);
				}
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return tasks;
	}
	
	/**
	 *
	 * Call a SQL query on the database
	 * @param query
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	private static ArrayList<Object> queryDatabase(String query, HashMap<Integer, Object> args) throws SQLException {
		try (var conn = DriverManager.getConnection(DB_URL);
			var pstmt = conn.prepareStatement(query)) {
			
			// Set each argument in the query
			for (Integer i: args.keySet()) {
				Object val = args.get(i);
				if (val instanceof Integer)
					pstmt.setInt(i, (int) val);
				else if (val instanceof String)
					pstmt.setString(i, (String) val);
				else if (val instanceof Double)
					pstmt.setDouble(i, (double) val);
				else if (val instanceof Boolean)
					pstmt.setBoolean(0, (boolean) val);
				else if (val instanceof Date)
					pstmt.setDate(0, (Date) val);
			}
			
			var rs = pstmt.executeQuery();
			var results = new ArrayList<Object>();
			
			int i = 1;
			while (rs.next()) {
				Object val = rs.getObject(i);
				if (val instanceof Integer)
					results.add(rs.getInt(i));
				else if (val instanceof String)
					results.add(rs.getString(i));
				else if (val instanceof Double)
					results.add(rs.getDouble(i));
				else if (val instanceof Boolean)
					results.add(rs.getBoolean(i));
				else if (val instanceof Date)
					results.add(rs.getInt(i));
				else
					results.add(null);
				i++;
			}
			
			return results;
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/** Saves every editable field of an existing task (including its status). */
	public static boolean updateTask(Task task) {
		if (task.getId() <= 0) {
			return false;
		}
		String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ?,"
				+ " is_repeating = ?, rep_interval = ? WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, task.gettTitle());
			pstmt.setString(2, task.gettDesc());
			pstmt.setString(3, task.gettDate());
			pstmt.setInt(4, task.gettStat());
			pstmt.setInt(5, task.isRep() ? 1 : 0);
			pstmt.setString(6, task.getRepInterval());
			pstmt.setInt(7, task.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean deleteTask(Task task) {
		if (task.getId() <= 0) {
			return false;
		}
		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
			pstmt.setInt(1, task.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

}