package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;


public class MakeMemberUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;

	public MakeMemberUI(JFrame parent, SQLConnection db) {
		this.parent = parent;
		this.db = db;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Add Prescription");
		JTextArea nameLabel = new JTextArea("Name:");
		JTextField nameField = new JTextField();
		JTextArea addressLabel = new JTextArea("Address:");
		JTextField addressField = new JTextField();
		JTextArea phoneLabel = new JTextArea("Phone:");
		JTextField phoneField = new JTextField();
		JButton add = new JButton("Add");

		
		nameLabel.setBounds(20, 20, 120, 20);
		nameField.setBounds(160, 20, 120, 20);
		addressLabel.setBounds(20, 60, 120, 20);
		addressField.setBounds(160, 60, 120, 20);
		phoneLabel.setBounds(20, 100, 120, 20);
		phoneField.setBounds(160, 100, 120, 20);
		add.setBounds(105, 300, 90, 50);


		nameLabel.setEditable(false);
		addressLabel.setEditable(false);
		phoneLabel.setEditable(false);
		
		frame.add(nameLabel);
		frame.add(nameField);
		frame.add(addressLabel);
		frame.add(addressField);
		frame.add(phoneLabel);
		frame.add(phoneField);
		frame.add(add);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String name = nameField.getText();
				String address = addressField.getText();
				String phone = phoneField.getText();
				int memberID;
				try {
					memberID = db.runUpdateString("INSERT INTO AwardsClubMember (name, address, phoneNumber, credits)"
							                    + "VALUES ('" + name + "', '" + address + "', '" + phone + "', " + 0 + ")");
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				}
				if (parent instanceof CheckoutUI) {
					((CheckoutUI)parent).callbackSetMemberID(memberID);
				}
				frame.dispose();
			}
		});
	}
}
