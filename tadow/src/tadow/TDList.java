package tadow;

import java.util.ArrayList;

public class TDList {

	// Data Members
	private int id = -1; // Database id (-1 until loaded from / saved to the database)
	private ArrayList<Task> tdList;
	private String lTitle;
	private String lDesc = "";
	private boolean shared;
	private String teamName = ""; // Name of the team this list is shared with (saved in the lists table).
	// Team.java exists but is not wired to the database yet; swap this for a Team later.

	// Constructors
	public TDList(String title) {
		this.tdList = new ArrayList<Task>();
		this.lTitle = title;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Task> getTdList() {
		return tdList;
	}

	public void setTdList(ArrayList<Task> tdList) {
		this.tdList = tdList;
	}

	public String getlTitle() {
		return lTitle;
	}

	public void setlTitle(String lTitle) {
		this.lTitle = lTitle;
	}

	public String getlDesc() {
		return lDesc;
	}

	public void setlDesc(String lDesc) {
		this.lDesc = (lDesc == null) ? "" : lDesc;
	}

	public void addTask(Task t) {
		tdList.add(t);
	}

	public void delTask(Task t) {
		tdList.remove(t);
	}

	public void editTask(Task t) {
		// TODO open edit task dialog
	}

	public void finTask(Task t) {
		t.settStat(2);
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = (teamName == null) ? "" : teamName;
	}

	@Override
	public String toString() {
		return lTitle; // This will display the title of the list in the JList
	}

}