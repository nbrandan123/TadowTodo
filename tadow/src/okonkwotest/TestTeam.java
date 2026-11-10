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
		assertEquals(user, team.getTeamAdmins().get(0), "User not an admin");
		assertEquals(user, team.getTeamUsers().get(0), "Users not as declared");
	}

	@Test
	void testTeamStringArrayListOfUserArrayListOfUser() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the users and admins are set
		assertEquals(admins, team.getTeamAdmins(), "Admins not as declared");
		assertEquals(users, team.getTeamUsers(), "Users not as declared");
		
		// 3. Assert that the admins are users
		assertTrue(team.getTeamUsers().containsAll(admins));
	}

	@Test
	void testGetTeamUsers() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the users are empty
		assertTrue(team.getTeamUsers().isEmpty(), "Users not empty");
		
		// 3. Add user
		team.joinTeam(new User(null, null, null, null, null, null));
		
		// 4. Assert that the users are not empty
		assertFalse(team.getTeamUsers().isEmpty(), "Users are empty");
	}

	@Test
	void testSetTeamUsers() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);

		// 2. Assert that the users are empty
		assertTrue(team.getTeamUsers().isEmpty(), "Users not empty");
		
		// 3. Create new list of users and reassign
		ArrayList<User> newUsers = new ArrayList<>();
		newUsers.add(new User(null, null, null, null, null, null));
		newUsers.add(new User(null, null, null, null, null, null));
		newUsers.add(new User(null, null, null, null, null, null));
		team.setTeamUsers(newUsers);
		
		// 4. Assert that the users are not empty
		assertFalse(team.getTeamUsers().isEmpty(), "Users are empty");
	}

	@Test
	void testGetTeamAdmins() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		admins.add(new User(null, null, null, null, null, null));
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that there is only one admin
		assertEquals(1, team.getTeamAdmins().size(), "Expected 1 admin. Has " + team.getTeamAdmins().size());
		
		// 3. Instantiate new team
		ArrayList<User> users2 = new ArrayList<>();
		ArrayList<User> admins2 = new ArrayList<>();
		admins2.add(new User(null, null, null, null, null, null));
		admins2.add(new User(null, null, null, null, null, null));
		admins2.add(new User(null, null, null, null, null, null));
		Team team2 = new Team("My Team", users2, admins2);
		
		// 4. Assert that there are 3 admins
		assertEquals(3, team2.getTeamAdmins().size(), "Expected 3 admins. Has " + team2.getTeamAdmins().size());
		
		// 5. Assert that the old admins are no longer contained
		assertFalse(team2.getTeamAdmins().containsAll(admins), "Old admins contained");
	}

	@Test
	void testSetTeamAdmins() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);

		// 2. Assert that the admins are empty
		assertTrue(team.getTeamAdmins().isEmpty());
		
		// 3. Create new list of users and reassign
		ArrayList<User> newAdmins = new ArrayList<>();
		newAdmins.add(new User(null, null, null, null, null, null));
		newAdmins.add(new User(null, null, null, null, null, null));
		newAdmins.add(new User(null, null, null, null, null, null));
		team.setTeamAdmins(newAdmins);
		
		// 4. Assert that the admins are not empty
		assertFalse(team.getTeamAdmins().isEmpty(), "Admins are empty");
		
		// 5. Assert that the admins are users
		assertTrue(team.getTeamUsers().containsAll(admins), "Admins are not users");
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
