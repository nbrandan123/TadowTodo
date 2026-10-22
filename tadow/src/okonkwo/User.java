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
	
	private BufferedImage profilePic;
	
	private String preferences;
	
	private ArrayList<TDList> toDoLists;

	// Constructors
	/**
	 * Explicit constructor | Create new user object
	 * @param username
	 * @param password
	 * @param displayName
	 * @param profilePic
	 * @param preferences
	 * @param toDoLists
	 */
	public User(String username, String password, String displayName, BufferedImage profilePic,
			String preferences, ArrayList<TDList> toDoLists) {
		super();
		this.username = username;
		this.password = password;
		this.displayName = displayName;
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
	/**
	 * Get username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * @param username username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get display name
	 * @return display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set display name
	 * @param displayName display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Get profile picture
	 * @return profile picture
	 */
	public BufferedImage getProfilePic() {
		return profilePic;
	}

	/**
	 * Set profile picture
	 * @param profilePic profile picture
	 */
	public void setProfilePic(BufferedImage profilePic) {
		this.profilePic = profilePic;
	}

	/**
	 * Get preferences
	 * @return preferences
	 */
	public String getPreferences() {
		return preferences;
	}

	/**
	 * Set preferences
	 * @param preferences preferences
	 */
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	/**
	 * Get to-do lists
	 * @return to-do lists
	 */
	public ArrayList<TDList> getToDoLists() {
		return toDoLists;
	}

	/**
	 * Set to-do lists
	 * @param toDoLists to-do lists
	 */
	public void setToDoLists(ArrayList<TDList> toDoLists) {
		this.toDoLists = toDoLists;
	}

}
