package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.SQLite;

/**
 * A test class for {@code SQLite}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestSQLite {

	/**
	 * Test the {@code connect()} method
	 */
	@Test
	void testConnect() {
		// 1. Attempt to connect
		SQLite.connect();
	}

	/**
	 * Test the {@code signup()} method
	 */
	@Test
	void testSignup() {
		// 1. Assert that a user that already exists cannot sign up
		assertFalse(SQLite.signup("tajokonkwo", "abc123"), "Pre-existing user allowed to sign up");
		
		// 2. Assert that a user that does not already exist can sign up
		fail("Not yet implemented: Assert that a user that does not already exist can sign up");
		//assertTrue(SQLite.signup("tajokonkwo2", "abc1234"), "Non-existent user not allowed to sign up");
		//SQLite.removeAccount("tajokonkwo2", "abc1234");
	}

	/**
	 * Test the {@code login()} method
	 */
	@Test
	void testLogin() {
		// 1. Assert that a false login results in failed connection
		assertFalse(SQLite.login("incor", "incor"), "False login registers as successful");
		
		// 2. Assert that a true login results in successful connection
		assertTrue(SQLite.login("tajokonkwo", "abc123"), "True login registers as failed");

		// 3. Assert case-sensitivity
		assertFalse(SQLite.login("tajOkonkwo", "abc123"), "Case-insensitive username and/or password");
	}
	
	/**
	 * Test the {@code removeAccount()} method
	 */
	@Test
	void testRemoveAccount() {
		fail("Not yet implemented");
	}

	@Test
	void testAddList() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetUserID() {
		// 1. Assert that an existing user has non-negative-one user id
		assertNotEquals(-1, SQLite.getUserID("tajokonkwo"), "Existing user has id -1");
		
		// 2. Assert that a non-existent user has -1 as user id
		assertEquals(-1, SQLite.getUserID("not a user"), "Non-existent user does not have id -1");
	}
	
	@Test
	void testGetListsForUser() {
		// 1. Assert that the test user "testNoLists" has no lists
		assertTrue(SQLite.login("testNoLists", ""), "User testNoLists unable to log in");
		assertTrue(SQLite.getListsForUser("testNoLists").isEmpty(), "User with no lists appears to have lists");
		
		// 2. Assert that the test user "testHasList" has lists
		assertTrue(SQLite.login("testHasList", ""), "User testHasList unable to log in");
		assertFalse(SQLite.getListsForUser("testHasList").isEmpty(), "User with lists appears to have no lists");
	}

}
