package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.Main;

/**
 * A test class for {@code Main}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestMain {

	@Test
	void testMain() {
		// 1. Instantiate program
		Main main = new Main();
		
		// 2. Assert that the application opens with login and not main
		assertTrue(main.getCardLogin().isVisible());
		assertFalse(main.getCardMain().isVisible());
	}

}
