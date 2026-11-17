package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.Main;
import tadow.NewListPopup;

/**
 * A test class for {@code NewListPopup}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestNewListPopup {

	/**
	 * Test the constructor
	 */
	@Test
	void testNewListPopup() {
		// 1. Attempt to open up the popup
		NewListPopup popup = new NewListPopup((new Main()).getFrame(), null, null);
	}

}
