package tadow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Main {

	private static final String CARD_LOGIN = "login";
	private static final String CARD_MAIN = "main";

	private JFrame frame;
	private JTextField txtLoginUN;
	private JPasswordField txtLoginPW;
	private JLabel lblLoginStatus;
	private JLabel lblUser;
	private JPanel cardLogin;
	private JPanel cardMain;
	private final CardLayout cards = new CardLayout();

	// Model for the lists JList
	private final DefaultListModel<TDList> listModel = new DefaultListModel<>();

	// Model for the tasks JTable (read-only cells)
	private final DefaultTableModel taskModel = new DefaultTableModel(new String[] { "Task", "Due Date", "Status" }, 0) {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// The Task objects behind the table rows (same order as the rows)
	private final List<Task> currentTasks = new ArrayList<>();

	private JList<TDList> toDoListsView;
	private JTable table;
	private JTextArea detailsArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SQLite.connect();
		EventQueue.invokeLater(() -> {
			try {
				Main window = new Main();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Tadow");
		frame.setBounds(100, 100, 813, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(cards);

		// Both cards are built once; the main card is just reloaded with data after each login
		initLoginCard();
		initMainCard();
		frame.getContentPane().add(cardLogin, CARD_LOGIN);
		frame.getContentPane().add(cardMain, CARD_MAIN);
		cards.show(frame.getContentPane(), CARD_LOGIN);
	}

	// =====================================================================
	// Login card
	// =====================================================================

	/**
	 * Build the login card
	 */
	private void initLoginCard() {
		cardLogin = new JPanel();
		cardLogin.setLayout(null);

		JLabel lblLoginTitle = new JLabel("Tadow");
		lblLoginTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setBounds(323, 37, 115, 35);
		cardLogin.add(lblLoginTitle);

		JLabel lblLoginUN = new JLabel("Username:");
		lblLoginUN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginUN.setBounds(220, 118, 94, 35);
		cardLogin.add(lblLoginUN);

		JLabel lblLoginPW = new JLabel("Password:");
		lblLoginPW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginPW.setBounds(418, 118, 94, 35);
		cardLogin.add(lblLoginPW);

		lblLoginStatus = new JLabel("");
		lblLoginStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginStatus.setBounds(228, 289, 310, 14);
		cardLogin.add(lblLoginStatus);

		txtLoginUN = new JTextField();
		txtLoginUN.setBounds(220, 151, 126, 27);
		cardLogin.add(txtLoginUN);
		txtLoginUN.setColumns(10);

		txtLoginPW = new JPasswordField();
		txtLoginPW.setColumns(10);
		txtLoginPW.setBounds(418, 151, 126, 27);
		cardLogin.add(txtLoginPW);

		// Pressing Enter in either box logs in
		txtLoginUN.addActionListener(e -> doLogin());
		txtLoginPW.addActionListener(e -> doLogin());

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(e -> doLogin());
		btnLogin.setBounds(328, 218, 110, 27);
		cardLogin.add(btnLogin);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(e -> doSignUp());
		btnSignUp.setBounds(328, 251, 110, 27);
		cardLogin.add(btnSignUp);
	}

	private void doLogin() {
		String un = txtLoginUN.getText().trim();
		String pw = new String(txtLoginPW.getPassword());

		if (SQLite.login(un, pw)) {
			lblLoginStatus.setText("");
			txtLoginPW.setText("");
			showMainCard();
		} else {
			lblLoginStatus.setText("Login failed! Check your username and password.");
		}
	}

	private void doSignUp() {
		String un = txtLoginUN.getText().trim();
		String pw = new String(txtLoginPW.getPassword());

		if (SQLite.signup(un, pw)) {
			lblLoginStatus.setText("Sign up successful! You can now log in.");
		} else {
			lblLoginStatus.setText("Sign up failed! Name taken or fields empty.");
		}
	}

	private void doLogout() {
		SQLite.UserSession.clear();
		listModel.clear();
		taskModel.setRowCount(0);
		currentTasks.clear();
		showDetails(null);
		txtLoginUN.setText("");
		txtLoginPW.setText("");
		lblLoginStatus.setText("");
		cards.show(frame.getContentPane(), CARD_LOGIN);
	}

	/**
	 * Load the user's data into the main card and switch to it.
	 */
	private void showMainCard() {
		lblUser.setText("  Signed in as " + SQLite.UserSession.getUserName());
		refreshLists(-1);
		cards.show(frame.getContentPane(), CARD_MAIN);
	}

	// =====================================================================
	// Data loading
	// =====================================================================

	private TDList getSelectedList() {
		return toDoListsView.getSelectedValue();
	}

	private Task getSelectedTask() {
		int row = table.getSelectedRow();
		return (row >= 0 && row < currentTasks.size()) ? currentTasks.get(row) : null;
	}

	/**
	 * Reload the lists for the logged-in user into the shared list model, then
	 * select the list with the given id (if there is one).
	 */
	private void refreshLists(int reselectId) {
		listModel.clear();
		int select = -1;
		int i = 0;
		for (TDList list : SQLite.getListsForUser(SQLite.UserSession.getUserName())) {
			listModel.addElement(list);
			if (list.getId() == reselectId) {
				select = i;
			}
			i++;
		}
		if (select >= 0) {
			toDoListsView.setSelectedIndex(select);
		} else {
			loadTasks(-1);
		}
	}

	/**
	 * Reload the tasks of the selected list into the table model, then select the
	 * task with the given id (if there is one).
	 */
	private void loadTasks(int reselectId) {
		taskModel.setRowCount(0);
		currentTasks.clear();

		TDList list = getSelectedList();
		if (list != null) {
			for (Task t : SQLite.getTasksForList(list)) {
				currentTasks.add(t);
				taskModel.addRow(new Object[] { t.gettTitle(), t.gettDate(), t.getStatusLabel() });
			}
		}

		int row = -1;
		for (int i = 0; i < currentTasks.size(); i++) {
			if (currentTasks.get(i).getId() == reselectId) {
				row = i;
			}
		}
		if (row >= 0) {
			table.setRowSelectionInterval(row, row);
		} else {
			showDetails(null);
		}
	}

	/**
	 * Fill the Details View with the selected task (or clear it).
	 */
	private void showDetails(Task t) {
		if (t == null) {
			detailsArea.setText("");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(t.gettTitle()).append("\n\n");
		sb.append("Due: ").append(t.gettDate().isEmpty() ? "none" : t.gettDate()).append('\n');
		sb.append("Status: ").append(t.getStatusLabel()).append('\n');
		if (t.isRep()) {
			sb.append("Repeats: ").append(t.getRepInterval().isEmpty() ? "yes" : t.getRepInterval()).append('\n');
		}
		if (!t.gettDesc().isBlank()) {
			sb.append('\n').append(t.gettDesc());
		}
		detailsArea.setText(sb.toString());
		detailsArea.setCaretPosition(0);
	}

	// =====================================================================
	// Main card
	// =====================================================================

	/**
	 * A header row: a title on the left and small buttons on the right.
	 * Lives outside the scroll pane, because a JTable puts its own column
	 * header into its scroll pane and would replace this one.
	 */
	private JPanel buildHeader(String title, JButton... buttons) {
		JPanel head = new JPanel(new BorderLayout(6, 0));
		head.setBorder(new EmptyBorder(2, 6, 2, 4));
		head.add(new JLabel(title), BorderLayout.WEST);

		JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 0));
		for (JButton b : buttons) {
			b.setMargin(new Insets(2, 6, 2, 6));
			b.setFocusable(false);
			btns.add(b);
		}
		head.add(btns, BorderLayout.EAST);
		return head;
	}

	/**
	 * Build the main card
	 */
	private void initMainCard() {
		cardMain = new JPanel();
		cardMain.setLayout(new BorderLayout(0, 0));

		JPanel gridpanel = new JPanel();
		cardMain.add(gridpanel, BorderLayout.CENTER);
		gridpanel.setLayout(new GridLayout(1, 3, 0, 0));

		// ---------- Left: lists ----------
		toDoListsView = new JList<>(listModel);
		toDoListsView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		toDoListsView.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				loadTasks(-1);
			}
		});

		JButton btnNewList = new JButton("+");
		btnNewList.setToolTipText("New list");
		btnNewList.addActionListener(e -> {
			NewListPopup nlp = new NewListPopup(frame, null, saved -> refreshLists(saved.getId()));
			nlp.setVisible(true);
		});

		JButton btnEditList = new JButton("Edit");
		btnEditList.addActionListener(e -> {
			TDList list = getSelectedList();
			if (list == null) {
				JOptionPane.showMessageDialog(frame, "Select a list first.");
				return;
			}
			NewListPopup nlp = new NewListPopup(frame, list, saved -> refreshLists(saved.getId()));
			nlp.setVisible(true);
		});

		JButton btnDelList = new JButton("Delete");
		btnDelList.addActionListener(e -> {
			TDList list = getSelectedList();
			if (list == null) {
				JOptionPane.showMessageDialog(frame, "Select a list first.");
				return;
			}
			int answer = JOptionPane.showConfirmDialog(frame,
					"Delete \"" + list.getlTitle() + "\" and all of its tasks?", "Delete list",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (answer == JOptionPane.YES_OPTION) {
				if (SQLite.deleteList(list)) {
					refreshLists(-1);
				} else {
					JOptionPane.showMessageDialog(frame, "Could not delete the list.");
				}
			}
		});

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(buildHeader("To Do Lists", btnNewList, btnEditList, btnDelList), BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(toDoListsView), BorderLayout.CENTER);
		gridpanel.add(leftPanel);

		// ---------- Center: tasks ----------
		table = new JTable(taskModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				showDetails(getSelectedTask());
			}
		});
		// Completed tasks are gray, overdue tasks are red
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					Color fg = t.getForeground();
					if (row < currentTasks.size()) {
						Task task = currentTasks.get(row);
						if (task.gettStat() == Task.COMPLETED) {
							fg = Color.GRAY;
						} else if (task.isOverdue()) {
							fg = new Color(180, 0, 0);
						}
					}
					c.setForeground(fg);
				}
				return c;
			}
		});

		JButton btnNewTask = new JButton("+");
		btnNewTask.setToolTipText("New task");
		btnNewTask.addActionListener(e -> {
			TDList list = getSelectedList();
			if (list == null) {
				JOptionPane.showMessageDialog(frame, "Select a list first.");
				return;
			}
			NewTaskPopup ntp = new NewTaskPopup(frame, list, null, saved -> loadTasks(saved.getId()));
			ntp.setVisible(true);
		});

		JButton btnEditTask = new JButton("Edit");
		btnEditTask.addActionListener(e -> {
			Task task = getSelectedTask();
			if (task == null) {
				JOptionPane.showMessageDialog(frame, "Select a task first.");
				return;
			}
			NewTaskPopup ntp = new NewTaskPopup(frame, getSelectedList(), task, saved -> loadTasks(saved.getId()));
			ntp.setVisible(true);
		});

		JButton btnDoneTask = new JButton("Done");
		btnDoneTask.setToolTipText("Mark the task complete (or not complete)");
		btnDoneTask.addActionListener(e -> {
			Task task = getSelectedTask();
			if (task == null) {
				JOptionPane.showMessageDialog(frame, "Select a task first.");
				return;
			}
			task.settStat(task.gettStat() == Task.COMPLETED ? Task.NOT_STARTED : Task.COMPLETED);
			if (!SQLite.updateTask(task)) {
				JOptionPane.showMessageDialog(frame, "Could not update the task.");
			}
			loadTasks(task.getId());
		});

		JButton btnDelTask = new JButton("Delete");
		btnDelTask.addActionListener(e -> {
			Task task = getSelectedTask();
			if (task == null) {
				JOptionPane.showMessageDialog(frame, "Select a task first.");
				return;
			}
			int answer = JOptionPane.showConfirmDialog(frame, "Delete \"" + task.gettTitle() + "\"?",
					"Delete task", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (answer == JOptionPane.YES_OPTION) {
				if (!SQLite.deleteTask(task)) {
					JOptionPane.showMessageDialog(frame, "Could not delete the task.");
				}
				loadTasks(-1);
			}
		});

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(buildHeader("Tasks", btnNewTask, btnEditTask, btnDoneTask, btnDelTask), BorderLayout.NORTH);
		centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		gridpanel.add(centerPanel);

		// ---------- Right: details ----------
		detailsArea = new JTextArea();
		detailsArea.setEditable(false);
		detailsArea.setLineWrap(true);
		detailsArea.setWrapStyleWord(true);
		detailsArea.setMargin(new Insets(6, 6, 6, 6));

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(buildHeader("Details View"), BorderLayout.NORTH);
		rightPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
		gridpanel.add(rightPanel);

		// ---------- Top bar ----------
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		lblUser = new JLabel("");
		toolBar.add(lblUser);
		toolBar.add(javax.swing.Box.createHorizontalGlue());
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(e -> doLogout());
		toolBar.add(btnLogout);
		cardMain.add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * For debugging
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * For debugging
	 */
	public JTextField getTxtLoginUN() {
		return txtLoginUN;
	}

	/**
	 * For debugging
	 */
	public JPasswordField getTxtLoginPW() {
		return txtLoginPW;
	}

	/**
	 * For debugging
	 */
	public JPanel getCardLogin() {
		return cardLogin;
	}

	/**
	 * For debugging
	 */
	public JPanel getCardMain() {
		return cardMain;
	}
}