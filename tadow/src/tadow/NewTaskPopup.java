package tadow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Dialog for creating a new task, or editing an existing one.
 */
public class NewTaskPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final TDList targetList;
	private final Task existing; // null when creating a new task
	private final Consumer<Task> onSaved;

	private JTextField textTaskTitle;
	private JTextField textTaskDesc;
	private JTextField textDueDate;
	private JTextField textFreq;
	private JRadioButton rdbtnIsRecur;

	/**
	 * @param owner      window the dialog is centered on (Main's frame)
	 * @param targetList the list the task belongs to (the list selected in Main)
	 * @param existing   the task to edit, or null to create a new task
	 * @param onSaved    called after the task is saved, with the saved task (its id is set); may be null
	 */
	public NewTaskPopup(Window owner, TDList targetList, Task existing, Consumer<Task> onSaved) {
		super(owner, existing == null ? "New Task" : "Edit Task", ModalityType.APPLICATION_MODAL);
		this.targetList = targetList;
		this.existing = existing;
		this.onSaved = onSaved;

		setBounds(100, 100, 450, 260);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);

		textTaskTitle = addRow("Task Name:", 0);
		textTaskDesc = addRow("Description:", 1);
		textDueDate = addRow("Due (MM/DD/YYYY):", 2);

		JLabel lblRecur = new JLabel("Is Repeated:");
		GridBagConstraints gbc_lblRecur = new GridBagConstraints();
		gbc_lblRecur.anchor = GridBagConstraints.EAST;
		gbc_lblRecur.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecur.gridx = 0;
		gbc_lblRecur.gridy = 3;
		contentPanel.add(lblRecur, gbc_lblRecur);

		rdbtnIsRecur = new JRadioButton("Task Repeats");
		GridBagConstraints gbc_rdbtnIsRecur = new GridBagConstraints();
		gbc_rdbtnIsRecur.anchor = GridBagConstraints.WEST;
		gbc_rdbtnIsRecur.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnIsRecur.gridx = 1;
		gbc_rdbtnIsRecur.gridy = 3;
		contentPanel.add(rdbtnIsRecur, gbc_rdbtnIsRecur);

		textFreq = addRow("Frequency:", 4);

		// Frequency only matters when the task repeats
		textFreq.setEnabled(false);
		rdbtnIsRecur.addActionListener(e -> textFreq.setEnabled(rdbtnIsRecur.isSelected()));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> saveTask());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		if (existing != null) {
			textTaskTitle.setText(existing.gettTitle());
			textTaskDesc.setText(existing.gettDesc());
			textDueDate.setText(existing.gettDate());
			rdbtnIsRecur.setSelected(existing.isRep());
			textFreq.setText(existing.getRepInterval());
			textFreq.setEnabled(existing.isRep());
		}
	}

	/**
	 * Adds a label + text field row to the form and returns the text field.
	 */
	private JTextField addRow(String labelText, int row) {
		boolean last = (row == 4);

		JLabel label = new JLabel(labelText);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, last ? 0 : 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = row;
		contentPanel.add(label, gbc_label);

		JTextField field = new JTextField();
		GridBagConstraints gbc_field = new GridBagConstraints();
		gbc_field.insets = new Insets(0, 0, last ? 0 : 5, 0);
		gbc_field.fill = GridBagConstraints.HORIZONTAL;
		gbc_field.gridx = 1;
		gbc_field.gridy = row;
		contentPanel.add(field, gbc_field);
		field.setColumns(10);
		return field;
	}

	private void saveTask() {
		String taskTitle = textTaskTitle.getText().trim();
		if (taskTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a task name.");
			return;
		}

		String due = textDueDate.getText().trim();
		if (!Task.isValidDate(due)) {
			JOptionPane.showMessageDialog(this, "Please enter the due date as MM/DD/YYYY (for example 03/07/2026),\nor leave it blank.");
			return;
		}

		// Build the task to save; the existing object is only used for its id and status
		Task toSave = new Task(taskTitle);
		toSave.settDesc(textTaskDesc.getText().trim());
		toSave.settDate(Task.normalizeDate(due));
		boolean repeats = rdbtnIsRecur.isSelected();
		toSave.setRep(repeats);
		toSave.setRepInterval(repeats ? textFreq.getText().trim() : "");

		boolean ok;
		if (existing == null) {
			ok = SQLite.addTask(toSave, targetList); // also sets toSave's id
		} else {
			toSave.setId(existing.getId());
			toSave.settStat(existing.gettStat());
			ok = SQLite.updateTask(toSave);
		}

		if (!ok) {
			JOptionPane.showMessageDialog(this, "Could not save the task.");
			return;
		}

		if (onSaved != null) {
			onSaved.accept(toSave);
		}
		dispose();
	}
}