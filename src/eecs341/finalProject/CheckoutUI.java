package eecs341.finalProject;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;

public class CheckoutUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	
	private DefaultListModel<String> listModel;
	private JTextField memberField = new JTextField();
	private JFrame parent;
	private SQLConnection db;
	private List<Integer> prescriptionIDs;
	private List<PurchaseListEntry> itemsToBuy;
	private int storeID;

	public CheckoutUI(JFrame parent, SQLConnection db, DefaultListModel<String> listModel, List<Integer> prescriptionIDs, List<PurchaseListEntry> items, int storeID) {
		this.parent = parent;
		this.db = db;
		this.listModel = listModel;
		this.prescriptionIDs = prescriptionIDs;
		this.itemsToBuy = items;
		this.storeID = storeID;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Checkout");
		JList<String> itemList = new JList<String>(listModel);
		JTextArea memberLabel = new JTextArea("Member ID: ");
		JButton createMember = new JButton("Create Dobis Member");
		JButton back = new JButton("Back");
		JButton checkout = new JButton("Checkout");
		JComboBox<String> dropDown = new JComboBox<String>(new String[]{"Credit", "Cash", "Check"});
		dropDown.setBounds(110, 320, 180, 50);
		frame.add(dropDown);
		
		memberLabel.setBounds(10, 10, 90, 20);
		memberField.setBounds(150, 10, 90, 20);
		createMember.setBounds(260, 10, 130, 20);
		itemList.setBounds(10, 40, 380, 280);
		back.setBounds(10, 320, 90, 50);
		checkout.setBounds(300, 320, 90, 50);
		
		itemList.setFont(new Font("monospaced", Font.PLAIN, 12));
		itemList.setBorder(BorderFactory.createLineBorder(Color.black));
		itemList.addListSelectionListener(null);
		
		memberLabel.setEditable(false);
		
		frame.add(memberField);
		frame.add(memberLabel);
		frame.add(createMember);
		frame.add(itemList);
		frame.add(back);
		frame.add(checkout);
		frame.setSize(400, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		createMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakeMemberUI(CheckoutUI.this, db);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String insertPurchase = "INSERT INTO Purchase (purchaseDate, amountCharged, paymentType, memberID) \n"
						+ "VALUES (?, ?, ?, ?)";
				String insertItemPurchased = "INSERT INTO ItemsPurchased \n"
						+ "VALUE (?, ?, ?)";
				String quantityQuery = "SELECT quantityStocked FROM AmountStocked WHERE itemID = ? and storeID = ?";
				String quantityReduce = "UPDATE AmountStocked SET quantityStocked = quantityStocked - ? where itemID = ? and storeID = ?";
				String quantityAdd = "UPDATE AmountStocked SET quantityStocked = quantityStocked + ? where itemID = ? and storeID = ?";
				String scriptFillCheck = "SELECT * from FilledAt where storeID = ? and prescriptionID = ?";
				String scriptFilledInsert = "INSERT INTO PrescriptionFilled VALUES (?, ?)";
				boolean transactionSuccessful = true;
				Connection con = null;
				Savepoint rollback = null;
				Integer memberID = null;
				try {
					Integer key = null;
					try{
						memberID = Integer.parseInt(memberField.getText());
					} catch (NumberFormatException e1) {
						memberID = null;
					}
					con = db.getActiveConnection();
					double price = 0d;
					for(PurchaseListEntry p : itemsToBuy) {
						price += p.price;
					}
					PreparedStatement scriptCheckStmt = con.prepareStatement(scriptFillCheck);
					for(Integer i: prescriptionIDs) {
						scriptCheckStmt.setInt(1, storeID);
						scriptCheckStmt.setInt(2, i);
						ResultSet rs = scriptCheckStmt.executeQuery();
						if(rs != null && rs.next()) {
							continue;
						} else {
							new PopupUI("Purchase failed", "Prescription " + i + " not available at this store");
							return;
						}
					}
					int stockQuantity = 0;
					PreparedStatement stockAmountStmt = con.prepareStatement(quantityQuery);
					PreparedStatement stockLowerStmt = con.prepareStatement(quantityReduce);
					PreparedStatement stockRaiseStmt = con.prepareStatement(quantityAdd);
					for(PurchaseListEntry p : itemsToBuy) {
						stockQuantity = 0;
						stockAmountStmt.setInt(1, p.itemID);
						stockAmountStmt.setInt(2, storeID);
						ResultSet quantity = stockAmountStmt.executeQuery();
						if(quantity != null && quantity.next()) {
							stockQuantity = quantity.getInt(1);
						} else {
							new PopupUI("Purchase Failed", "Store does not stock " + p.name);
							return;
						}
						if(stockQuantity >= p.quantityBought){
							if(stockQuantity - p.quantityBought < 5) {
								stockRaiseStmt.setInt(1, 20);
								stockRaiseStmt.setInt(2, p.itemID);
								stockRaiseStmt.setInt(3, storeID);
								stockRaiseStmt.executeUpdate();
							}
						} else {
							new PopupUI("Purchase Failed", "Store only has " + stockQuantity + " of " + p.name);
							return;
						}
					}
					
					con.setAutoCommit(false);
					rollback = con.setSavepoint();
					PreparedStatement purchaseInsertStmt = con.prepareStatement(insertPurchase, Statement.RETURN_GENERATED_KEYS);
					purchaseInsertStmt.setDate(1, new Date(Calendar.getInstance().getTimeInMillis()));
					purchaseInsertStmt.setString(2, Double.toString(price));
					purchaseInsertStmt.setString(3, "Undetermined");
					if(memberID != null){
						purchaseInsertStmt.setInt(4, memberID);
					} else {
						purchaseInsertStmt.setNull(4, java.sql.Types.INTEGER);
					}
					purchaseInsertStmt.executeUpdate();
					ResultSet keySet = purchaseInsertStmt.getGeneratedKeys();
					if(keySet != null && keySet.next()){
						key = keySet.getInt(1);
					} else {
						transactionSuccessful = false;
					}
					
					
					PreparedStatement insertItemStmt = con.prepareStatement(insertItemPurchased);
					for(PurchaseListEntry p : itemsToBuy) {
						insertItemStmt.setInt(1, key);
						insertItemStmt.setInt(2, p.itemID);
						insertItemStmt.setInt(3, p.quantityBought);
						insertItemStmt.executeUpdate();
						stockLowerStmt.setInt(1, p.quantityBought);
						stockLowerStmt.setInt(2, p.itemID);
						stockLowerStmt.setInt(3, storeID);
						stockLowerStmt.executeUpdate();
					}
					PreparedStatement insertScriptFill = con.prepareStatement(scriptFilledInsert);
					for (int prescriptionID : prescriptionIDs) {
						insertScriptFill.setInt(1, key);
						insertScriptFill.setInt(2, prescriptionID);
						insertScriptFill.executeQuery();
					}
					if(transactionSuccessful) {
						con.commit();
					} else {
						con.rollback(rollback);
						new PopupUI("Checkout Failed", "Checkout could not be successfully completed. Please try again.");
					}
				} catch (SQLConnectionException e) {
					try {
						con.rollback(rollback);
					} catch (SQLException e1) {
						new PopupUI(e1.toString(), e1.getMessage());
						e1.printStackTrace();
					}
					new PopupUI(e.toString(), e.getMessage());
					return;
				} catch (SQLException e) {
					try {
						con.rollback(rollback);
					} catch (SQLException e1) {
						new PopupUI(e1.toString(), e1.getMessage());
						e1.printStackTrace();
					}
					new PopupUI(e.toString(), e.getMessage());
					return;
				}  finally {
					try {
						con.setAutoCommit(true);
					} catch (SQLException e) {
						new PopupUI(e.toString(), e.getMessage());
						return;
					}
				}
				frame.dispose();
				((MakePurchaseUI)parent).callbackDoneCheckout();
			}
		});
	}

	public void callbackSetMemberID(int memberID) {
		memberField.setText(Integer.toString(memberID));
	}

}

// TODO STORE
