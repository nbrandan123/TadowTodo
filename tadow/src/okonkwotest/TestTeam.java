package okonkwotest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tadow.Team;
import tadow.User;

/**
 * A test class for {@code Team}
 * <p>Comments, documentation, and code generated in part by GitHub Copilot</p>
 * @author Taj Okonkwo
 * @version 0.1
 */
class TestTeam {

	@Test
	void testTeamStringUser() {
		// 1. Instantiate team
		User user = new User("Test User", null, null, null, null, null);
		Team team = new Team("My Team", user);
		
		// 2. Assert that the creator is an admin and a user
		assertEquals(user, team.getTeamAdmins().get(0));
		assertEquals(user, team.getTeamUsers().get(0));
	}

	@Test
	void testTeamStringArrayListOfUserArrayListOfUser() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the users and admins are set
		assertEquals(users, team.getTeamAdmins());
		assertEquals(users, team.getTeamUsers());
	}

	@Test
	void testGetTeamUsers() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the users and admins are set
		assertEquals(users, team.getTeamAdmins());
		assertEquals(users, team.getTeamUsers());
	}

	@Test
	void testSetTeamUsers() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTeamAdmins() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTeamAdmins() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTeamName() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTeamName() {
		fail("Not yet implemented");
	}

	@Test
	void testJoinTeam() {
		fail("Not yet implemented");
	}

	@Test
	void testIsAdmin() {
		fail("Not yet implemented");
	}

}
