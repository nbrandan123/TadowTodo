package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import tadow.TDList;
import tadow.Task;
import tadow.Team;

/**
 * A test class for {@code TDList}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestTDList {

	@Test
	void testTDList() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert that the to-do list is empty
		assertTrue(list.getTdList().isEmpty(), "To-do list not empty");
	}

	@Test
	void testGetTdList() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert that the to-do list is empty
		assertTrue(list.getTdList().isEmpty(), "To-do list not empty");
	}

	@Test
	void testSetTdList() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Add tasks
		ArrayList<Task> tasks = new ArrayList<>();
		tasks.add(new Task("Task 1"));
		tasks.add(new Task("Task 2"));
		tasks.add(new Task("Task 3"));
		list.setTdList(tasks);
		
		// 3. Assert that the to-do list is new
		assertEquals(tasks, list.getTdList(), "To-do list not as assigned");
	}

	@Test
	void testGetlTitle() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert that the title is set from the constructor
		assertEquals("Test List", list.getlTitle(), "Title not as assigned");
	}

	@Test
	void testSetlTitle() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Set title
		list.setlTitle("Test List 2");
		
		// 3. Assert that the title is set from the constructor
		assertEquals("Test List 2", list.getlTitle(), "Title not as assigned");
	}

	@Test
	void testGetlDesc() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert that the description is empty
		assertEquals("", list.getlDesc(), "Description not as assigned");
	}

	@Test
	void testSetlDesc() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Set new description
		list.setlDesc("Desc");
		
		// 2. Assert that the description is empty
		assertEquals("Desc", list.getlDesc(), "Description not as assigned");
	}

	@Test
	void testAddTask() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Add task
		Task task = new Task("Test Task");
		list.addTask(task);
		
		// 3. Assert that list contains task
		assertTrue(list.getTdList().contains(task), "Task not contained");
	}

	@Test
	void testDelTask() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Add task
		Task task = new Task("Test Task");
		list.addTask(task);
		
		// 3. Remove task
		list.delTask(task);
		
		// 4. Assert that list doesn't contain task
		assertFalse(list.getTdList().contains(task), "Task not deleted");
	}

	@Test
	void testEditTask() {
		fail("Not yet implemented");
	}

	@Test
	void testFinTask() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Add task
		Task task = new Task("Test Task");
		list.addTask(task);
		
		// 3. Complete task
		list.finTask(task);
		
		// 4. Assert that task is completed
		assertEquals(2, task.gettStat(), "Task not completed");
	}
	
	@Test
	void testIsShared() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert the list is not shared
		assertFalse(list.isShared(), "List shared");
	}
	
	@Test
	void testSetShared() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Set the share status
		list.setShared(true);
		
		// 3. Assert the list is shared
		assertTrue(list.isShared(), "List not shared");
	}
	
	@Test
	void testGetTeam() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Assert that no team is set
		assertNull(list.getTeam(), "List has a team");
	}
	
	@Test
	void testSetTeam() {
		// 1. Instantiate to-do list
		TDList list = new TDList("Test List");
		
		// 2. Set new team
		Team team = new Team("Test Team", new ArrayList<>(), new ArrayList<>());
		list.setTeam(team);
		
		// 3. Assert that team is set
		assertEquals(team, list.getTeam(), "List team not as assigned");
	}

}
