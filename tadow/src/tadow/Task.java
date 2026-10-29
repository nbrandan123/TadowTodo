package tadow;

import java.util.ArrayList;
import java.util.Date;

public class Task {

	// Data Methods
	private String tTitle; // Title of the task
	private String tDesc = ""; // Description of the task
	private Date tDate = new Date(); // Due date of the task
	private int tStat = 0; // 0 = Not Started, 1 = In Progress, 2 = Completed
	private boolean isRep = false; // Is the task a repeating task?
	private Date repInterval = new Date(0,0,0);
	private ArrayList<String> tComments = new ArrayList<>(); // Additional comments/notes for the task

	// Constructors
	public Task(String tTitle) {
		this.tTitle = tTitle;
	}

	// Getters and Setters
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

	public Date gettDate() {
		return tDate;
	}

	public void settDate(Date tDate) {
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
	
	public Date getRepInterval() {
		return repInterval;
	}

	public void setRepInterval(Date repInterval) {
		this.repInterval = repInterval;
	}

	public ArrayList<String> gettComments() {
		return tComments;
	}

	public void settComments(ArrayList<String> tComments) {
		this.tComments = tComments;
	}

}
