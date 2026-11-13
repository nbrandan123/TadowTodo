package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import tadow.Task;

/**
 * A test class for {@code Task}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestTask {

	/**
	 * Test the constructor
	 */
	@Test
	void testTask() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that the status is 0
		assertEquals(0, task.gettStat(), "Status not zero");
	}

	@Test
	void testGettTitle() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("Test Task", task.gettTitle(), "Title not as assigned");
	}

	@Test
	void testSettTitle() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new title
		task.settTitle("Test Task 2");
		
		// 2. Assert that the title is correct from the constructor
		assertEquals("Test Task 2", task.gettTitle(), "Title not as assigned");
	}

	@Test
	void testGettDesc() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new description
		task.settDesc("This is a test task.");
		
		// 2. Assert that the description is correct from the constructor
		assertEquals("This is a test task.", task.gettDesc(), "Description not as assigned");
	}

	@Test
	void testSettDesc() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new description
		task.settDesc("This is a test task.");
		
		// 2. Assert that the description is correct from the constructor
		assertEquals("This is a test task.", task.gettDesc(), "Description not as assigned");
	}

	@Test
	void testGettDate() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that the date of the task is set
		assertNotNull(task.gettDate(), "Date unassigned");
	}

	@Test
	void testSettDate() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set to new date
		Date myDate = new Date(1, 2, 3);
		task.settDate(myDate);
		
		// 2. Assert that the date is set
		assertEquals(myDate, task.gettDate(), "Date not as assigned");
	}

	@Test
	void testGettStat() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that status is default
		assertEquals(0, task.gettStat(), "Status not 0");
	}

	@Test
	void testSettStat() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new status
		task.settStat(1);
		
		// 3. Assert that status is the new status
		assertEquals(1, task.gettStat(), "Status not as assigned");
	}

	@Test
	void testIsRep() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that repetition status is default
		assertEquals(false, task.isRep(), "Repetition not default");
	}

	@Test
	void testSetRep() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Invert repetition status
		task.setRep(true);
		
		// 2. Assert that repetition status is new
		assertEquals(true, task.isRep(), "Repetition not as assigned");
	}
	
	@Test
	void testGetRepInterval() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that repetition interval is default
		assertEquals(new Date(0,0,0), task.getRepInterval(), "Repetition interval not default");
	}
	
	@Test
	void testSetRepInterval() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new repetition interval
		Date myDate = new Date(1, 2, 3);
		task.setRepInterval(myDate);
		
		// 3. Assert that repetition interval is new
		assertEquals(myDate, task.getRepInterval(), "Repetition interval not as assigned");
	}

	@Test
	void testGettComments() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Assert that comments are default
		assertNotNull(task.gettComments(), "Comments not default");
	}

	@Test
	void testSettComments() {
		// 1. Instantiate task
		Task task = new Task("Test Task");
		
		// 2. Set new comments
		ArrayList<String> myComments = new ArrayList<String>();
		myComments.add("This is a test comment.");
		task.settComments(myComments);
		
		// 3. Assert that comments are new
		assertEquals(myComments, task.gettComments(), "Comments not as assigned");
	}

}
