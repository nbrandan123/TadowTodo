package tadow;

import java.util.ArrayList;

public class TDList {
	
	// Data Members
	private ArrayList<Task> tdList;
	private String lTitle;
	private String lDesc = "";
	private boolean shared;
	private Team team;
	
	// Constructors
	public TDList(String title) {
		this.tdList = new ArrayList<Task>();
		this.lTitle = title;
	}

	// Getters and Setters
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
		this.lDesc = lDesc;
	}
	
	public void addTask(Task t) {
		tdList.add(t);
	}
	public void delTask(Task t) {
		tdList.remove(t);
	}
	public void editTask(Task t) {
		//open editTask card ?
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
    @Override
    public String toString() {
        return lTitle; // This will display the title of the list in the JList
    }

}
