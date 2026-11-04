package tadow;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
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

public class Main {

	private JFrame frame;
	private JTextField txtLoginUN;
	private JTextField txtLoginPW;
	private JPanel cardLogin;
	private JPanel cardMain;

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
		initLoginCard();
		
		// Main Card
		initMainCard();
		
		frame.getContentPane().add(cardLogin);
		cardLogin.setVisible(true); // Enable login window by default
		cardMain.setVisible(false); // Disable main window by default
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
				if (SQLite.login(txtLoginUN.getText(), txtLoginPW.getText())); {
					cardLogin.setVisible(false);
					cardMain.setVisible(true);
				}
				
			}
		});
		btnLogin.setBounds(328, 218, 110, 27);
		cardLogin.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SQLite.signup(txtLoginUN.getText(), txtLoginPW.getText());
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
		cardMain.setLayout(new BorderLayout(4, 0));
		
		JScrollPane paneLeft = new JScrollPane();
		cardMain.add(paneLeft, BorderLayout.WEST);
		
		JList listOfLists = new JList();
		paneLeft.setViewportView(listOfLists);
		
		JScrollPane paneCenter = new JScrollPane();
		paneCenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cardMain.add(paneCenter, BorderLayout.CENTER);
		
		JTree taskTree = new JTree();
		paneCenter.setViewportView(taskTree);
		
		JScrollPane paneRight = new JScrollPane();
		cardMain.add(paneRight, BorderLayout.EAST);
		
		JList taskView = new JList();
		paneRight.setViewportView(taskView);
		
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
