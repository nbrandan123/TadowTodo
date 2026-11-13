package tadow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewListPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textNewListTitle;
	private JTextField textNewListDesc;
	private JTextField textNewListTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewListPopup dialog = new NewListPopup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewListPopup() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 5, 0));
		{
			JLabel lblNewListTitle = new JLabel("List Name:");
			lblNewListTitle.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewListTitle);
		}
		{
			textNewListTitle = new JTextField();
			contentPanel.add(textNewListTitle);
			textNewListTitle.setColumns(10);
		}
		{
			JLabel lblNewListDesc = new JLabel("Description:");
			lblNewListDesc.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewListDesc);
		}
		{
			textNewListDesc = new JTextField();
			contentPanel.add(textNewListDesc);
			textNewListDesc.setColumns(10);
		}
		{
			JLabel lblSpacer = new JLabel("");
			contentPanel.add(lblSpacer);
		}
		{
			JRadioButton rdbtnNewListTeam = new JRadioButton("Team Share");
			contentPanel.add(rdbtnNewListTeam);
		}
		{
			JLabel lblNewListTeam = new JLabel("Team Name:");
			lblNewListTeam.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewListTeam);
		}
		{
			textNewListTeam = new JTextField();
			contentPanel.add(textNewListTeam);
			textNewListTeam.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Create a new TDlist object with the provided title and description
						String nTitle = textNewListTitle.getText();
						String nDesc = textNewListDesc.getText();
						TDList newList = new TDList(nTitle);
						newList.setlDesc(nDesc);
						
						// Send new list to database
						SQLite.addList(newList);
						
						// Close the popup
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
