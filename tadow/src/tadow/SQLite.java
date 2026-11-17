package tadow;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

	public static int getUserID(String username) {
		int userID = -1;
		if (username == null) {
			return userID;
		}
		String sql = "SELECT id FROM users WHERE name = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
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
		String sql = "SELECT id FROM lists WHERE name = ? AND user_id = ?";

		try (Connection conn = DriverManager.getConnection(URL);
				var pstmt = conn.prepareStatement(sql)) {
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
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return tasks;
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