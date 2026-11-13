package tadow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTree;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Main {

	private JFrame frame;
	private JTextField txtLoginUN;
	private JTextField txtLoginPW;
	private JPanel cardLogin;
	private JPanel cardMain;
	// model for JList
	DefaultListModel<TDList> listModel = new DefaultListModel<>();
	String[] columnNames = {"Task", "Due Date"};
	DefaultTableModel taskModel = new DefaultTableModel(columnNames, 0);
	
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SQLite.connect();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Login Card
		//initLoginCard();
		// TEMPORARY to edit display
		initMainCard();
		
		frame.getContentPane().add(cardLogin);
		
		cardLogin.setVisible(true); // Enable login window by default
		
	}
	
	/**
	 * Open the login card
	 */
	private void initLoginCard() {
		// 1. Close other cards
		closeAllBut(cardLogin);
		
		// 2. Instantiate UI
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
		
		JLabel lblSignupStatus = new JLabel("");
		lblSignupStatus.setBounds(278, 289, 222, 14);
		cardLogin.add(lblSignupStatus);
		
		txtLoginUN = new JTextField();
		txtLoginUN.setBounds(220, 151, 126, 27);
		cardLogin.add(txtLoginUN);
		txtLoginUN.setColumns(10);
		
		txtLoginPW = new JTextField();
		txtLoginPW.setColumns(10);
		txtLoginPW.setBounds(418, 151, 126, 27);
		cardLogin.add(txtLoginPW);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SQLite.login(txtLoginUN.getText(), txtLoginPW.getText())) {
					cardLogin.setVisible(false);
					SQLite.getListsForUser(SQLite.UserSession.getUserName());
					
					// Set the default model for the JList with the retrieved lists
					listModel.clear();
					for (TDList list : SQLite.getListsForUser(SQLite.UserSession.getUserName())) {
						listModel.addElement(list);
					}
					
					
					// Main Card
					initMainCard();
					frame.getContentPane().add(cardMain);
					cardMain.setVisible(true);
					//cardMain.setVisible(false); // Disable main window by default
				}
				
			}
		});
		btnLogin.setBounds(328, 218, 110, 27);
		cardLogin.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SQLite.signup(txtLoginUN.getText(), txtLoginPW.getText())) {
					lblSignupStatus.setText("Sign up successful! You can now log in.");
				}
				else {
					lblSignupStatus.setText("Sign up failed! Please try again.");
				}
			}
		});
		btnSignUp.setBounds(328, 251, 110, 27);
		cardLogin.add(btnSignUp);
	}
	
	private void initMainCard() {
		// 1. Close other cards
		closeAllBut(cardLogin);
		
		// 2. Instantiate UI
		cardMain = new JPanel();
		cardMain.setLayout(new BorderLayout(0, 0));
		
		JPanel gridpanel = new JPanel();
		cardMain.add(gridpanel, BorderLayout.CENTER);
		gridpanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JScrollPane scrollPane_left = new JScrollPane();
		gridpanel.add(scrollPane_left);
		
		JList<TDList> toDoListsView = new JList<>(listModel);
		toDoListsView.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					TDList selectedList = toDoListsView.getSelectedValue();
					if (selectedList != null) {
						System.out.println("Selected list: " + selectedList.getlTitle());
						// Here you can update the tasks view based on the selected list
					}
				}
			}
		});
		// Set the model for the JList
		//toDoListsView.setModel(listModel);
		scrollPane_left.setViewportView(toDoListsView);
		
		JPanel leftHead = new JPanel();
		scrollPane_left.setColumnHeaderView(leftHead);
		GridBagLayout gbl_leftHead = new GridBagLayout();
		gbl_leftHead.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_leftHead.rowHeights = new int[] {0, 0};
		gbl_leftHead.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_leftHead.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		leftHead.setLayout(gbl_leftHead);
		
		JLabel lblLeftHead = new JLabel("To Do Lists");
		GridBagConstraints gbc_lblLeftHead = new GridBagConstraints();
		gbc_lblLeftHead.insets = new Insets(0, 0, 0, 5);
		gbc_lblLeftHead.gridx = 0;
		gbc_lblLeftHead.gridy = 0;
		leftHead.add(lblLeftHead, gbc_lblLeftHead);
		
		JButton btnNewList = new JButton("+");
		btnNewList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewListPopup nlp = new NewListPopup();
				nlp.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewList = new GridBagConstraints();
		gbc_btnNewList.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewList.gridwidth = 2;
		gbc_btnNewList.gridx = 4;
		gbc_btnNewList.gridy = 0;
		leftHead.add(btnNewList, gbc_btnNewList);
		
		JButton btnEditList = new JButton("Edit");
		btnEditList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnEditList = new GridBagConstraints();
		gbc_btnEditList.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditList.gridx = 6;
		gbc_btnEditList.gridy = 0;
		leftHead.add(btnEditList, gbc_btnEditList);
		
		JScrollPane scrollPane_center = new JScrollPane();
		gridpanel.add(scrollPane_center);
		
		JPanel centerHead = new JPanel();
		scrollPane_center.setColumnHeaderView(centerHead);
		GridBagLayout gbl_centerHead = new GridBagLayout();
		gbl_centerHead.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_centerHead.rowHeights = new int[]{0, 0};
		gbl_centerHead.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_centerHead.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		centerHead.setLayout(gbl_centerHead);
		
		JLabel lblCentHead = new JLabel("Tasks");
		GridBagConstraints gbc_lblCentHead = new GridBagConstraints();
		gbc_lblCentHead.insets = new Insets(0, 0, 0, 5);
		gbc_lblCentHead.gridx = 0;
		gbc_lblCentHead.gridy = 0;
		centerHead.add(lblCentHead, gbc_lblCentHead);
		
		JButton btnNewTask = new JButton("+");
		btnNewTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewTask = new GridBagConstraints();
		gbc_btnNewTask.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewTask.gridwidth = 2;
		gbc_btnNewTask.gridx = 4;
		gbc_btnNewTask.gridy = 0;
		centerHead.add(btnNewTask, gbc_btnNewTask);
		
		JButton btnEditTask = new JButton("Edit");
		btnEditTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewTaskPopup ntp = new NewTaskPopup();
				ntp.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnEditTask = new GridBagConstraints();
		gbc_btnEditTask.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditTask.gridx = 6;
		gbc_btnEditTask.gridy = 0;
		centerHead.add(btnEditTask, gbc_btnEditTask);
		
		JPanel panel = new JPanel();
		scrollPane_center.setViewportView(panel);
		
		table = new JTable(taskModel);
		panel.add(table);
		
		JScrollPane scrollPane_right = new JScrollPane();
		gridpanel.add(scrollPane_right);
		
		JLabel lblRightHead = new JLabel("Details View");
		scrollPane_right.setColumnHeaderView(lblRightHead);
		
		JToolBar toolBar = new JToolBar();
		cardMain.add(toolBar, BorderLayout.NORTH);
		
	}
	
	/**
	 * Close every card except {@code toOpen}
	 * @param toOpen the card to leave opened
	 */
	private void closeAllBut(JPanel toOpen) {
		if (cardLogin != null && cardLogin != toOpen)
			cardLogin.setVisible(false);
		if (cardMain != null && cardMain != toOpen)
			cardMain.setVisible(false);
	}

	/**
	 * For debugging
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * For debugging
	 * @return
	 */
	public JTextField getTxtLoginUN() {
		return txtLoginUN;
	}

	/**
	 * For debugging
	 * @return
	 */
	public JTextField getTxtLoginPW() {
		return txtLoginPW;
	}

	/**
	 * For debugging
	 * @return
	 */
	public JPanel getCardLogin() {
		return cardLogin;
	}

	/**
	 * For debugging
	 * @return
	 */
	public JPanel getCardMain() {
		return cardMain;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
