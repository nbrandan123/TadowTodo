package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.NewListPopup;

/**
 * A test class for {@code NewListPopup}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestNewListPopup {

	//@Test
	/*void testMain() {
		fail("Not yet implemented");
	}*/

	/**
	 * Test the constructor
	 */
	@Test
	void testNewListPopup() {
		// 1. Open new popup and assert that no errors occur
		assertDoesNotThrow(() -> {
			NewListPopup popup = new NewListPopup();
		}, "Error in NewListPopup");
	}

}
