package okonkwo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A user of the application
 * 
 * @version 0.1
 * @author Taj Okonkwo
 */
public class User {
	
	// Data Members
	private String username;
	private String password;
	private String displayName;
	
	private boolean isAdmin;
	
	private BufferedImage profilePic;
	
	private String preferences;
	
	private ArrayList<TDList> toDoLists;

	// Constructors
	/**
	 * Explicit constructor | Create new user object
	 * @param username
	 * @param password
	 * @param displayName
	 * @param isAdmin
	 * @param profilePic
	 * @param preferences
	 * @param toDoLists
	 */
	public User(String username, String password, String displayName, boolean isAdmin, BufferedImage profilePic,
			String preferences, ArrayList<TDList> toDoLists) {
		super();
		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.isAdmin = isAdmin;
		this.profilePic = profilePic;
		this.preferences = preferences;
		this.toDoLists = toDoLists;
	}
	
	// Methods
	/**
	 * Log in with a username and password
	 * @param user username
	 * @param pass password
	 */
	public void login(String user, String pass) {
		
	}
	
	/**
	 * Sign up with a username and password
	 * @param user username
	 * @param pass password
	 */
	public void signUp(String user, String pass) {
		
	}
	
	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public BufferedImage getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(BufferedImage profilePic) {
		this.profilePic = profilePic;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public ArrayList<TDList> getToDoLists() {
		return toDoLists;
	}

	public void setToDoLists(ArrayList<TDList> toDoLists) {
		this.toDoLists = toDoLists;
	}

}
