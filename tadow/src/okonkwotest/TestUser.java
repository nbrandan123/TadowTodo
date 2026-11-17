package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import tadow.User;

/**
 * A test class for {@code User}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestUser {

	/**
	 * Test the constructor
	 */
	@Test
	void testUser() {
		// 1. Instantiate user (pass if no errors)
		User user = new User("testUser", "testPass", "Test User", null, null, null);
	}

	/**
	 * Test the {@code login()} method
	 */
	@Test
	void testLogin() {
		fail("Not yet implemented");
	}

	/**
	 * Test the {@code signUp()} method
	 */
	@Test
	void testSignUp() {
		fail("Not yet implemented");
	}

	/**
	 * Test the {@code getUsername()} method
	 */
	@Test
	void testGetUsername() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the username is as assigned
		assertEquals("testUser", user.getUsername(), "Username not as assigned");
	}

	/**
	 * Test the {@code setUsername()} method
	 */
	@Test
	void testSetUsername() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the username
		user.setUsername("testUser2");
		
		// 3. Assert that the username is as assigned
		assertEquals("testUser2", user.getUsername(), "Username not as assigned");
	}

	/**
	 * Test the {@code getPassword()} method
	 */
	@Test
	void testGetPassword() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the password is as assigned
		assertEquals("testPass", user.getPassword(), "Password not as assigned");
	}

	/**
	 * Test the {@code setPassword()} method
	 */
	@Test
	void testSetPassword() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the password
		user.setPassword("testPass2");
		
		// 3. Assert that the password is as assigned
		assertEquals("testPass2", user.getPassword(), "Password not as assigned");
	}

	/**
	 * Test the {@code getDisplayName()} method
	 */
	@Test
	void testGetDisplayName() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the display name is as assigned
		assertEquals("Test User", user.getDisplayName(), "Display name not as assigned");
	}

	/**
	 * Test the {@code setDisplayName()} method
	 */
	@Test
	void testSetDisplayName() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the display name
		user.setDisplayName("Test User 2");
		
		// 3. Assert that the display name is as assigned
		assertEquals("Test User 2", user.getDisplayName(), "Display name not as assigned");
	}

	/**
	 * Test the {@code getProfilePic()} method
	 */
	@Test
	void testGetProfilePic() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the profile picture is null
		assertNull(user.getProfilePic(), "Profile picture not as assigned");
	}

	/**
	 * Test the {@code setProfilePic()} method
	 */
	@Test
	void testSetProfilePic() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the profile picture
		user.setProfilePic(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
		
		// 3. Assert that the profile picture is not null
		assertNotNull(user.getProfilePic(), "Profile picture not as assigned");
	}

	/**
	 * Test the {@code getPreferences()} method
	 */
	@Test
	void testGetPreferences() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the preferences are null
		assertNull(user.getPreferences(), "Preferences not as assigned");
	}

	/**
	 * Test the {@code setPreferences()} method
	 */
	@Test
	void testSetPreferences() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the preferences
		user.setPreferences("Test Preferences");
		
		// 3. Assert that the preferences are as assigned
		assertEquals("Test Preferences", user.getPreferences(), "Preferences not as assigned");
	}

	/**
	 * Test the {@code getToDoLists()} method
	 */
	@Test
	void testGetToDoLists() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Assert that the to-do lists are null
		assertNull(user.getToDoLists(), "To-do lists not as assigned");
	}

	/**
	 * Test the {@code setToDoLists()} method
	 */
	@Test
	void testSetToDoLists() {
		// 1. Instantiate user
		User user = new User("testUser", "testPass", "Test User", null, null, null);
		
		// 2. Set the to-do lists
		user.setToDoLists(new ArrayList<>());
		
		// 3. Assert that the to-do lists are as assigned
		assertNotNull(user.getToDoLists(), "To-do lists not as assigned");
	}

}
