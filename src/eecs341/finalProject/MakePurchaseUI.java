package eecs341.finalProject;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MakePurchaseUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	protected DefaultListModel<String> itemListModel;
	protected SQLConnection db;
	
	/*
	public MakePurchaseUI() {
		itemListModel = new DefaultListModel<String>();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				db = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.database, DBInfo.account, DBInfo.password);
				try {
					db.initializeConnection();
					launchDisplay();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	public MakePurchaseUI(DatabaseUI parent) {
		itemListModel = new DefaultListModel<String>();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				db = parent.db;
				try {
					launchDisplay();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void launchDisplay() throws SQLConnectionException, SQLException {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Make Purchase");
		JList<String> itemList = new JList<String>(itemListModel);
		JTextField item = new JTextField();
		JTextArea itemlabel = new JTextArea("Item ID: ");
		JButton addItem = new JButton("Add Item");
		JButton removeItem = new JButton("Remove Item");
		JButton addPrescription = new JButton("Prescription");
		JButton back = new JButton("Back");
		JButton checkout = new JButton("Checkout...");
		
		itemlabel.setBounds(300, 10, 90, 20);
		item.setBounds(300, 40, 90, 20);
		addItem.setBounds(300, 70, 90, 20);
		removeItem.setBounds(300, 100, 90, 20);
		itemList.setBounds(10, 10, 280, 300);
		addPrescription.setBounds(155, 320, 90, 50);
		back.setBounds(10, 320, 90, 50);
		checkout.setBounds(300, 320, 90, 50);
		
		itemList.setFont(new Font("monospaced", Font.PLAIN, 12));
		itemList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		itemlabel.setEditable(false);
		
		frame.add(item);
		frame.add(itemlabel);
		frame.add(addItem);
		frame.add(removeItem);
		frame.add(itemList);
		frame.add(addPrescription);
		frame.add(back);
		frame.add(checkout);
		frame.setSize(400, 400);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int itemID;
				try {
					itemID = Integer.parseInt(item.getText());
				} catch (NumberFormatException e) {
					new PopupUI("Bad item ID", "The item ID must be an integer.");
					return;
				} finally {
					item.setText("");
				}
				addItem(itemID);
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = itemList.getSelectedIndex();
				if (index < 0) {
					new PopupUI("No item selected", "You must select an item to remove it from the purchase");
				} else {
					itemListModel.remove(index);
				}
			}
		});
		
		addPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PrescriptionUI(MakePurchaseUI.this);
			}
		});
		
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CheckoutUI(MakePurchaseUI.this);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	
	public void addItem(int itemID) {
		try {
			String itemName;
			double currentPrice;
			ResultSet rs = db.runQueryString("SELECT itemID, name, currentPrice FROM Items WHERE itemID = " + itemID);
			if (rs.next()) {
				itemName = rs.getString(2);
				currentPrice = Double.parseDouble(rs.getString(3));
				itemListModel.addElement(String.format("%3s  %25s  $%2.2f",itemID, itemName, currentPrice));
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
	
	public void addPrescription(int prescriptionID) {
		ResultSet rs;
		try {
			rs = db.runQueryString("SELECT prescriptionID, itemID FROM Prescription WHERE prescriptionID = " + prescriptionID);
			if (rs.next()) {
				int itemID = Integer.parseInt(rs.getString(2));
				addItem(itemID);
				if (rs.next()) {
					new PopupUI("Prescription collision", "The prescription ID " + prescriptionID + " was found more than once in the database.");
				}
			} else {
				new PopupUI("Prescription not found", "The prescription ID " + prescriptionID + " was not found in the database.");
			}
		} catch (SQLConnectionException e) {
			new PopupUI(e.toString(), e.getMessage());
		} catch (SQLException e) {
			new PopupUI(e.toString(), e.getMessage());
		} catch (NumberFormatException e) {
			new PopupUI(e.toString(), e.getMessage());
		}
		
	}
	
	protected void doneCheckout() {
		frame.dispose();
	}

}
