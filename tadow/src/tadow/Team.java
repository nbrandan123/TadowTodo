package tadow;

import tadow.User;
import java.util.ArrayList;

public class Team {
	
	private String teamName;
	private ArrayList<User> teamUsers;
	private ArrayList<User> teamAdmins;
	
	
	/* Scenario A: creating a new team with no other members
	 * Important note: a team can't get created without members in this model. When a user creates a team they get
	 * added to the team as its first member and admin.
	 * */
	public Team(String teamName, User creator) {
	    this.teamName = teamName;
	    this.teamUsers = new ArrayList<>();
	    this.teamAdmins = new ArrayList<>();
	    teamUsers.add(creator);
	    teamAdmins.add(creator);
	}
	
	//Scenario B: creating a team with existing values from the database
	public Team(String teamName, ArrayList<User> users, ArrayList<User> admins) {
	    this.teamName = teamName;
	    this.teamUsers = users;
	    this.teamAdmins = admins;
	}

	public ArrayList<User> getTeamUsers() {
		return teamUsers;
	}

	public void setTeamUsers(ArrayList<User> teamUsers) {
		this.teamUsers = teamUsers;
	}

	public ArrayList<User> getTeamAdmins() {
		return teamAdmins;
	}

	public void setTeamAdmins(ArrayList<User> teamAdmins) {
		this.teamAdmins = teamAdmins;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void joinTeam(User e) {
		
		teamUsers.add(e);
		
		/* This rest of this part will need to be programmed once SQLite database is set up
		 */
	}
	public boolean isAdmin(User u) {
		return teamAdmins.contains(u);
	}

	

}
