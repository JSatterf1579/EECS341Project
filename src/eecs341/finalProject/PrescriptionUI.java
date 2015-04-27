package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class PrescriptionUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	protected SQLConnection db;
	private int storeID;

	public PrescriptionUI(JFrame parent, SQLConnection db, int storeID) {
		this.parent = parent;
		this.db = db;
		this.storeID = storeID;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Prescription");
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		JTextField itemIDField = new JTextField();
		JTextArea prescriberNameLabel = new JTextArea("Prescriber Name:");
		JTextField prescriberNameField = new JTextField();
		JTextArea amountLabel = new JTextArea("Amount:");
		JTextField amountField = new JTextField();
		JTextArea unitLabel = new JTextArea("Unit:");
		JTextField unitField = new JTextField();
		JTextArea frequencyLabel = new JTextArea("Frequency:");
		JTextField frequencyField = new JTextField();
		JButton add = new JButton("Add");

		itemIDLabel.setBounds(20, 20, 120, 20);
		itemIDField.setBounds(160, 20, 120, 20);
		prescriberNameLabel.setBounds(20, 60, 120, 20);
		prescriberNameField.setBounds(160, 60, 120, 20);
		amountLabel.setBounds(20, 100, 120, 20);
		amountField.setBounds(160, 100, 120, 20);
		unitLabel.setBounds(20, 140, 120, 20);
		unitField.setBounds(160, 140, 120, 20);
		frequencyLabel.setBounds(20, 180, 120, 20);
		frequencyField.setBounds(160, 180, 120, 20);
		add.setBounds(105, 300, 90, 50);


		itemIDLabel.setEditable(false);
		prescriberNameLabel.setEditable(false);
		amountLabel.setEditable(false);
		unitLabel.setEditable(false);
		frequencyLabel.setEditable(false);
		
		frame.add(itemIDLabel);
		frame.add(itemIDField);
		frame.add(prescriberNameLabel);
		frame.add(prescriberNameField);
		frame.add(amountLabel);
		frame.add(amountField);
		frame.add(unitLabel);
		frame.add(unitField);
		frame.add(frequencyLabel);
		frame.add(frequencyField);
		frame.add(add);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int itemID;
				String prescriberName;
				int amount;
				String unit;
				String frequency;
				try {
					itemID = Integer.parseInt(itemIDField.getText());
				} catch (NumberFormatException e) {
					new PopupUI("Bad item ID", "The item ID must be an integer.");
					return;
				}
				prescriberName = prescriberNameField.getText();
				try {
					amount = Integer.parseInt(amountField.getText());
				} catch (NumberFormatException e) {
					new PopupUI("Bad amount", "The amount must be an integer.");
					return;
				}
				unit = unitField.getText();
				frequency = frequencyField.getText();
				int prescriptionID;
				try {
					prescriptionID = dbInsertNewPrescription(itemID, prescriberName, amount, unit, frequency);
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				} finally {
					frame.dispose();
				}
				if (parent instanceof MakePurchaseUI) {
					((MakePurchaseUI)parent).callbackUsePrescription(prescriptionID);
				}
			}
		});
	}
	
	/* returns new perscriptionID */
	int dbInsertNewPrescription(int itemID, String prescriberName, int amount, String unit, String frequency) throws SQLConnectionException, SQLException {
		db.runUpdateString("INSERT INTO Prescription (itemID, prescriberName, amountGiven, unit, fillingFrequency)"
				+ "VALUES (" + itemID + ", '" + prescriberName + "', " + amount + ", '" + unit + "', '" + frequency + "')");
		ResultSet rs = db.runQueryString("SELECT LAST_INSERT_ID()");
		if (rs.next()) {
			return Integer.parseInt(rs.getString(1));
		} else {
			throw new SQLException("The perscription added successfully, but there was a problem getting the automatically assigned ID");
		}
	}
}

// TODO STORE
