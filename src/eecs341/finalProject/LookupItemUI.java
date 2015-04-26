package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LookupItemUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	
	public LookupItemUI(JFrame parent, SQLConnection db) {
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
		frame.setTitle("Lookup Item");
		
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		JTextField itemIDField = new JTextField();
		JTextArea detailsList = new JTextArea();
		JButton search = new JButton("Search");
		JButton back = new JButton("Back");
		
		itemIDLabel.setBounds(20, 20, 80, 20);
		itemIDField.setBounds(110, 20, 80, 20);
		detailsList.setBounds(20, 60, 260, 250);
		search.setBounds(200, 20, 80, 20);
		back.setBounds(10, 330, 90, 40);
		
		detailsList.setEditable(false);
		detailsList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		itemIDLabel.setEditable(false);
		
		frame.add(itemIDLabel);
		frame.add(itemIDField);
		frame.add(detailsList);
		frame.add(search);
		frame.add(back);
		
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int itemID = Integer.parseInt(itemIDField.getText());
				String itemName;
				int supplierID;
				double currentPrice;
				try {
					ResultSet rs = db.runQueryString("SELECT itemID, name, supplierID, currentPrice FROM Items WHERE itemID = " + itemID);
					if (rs.next()) {
						itemName = rs.getString(2);
						supplierID = Integer.parseInt(rs.getString(3));
						currentPrice = Double.parseDouble(rs.getString(4));
						detailsList.setText(String.format("%3s  %25s  %3s  $%2.2f",itemID, itemName, supplierID, currentPrice));
						if (rs.next()) {
							new PopupUI("Item collision", "The item ID you entered, " + itemID + ", was found more than once in the database.");
						}
					} else {
						new PopupUI("Item not found", "The item ID you entered, " + itemID + ", was not found in the database.");
					}
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
				} catch (NumberFormatException e) {
					new PopupUI(e.toString(), e.getMessage());
				}
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

}
