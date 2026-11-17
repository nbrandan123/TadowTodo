package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import tadow.SQLite;
import tadow.TDList;
import tadow.Task;

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

	/**
	 * Test the {@code getUserID()} method
	 */
	@Test
	void testGetUserID() {
		// 1. Assert that an existing user has non-negative-one user id
		assertNotEquals(-1, SQLite.getUserID("tajokonkwo"), "Existing user has id -1");
		
		// 2. Assert that a non-existent user has -1 as user id
		assertEquals(-1, SQLite.getUserID("not a user"), "Non-existent user does not have id -1");
	}

	/**
	 * Test the {@code addList()} method
	 */
	@Test
	void testAddList() {
		// 1. Login as a user to add a list for
		SQLite.login("tajokonkwo", "abc123");
		
		// 2. Assert that a list can be added
		assertTrue(SQLite.addList(new TDList("Test List")), "Failed to add list for user");
	}
	
	/**
	 * Test the {@code getListsForUser()} method
	 */
	@Test
	void testGetListsForUser() {
		// 1. Assert that the test user "testNoLists" has no lists
		assertTrue(SQLite.getListsForUser("testNoLists").isEmpty(), "User with no lists appears to have lists");
		
		// 2. Assert that the test user "testHasList" has lists
		assertFalse(SQLite.getListsForUser("testHasList").isEmpty(), "User with lists appears to have no lists");
	}

	/**
	 * Test the {@code getListID()} method
	 */
	@Test
	void testGetListID() {
		// 1. Assert that an existing list has non-negative-one list id
		assertNotEquals(-1, SQLite.getListID(SQLite.getListsForUser("testHasList").get(0)), "Existing list has id -1");
		
		// 2. Assert that a non-existent list has -1 as list id
		assertEquals(-1, SQLite.getListID(new TDList("Not a list")), "Non-existent list does not have id -1");
	}
	
	/**
	 * Test the {@code updateList()} method
	 */
	@Test
	void testUpdateList() {
		// 1. Add a list to update
		SQLite.login("tajokonkwo", "abc123");
		SQLite.addList(new TDList("Test List"));
		
		// 2. Update the list's title and description
		ArrayList<TDList> lists = SQLite.getListsForUser("tajokonkwo");
		TDList listToUpdate = lists.get(lists.size() - 1);
		listToUpdate.setlTitle("Updated Test List");
		listToUpdate.setlDesc("Updated description");
		
		// 3. Assert that the list's title and description were updated
		SQLite.updateList(listToUpdate);
		assertEquals("Updated Test List", SQLite.getListsForUser("tajokonkwo").get(lists.size() - 1).getlTitle(), "List title was not updated");
		assertEquals("Updated description", SQLite.getListsForUser("tajokonkwo").get(lists.size() - 1).getlDesc(), "List description was not updated");
	}
	
	/**
	 * Test the {@code deleteList()} method
	 */
	@Test
	void testDeleteList() {
		// 1. Add a list to delete
		SQLite.login("tajokonkwo", "abc123");
		SQLite.addList(new TDList("Test List"));
		
		// 2. Delete the list
		ArrayList<TDList> lists = SQLite.getListsForUser("tajokonkwo");
		TDList listToDelete = lists.get(lists.size() - 1);
		SQLite.deleteList(listToDelete);
		
		// 3. Assert that the list was deleted
		assertFalse(SQLite.getListsForUser("tajokonkwo").contains(listToDelete), "List was not deleted");
	}

	/**
	 * Test the {@code addTask()} method
	 */
	@Test
	void testAddTask() {
		// 1. Get a list from a user
		SQLite.login("tajokonkwo", "abc123");
		TDList list = SQLite.getListsForUser("tajokonkwo").get(0);
		
		// 2. Assert that a task can be added
		assertTrue(SQLite.addTask(new Task("New Task"), list), "Unable to add task");
	}

	/**
	 * Test the {@code getTasksForList()} method
	 */
	@Test
	void testGetTasksForList() {
		// 1. Get a list from a user
		SQLite.login("tajokonkwo", "abc123");
		TDList list = SQLite.getListsForUser("tajokonkwo").get(0);
		
		// 2. Get the tasks of the list
		ArrayList<Task> tasks = SQLite.getTasksForList(list);
		
		// 3. Assert that the list of tasks is not empty
		assertFalse(tasks.isEmpty(), "Known list of tasks is empty");
	}

	/**
	 * Test the {@code getTaskID()} method
	 */
	@Test
	void testUpdateTask() {
		// 1. Get a list from a user
		SQLite.login("tajokonkwo", "abc123");
		TDList list = SQLite.getListsForUser("tajokonkwo").get(0);
		
		// 2. Update a task's description
		Task task = SQLite.getTasksForList(list).get(0);
		task.settDesc("New description!");
		
		// 3. Assert that the task has been updated
		assertTrue(SQLite.updateTask(task), "Task could not be updated");
	}
	
	/**
	 * Test the {@code deleteTask()} method
	 */
	@Test
	void testDeleteTask() {
		// 1. Get a list from a user
		SQLite.login("tajokonkwo", "abc123");
		TDList list = SQLite.getListsForUser("tajokonkwo").get(0);
		
		// 2. Assert that a task can be removed
		Task taskToDelete = SQLite.getTasksForList(list).get(0);
		assertTrue(SQLite.deleteTask(taskToDelete), "Cannot remove task");
	}
}
