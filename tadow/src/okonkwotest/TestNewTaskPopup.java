package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.Main;
import tadow.NewTaskPopup;

/**
 * A test class for {@code NewTaskPopup}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestNewTaskPopup {

	/**
	 * Test the constructor
	 */
	@Test
	void testNewTaskPopup() {
		// 1. Attempt to open up the popup
		NewTaskPopup popup = new NewTaskPopup((new Main()).getFrame(), null, null, null);
	}

}
