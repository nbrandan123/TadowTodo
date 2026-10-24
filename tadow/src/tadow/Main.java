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

public class Main {

	private JFrame frame;
	private JTextField txtLoginUN;
	private JTextField txtLoginPW;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		
		JPanel cardLogin = new JPanel();
		frame.getContentPane().add(cardLogin);
		cardLogin.setLayout(null);
		
		JLabel lblLoginTitle = new JLabel("Tadow");
		lblLoginTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setBounds(154, 36, 115, 35);
		cardLogin.add(lblLoginTitle);
		
		JLabel lblLoginUN = new JLabel("Username:");
		lblLoginUN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginUN.setBounds(51, 117, 94, 35);
		cardLogin.add(lblLoginUN);
		
		JLabel lblLoginPW = new JLabel("Password:");
		lblLoginPW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoginPW.setBounds(249, 117, 94, 35);
		cardLogin.add(lblLoginPW);
		
		txtLoginUN = new JTextField();
		txtLoginUN.setBounds(51, 150, 126, 27);
		cardLogin.add(txtLoginUN);
		txtLoginUN.setColumns(10);
		
		txtLoginPW = new JTextField();
		txtLoginPW.setColumns(10);
		txtLoginPW.setBounds(249, 150, 126, 27);
		cardLogin.add(txtLoginPW);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(51, 222, 110, 27);
		cardLogin.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(265, 222, 110, 27);
		cardLogin.add(btnSignUp);
		
		JPanel cardMain = new JPanel();
		frame.getContentPane().add(cardMain);
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
}
