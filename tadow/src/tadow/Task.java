package tadow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Task {

	// Stored status values. "Overdue" is NOT stored: it is worked out from the due date.
	public static final int NOT_STARTED = 0;
	public static final int COMPLETED = 2;

	// Accepts 3/7/2026 or 03/07/2026; rejects impossible dates such as 02/30/2026
	private static final DateTimeFormatter PARSE_FMT = DateTimeFormatter.ofPattern("M/d/uuuu")
			.withResolverStyle(ResolverStyle.STRICT);
	private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	// Data Members
	private int id = -1; // Database id (-1 until saved / loaded)
	private String tTitle; // Title of the task
	private String tDesc = ""; // Description of the task
	private String tDate = ""; // Due date of the task (MM/DD/YYYY text, "" = none)
	private int tStat = NOT_STARTED; // 0 = Not Started, 2 = Completed
	private boolean isRep = false; // Is the task a repeating task?
	private String repInterval = ""; // How often the task repeats (free text for now)
	private ArrayList<String> tComments = new ArrayList<>(); // Additional comments/notes for the task

	// Constructors
	public Task(String tTitle) {
		this.tTitle = tTitle;
	}

	// Date helpers
	/** @return the parsed date, or null if the text is blank or not a valid date */
	public static LocalDate parseDate(String text) {
		if (text == null || text.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(text.trim(), PARSE_FMT);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/** A blank date is allowed (no due date). */
	public static boolean isValidDate(String text) {
		return text == null || text.isBlank() || parseDate(text) != null;
	}

	/** Turns 3/7/2026 into 03/07/2026 so stored dates are consistent. Blank stays blank. */
	public static String normalizeDate(String text) {
		LocalDate d = parseDate(text);
		return (d == null) ? "" : d.format(OUT_FMT);
	}

	public boolean isOverdue() {
		if (tStat == COMPLETED) {
			return false;
		}
		LocalDate due = parseDate(tDate);
		return due != null && due.isBefore(LocalDate.now());
	}

	/** Text for the Status column / details view. */
	public String getStatusLabel() {
		if (tStat == COMPLETED) {
			return "Completed";
		}
		return isOverdue() ? "Overdue" : "Not Started";
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		this.tDesc = (tDesc == null) ? "" : tDesc;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = (tDate == null) ? "" : tDate;
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

	public String getRepInterval() {
		return repInterval;
	}

	public void setRepInterval(String repInterval) {
		this.repInterval = (repInterval == null) ? "" : repInterval;
	}

	public ArrayList<String> gettComments() {
		return tComments;
	}

	public void settComments(ArrayList<String> tComments) {
		this.tComments = tComments;
	}

}