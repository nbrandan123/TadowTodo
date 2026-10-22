package okonkwo;

import java.util.ArrayList;

import tadow.Task;

public class TDList {
	
	// Data Members
	private ArrayList<Task> tdList;
	private String title;
	private String desc;
	
	// Constructors
	/**
	 * Explicit constructor | Create to-do list with title, description, and no set list of tasks
	 * @param title title
	 * @param desc description
	 */
	public TDList(String title, String desc) {
		super();
		this.tdList = new ArrayList<>();
		this.title = title;
		this.desc = desc;
	}
	
	/**
	 * Explicit constructor | Create to-do list with title, description, and a set list of tasks
	 * @param tdList task list
	 * @param title title
	 * @param desc description
	 */
	public TDList(ArrayList<Task> tdList, String title, String desc) {
		super();
		this.tdList = tdList;
		this.title = title;
		this.desc = desc;
	}
	
	// Methods
	public Task addTask(User user, String title, String desc) {
		return null;
	}
	
	public Task addTask(User user, Task task) {
		return task;
	}
	
	public Task delTask(User user, String title, String desc) {
		return null;
	}
	
	public Task delTask(User user, Task task) {
		return task;
	}
	
	public void editTask(User user, String title, String desc) {
		
	}
	
	public void editTask(User user, Task task) {
		
	}
	
	public void finTask(User user, String title, String desc) {
		
	}
	
	public void finTask(User user, Task task) {
		
	}
	
	// Getters and Setters
	/**
	 * Get the list of tasks
	 * @return task list
	 */
	public ArrayList<Task> getTDList() {
		return tdList;
	}

	/**
	 * Reset the list of tasks
	 * @param tdList new task list
	 */
	public void setTDList(ArrayList<Task> tdList) {
		this.tdList = tdList;
	}

	/**
	 * Get list title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set list title
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get list description
	 * @return description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Set list description
	 * @param desc description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
