package tadow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Dialog for creating a new list, or editing an existing one.
 */
public class NewListPopup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final TDList existing; // null when creating a new list
	private final Consumer<TDList> onSaved;

	private JTextField textNewListTitle;
	private JTextField textNewListDesc;
	private JTextField textNewListTeam;
	private JRadioButton rdbtnNewListTeam;

	/**
	 * @param owner   window the dialog is centered on (Main's frame)
	 * @param existing the list to edit, or null to create a new list
	 * @param onSaved called after the list is saved, with the saved list (its id is set); may be null
	 */
<<<<<<< HEAD
	/*public static void main(String[] args) {
		try {
			NewListPopup dialog = new NewListPopup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
=======
	public NewListPopup(Window owner, TDList existing, Consumer<TDList> onSaved) {
		super(owner, existing == null ? "New List" : "Edit List", ModalityType.APPLICATION_MODAL);
		this.existing = existing;
		this.onSaved = onSaved;
>>>>>>> 23a8c0f9c19808aeb019bfaf41f629bc41c72cfb

		setBounds(100, 100, 450, 240);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 5, 0));

		JLabel lblNewListTitle = new JLabel("List Name:");
		lblNewListTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblNewListTitle);

		textNewListTitle = new JTextField();
		contentPanel.add(textNewListTitle);
		textNewListTitle.setColumns(10);

		JLabel lblNewListDesc = new JLabel("Description:");
		lblNewListDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblNewListDesc);

		textNewListDesc = new JTextField();
		contentPanel.add(textNewListDesc);
		textNewListDesc.setColumns(10);

		JLabel lblSpacer = new JLabel("");
		contentPanel.add(lblSpacer);

		rdbtnNewListTeam = new JRadioButton("Team Share");
		contentPanel.add(rdbtnNewListTeam);

		JLabel lblNewListTeam = new JLabel("Team Name:");
		lblNewListTeam.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(lblNewListTeam);

		textNewListTeam = new JTextField();
		contentPanel.add(textNewListTeam);
		textNewListTeam.setColumns(10);

		// The team name only matters when the list is shared
		textNewListTeam.setEnabled(false);
		rdbtnNewListTeam.addActionListener(e -> textNewListTeam.setEnabled(rdbtnNewListTeam.isSelected()));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> saveList());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		if (existing != null) {
			textNewListTitle.setText(existing.getlTitle());
			textNewListDesc.setText(existing.getlDesc());
			rdbtnNewListTeam.setSelected(existing.isShared());
			textNewListTeam.setText(existing.getTeamName());
			textNewListTeam.setEnabled(existing.isShared());
		}
	}

	private void saveList() {
		String nTitle = textNewListTitle.getText().trim();
		if (nTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a list name.");
			return;
		}

		boolean shared = rdbtnNewListTeam.isSelected();
		String team = shared ? textNewListTeam.getText().trim() : "";
		if (shared && team.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a team name for a shared list.");
			return;
		}

		// Build the list to save; the existing object is only used for its id
		TDList toSave = new TDList(nTitle);
		toSave.setlDesc(textNewListDesc.getText().trim());
		toSave.setShared(shared);
		toSave.setTeamName(team);

		boolean ok;
		if (existing == null) {
			ok = SQLite.addList(toSave); // also sets toSave's id
		} else {
			toSave.setId(existing.getId());
			ok = SQLite.updateList(toSave);
		}

		if (!ok) {
			JOptionPane.showMessageDialog(this, "Could not save the list.");
			return;
		}

		if (onSaved != null) {
			onSaved.accept(toSave);
		}
		dispose();
	}
}