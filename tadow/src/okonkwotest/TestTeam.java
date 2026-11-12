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

	/**
	 * Test single-user constructor
	 */
	@Test
	void testTeamStringUser() {
		// 1. Instantiate team
		User user = new User("Test User", null, null, null, null, null);
		Team team = new Team("My Team", user);
		
		// 2. Assert that the creator is an admin and a user
		assertEquals(user, team.getTeamAdmins().get(0), "User not an admin");
		assertEquals(user, team.getTeamUsers().get(0), "Users not as declared");
	}

	/**
	 * Test multiple-user constructor
	 */
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

	/**
	 * Test {@code getTeamUsers()} method
	 */
	@Test
	void testGetTeamUsers() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the users are empty
		assertTrue(team.getTeamUsers().isEmpty(), "Users not empty");
		
		// 3. Add user
		team.joinTeam(new User(null, null, null, null, null, null), false);
		
		// 4. Assert that the users are not empty
		assertFalse(team.getTeamUsers().isEmpty(), "Users are empty");
	}

	/**
	 * Test {@code setTeamUsers()} method
	 */
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

	/**
	 * Test {@code getTeamAdmins()} method
	 */
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

	/**
	 * Test {@code setTeamAdmins()} method
	 */
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
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that team name is as assigned
		assertEquals("My Team", team.getTeamName(), "Team name not as assigned");
	}

	@Test
	void testSetTeamName() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Reassign team name
		team.setTeamName("My Team 2");
		
		// 3. Assert that team name is as assigned
		assertEquals("My Team 2", team.getTeamName(), "Team name not as assigned");
	}

	@Test
	void testJoinTeam() {
		// 1. Instantiate team
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		Team team = new Team("My Team", users, admins);
		
		// 2. Add new member
		User newUser = new User("New User", null, null, null, null, null);
		team.joinTeam(newUser, false);
		
		// 3. Assert that new member has been added but not as admin
		assertTrue(team.getTeamUsers().contains(newUser), "New user not contained");
		assertFalse(team.isAdmin(newUser), "New user set as admin");
	
		// 4. Add new admin
		User newAdmin = new User("New Admin", null, null, null, null, null);
		team.joinTeam(newAdmin, true);
		
		// 5. Assert that new member has been added
		assertTrue(team.isAdmin(newAdmin), "New admin not contained");
		assertTrue(team.getTeamUsers().contains(newAdmin), "New admin not user");
	}

	@Test
	void testIsAdmin() {
		// 1. Instantiate team with admin
		ArrayList<User> users = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();
		User myAdmin = new User("New Admin", null, null, null, null, null);
		admins.add(myAdmin);
		
		Team team = new Team("My Team", users, admins);
		
		// 2. Assert that the user is both an admin and a user
		assertTrue(team.isAdmin(myAdmin), "New admin not contained");
		assertTrue(team.getTeamUsers().contains(myAdmin), "New admin not user");
	}

}
