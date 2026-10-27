package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.Task;

class TestTask {

	/**
	 * Test the constructor
	 */
	@Test
	void testTask() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that the status is 0
		assertEquals(0, task.gettStat());
	}

	@Test
	void testGettTitle() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("Test Task", task.gettTitle());
	}

	@Test
	void testSettTitle() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new title
		task.settTitle("Test Task 2");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("Test Task 2", task.gettTitle());
	}

	@Test
	void testGettDesc() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new description
		task.settDesc("This is a test task.");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("This is a test task.", task.gettDesc());
	}

	@Test
	void testSettDesc() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new description
		task.settDesc("This is a test task.");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("This is a test task.", task.gettDesc());
	}

	@Test
	void testGettDate() {
		fail("Not yet implemented");
	}

	@Test
	void testSettDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGettStat() {
		fail("Not yet implemented");
	}

	@Test
	void testSettStat() {
		fail("Not yet implemented");
	}

	@Test
	void testIsRep() {
		fail("Not yet implemented");
	}

	@Test
	void testSetRep() {
		fail("Not yet implemented");
	}

	@Test
	void testGettComments() {
		fail("Not yet implemented");
	}

	@Test
	void testSettComments() {
		fail("Not yet implemented");
	}

}
