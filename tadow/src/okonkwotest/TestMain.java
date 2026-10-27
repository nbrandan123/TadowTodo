package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tadow.Main;

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
