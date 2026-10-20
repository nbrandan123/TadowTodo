package tadow;

import java.util.ArrayList;

public class Task {
	String tTitle;					// Title of the task
	String tDesc;					// Description of the task
	String tDate;					// Due date of the task
	int tStat;						// 0 = Not Started, 1 = In Progress, 2 = Completed
	boolean isRep;					// Is the task a repeating task?
	ArrayList<String> tComments;	// Additional comments/notes for the task
}
