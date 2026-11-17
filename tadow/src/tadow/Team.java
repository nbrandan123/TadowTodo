package tadow;

import java.util.ArrayList;

public class Team {
	
	// Data Members
	private String teamName;
	private ArrayList<User> teamUsers;
	private ArrayList<User> teamAdmins;
	
	
	// Constructors
	/** Scenario A: creating a new team with no other members
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
	
	/** Scenario B: creating a team with existing values 
	 * from the database. Copies the lists so the caller's lists are not changed,
	 * and makes sure every admin is also counted as a member (without duplicates).
	 * */
	public Team(String teamName, ArrayList<User> users, ArrayList<User> admins) {
	    this.teamName = teamName;
	    this.teamUsers = new ArrayList<>(users);
	    this.teamAdmins = new ArrayList<>(admins);
	    
	    for (User a : admins) {
	        if (!teamUsers.contains(a)) {
	            teamUsers.add(a);
	        }
	    }
	}

	// Getters and Setters
	public ArrayList<User> getTeamUsers() {
		return teamUsers;
	}

	public void setTeamUsers(ArrayList<User> teamUsers) {
		this.teamUsers = teamUsers;
	}

	public ArrayList<User> getTeamAdmins() {
		return teamAdmins;
	}

	/** Replaces the admin list. Every admin is also a member; someone who is no longer
	 * an admin stays on the team as a regular member (they are not removed). */
	public void setTeamAdmins(ArrayList<User> teamAdmins) {
		this.teamAdmins = teamAdmins;
		for (User a : teamAdmins) {
			if (!teamUsers.contains(a)) {
				teamUsers.add(a);
			}
		}
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void joinTeam(User e, boolean asAdmin) {
		
		if (!teamUsers.contains(e)) {
			teamUsers.add(e);
		}
		if (asAdmin && !teamAdmins.contains(e)) {
			teamAdmins.add(e);
		}
		
		/* This rest of this part will need to be programmed once SQLite database is set up
		 */
	}
	public boolean isAdmin(User u) {
		return teamAdmins.contains(u);
	}

	

}