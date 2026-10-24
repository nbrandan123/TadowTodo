package tadow;

import java.util.ArrayList;

public class Task {
	private String tTitle;					// Title of the task
	private String tDesc;					// Description of the task
	private String tDate;					// Due date of the task
	private int tStat;						// 0 = Not Started, 1 = In Progress, 2 = Completed
	private boolean isRep = false;			// Is the task a repeating task?
	private String repInterval;
	private ArrayList<String> tComments;	// Additional comments/notes for the task
	
	public Task(String tTitle) {
		this.tTitle = tTitle;
	}

	public String gettTitle() {
		return tTitle;
	}
	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}
	public String gettDesc() {
		return tDesc;
	}
	public void settDesc(String tDesc) {
		this.tDesc = tDesc;
	}
	public String gettDate() {
		return tDate;
	}
	public void settDate(String tDate) {
		this.tDate = tDate;
	}
	public int gettStat() {
		return tStat;
	}
	public void settStat(int tStat) {
		this.tStat = tStat;
	}
	public boolean isRep() {
		return isRep;
	}
	public void setRep(boolean isRep) {
		this.isRep = isRep;
	}
	public ArrayList<String> gettComments() {
		return tComments;
	}
	public void settComments(ArrayList<String> tComments) {
		this.tComments = tComments;
	}
	
	
}
