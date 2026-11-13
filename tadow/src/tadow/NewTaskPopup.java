package tadow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewTaskPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textTaskTitle;
	private JTextField textTaskDesc;
	private JTextField textDueDate;
	private JTextField textFreq;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewTaskPopup dialog = new NewTaskPopup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewTaskPopup() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTaskName = new JLabel("Task Name:");
			GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
			gbc_lblTaskName.anchor = GridBagConstraints.EAST;
			gbc_lblTaskName.insets = new Insets(0, 0, 5, 5);
			gbc_lblTaskName.gridx = 0;
			gbc_lblTaskName.gridy = 0;
			contentPanel.add(lblTaskName, gbc_lblTaskName);
		}
		{
			textTaskTitle = new JTextField();
			GridBagConstraints gbc_textTaskTitle = new GridBagConstraints();
			gbc_textTaskTitle.insets = new Insets(0, 0, 5, 0);
			gbc_textTaskTitle.fill = GridBagConstraints.HORIZONTAL;
			gbc_textTaskTitle.gridx = 1;
			gbc_textTaskTitle.gridy = 0;
			contentPanel.add(textTaskTitle, gbc_textTaskTitle);
			textTaskTitle.setColumns(10);
		}
		{
			JLabel lblTaskDesc = new JLabel("Description:");
			GridBagConstraints gbc_lblTaskDesc = new GridBagConstraints();
			gbc_lblTaskDesc.anchor = GridBagConstraints.EAST;
			gbc_lblTaskDesc.insets = new Insets(0, 0, 5, 5);
			gbc_lblTaskDesc.gridx = 0;
			gbc_lblTaskDesc.gridy = 1;
			contentPanel.add(lblTaskDesc, gbc_lblTaskDesc);
		}
		{
			textTaskDesc = new JTextField();
			GridBagConstraints gbc_textTaskDesc = new GridBagConstraints();
			gbc_textTaskDesc.insets = new Insets(0, 0, 5, 0);
			gbc_textTaskDesc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textTaskDesc.gridx = 1;
			gbc_textTaskDesc.gridy = 1;
			contentPanel.add(textTaskDesc, gbc_textTaskDesc);
			textTaskDesc.setColumns(10);
		}
		{
			JLabel lblDueDate = new JLabel("Due Date:");
			GridBagConstraints gbc_lblDueDate = new GridBagConstraints();
			gbc_lblDueDate.anchor = GridBagConstraints.EAST;
			gbc_lblDueDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblDueDate.gridx = 0;
			gbc_lblDueDate.gridy = 2;
			contentPanel.add(lblDueDate, gbc_lblDueDate);
		}
		{
			textDueDate = new JTextField();
			textDueDate.setText("Use MM/DD/HH Format");
			GridBagConstraints gbc_textDueDate = new GridBagConstraints();
			gbc_textDueDate.insets = new Insets(0, 0, 5, 0);
			gbc_textDueDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDueDate.gridx = 1;
			gbc_textDueDate.gridy = 2;
			contentPanel.add(textDueDate, gbc_textDueDate);
			textDueDate.setColumns(10);
		}
		{
			JLabel lblRecur = new JLabel("Is Repeated:");
			GridBagConstraints gbc_lblRecur = new GridBagConstraints();
			gbc_lblRecur.insets = new Insets(0, 0, 5, 5);
			gbc_lblRecur.gridx = 0;
			gbc_lblRecur.gridy = 3;
			contentPanel.add(lblRecur, gbc_lblRecur);
		}
		{
			JRadioButton rdbtnIsRecur = new JRadioButton("Task Repeats");
			GridBagConstraints gbc_rdbtnIsRecur = new GridBagConstraints();
			gbc_rdbtnIsRecur.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnIsRecur.gridx = 1;
			gbc_rdbtnIsRecur.gridy = 3;
			contentPanel.add(rdbtnIsRecur, gbc_rdbtnIsRecur);
		}
		{
			JLabel lblFreq = new JLabel("Frequency:");
			GridBagConstraints gbc_lblFreq = new GridBagConstraints();
			gbc_lblFreq.anchor = GridBagConstraints.EAST;
			gbc_lblFreq.insets = new Insets(0, 0, 0, 5);
			gbc_lblFreq.gridx = 0;
			gbc_lblFreq.gridy = 4;
			contentPanel.add(lblFreq, gbc_lblFreq);
		}
		{
			textFreq = new JTextField();
			GridBagConstraints gbc_textFreq = new GridBagConstraints();
			gbc_textFreq.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFreq.gridx = 1;
			gbc_textFreq.gridy = 4;
			contentPanel.add(textFreq, gbc_textFreq);
			textFreq.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
