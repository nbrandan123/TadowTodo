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

	@Test
	void testConnect() {
		fail("Not yet implemented");
	}

	@Test
	void testSignup() {
		fail("Not yet implemented");
		// 1. Assert that a user that already exists cannot sign up
		
		// 2. Assert that a user that does not already exist can sign up
	}

	@Test
	void testLogin() {
		// 1. Assert that a false login results in failed connection
		assertFalse(SQLite.login("incor", "incor"));
		
		// 2. Assert that a true login results in successful connection
		assertTrue(SQLite.login("tajokonkwo", "abc123"));

		// 3. Assert case-sensitivity
		assertFalse(SQLite.login("tajOkonkwo", "abc123"));
	}

}
