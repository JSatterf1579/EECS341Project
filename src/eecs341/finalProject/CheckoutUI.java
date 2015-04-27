package eecs341.finalProject;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class CheckoutUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	
	private DefaultListModel<String> listModel;
	private JTextField memberField = new JTextField();
	private JFrame parent;
	private SQLConnection db;
	private ArrayList<Integer> prescriptionIDs;
	private int storeID;

	public CheckoutUI(JFrame parent, SQLConnection db, DefaultListModel<String> listModel, ArrayList<Integer> prescriptionIDs, int storeID) {
		this.parent = parent;
		this.db = db;
		this.listModel = listModel;
		this.prescriptionIDs = prescriptionIDs;
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
		JButton createMember = new JButton("Create JMJ");
		JButton back = new JButton("Back");
		JButton checkout = new JButton("Checkout");
		
		memberLabel.setBounds(200, 10, 90, 20);
		memberField.setBounds(200, 40, 90, 20);
		createMember.setBounds(200, 100, 90, 20);
		itemList.setBounds(10, 10, 180, 300);
		back.setBounds(10, 320, 90, 50);
		checkout.setBounds(200, 320, 90, 50);
		
		itemList.setFont(new Font("monospaced", Font.PLAIN, 12));
		itemList.addListSelectionListener(null);
		
		memberLabel.setEditable(false);
		
		frame.add(memberField);
		frame.add(memberLabel);
		frame.add(createMember);
		frame.add(itemList);
		frame.add(back);
		frame.add(checkout);
		frame.setSize(300, 400);
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
				int memberID;
				try {
					memberID = Integer.parseInt(memberField.getText());
					int purchaseID = db.runUpdateString("INSERT INTO Purchase (purchaseDate, amountCharged, paymentType, memberID)"
						           	                  + "VALUES ('2011-04-12T00:00:00.000', -999, 'dummy_pmt_type', " + memberID + ")");
					for (int prescriptionID : prescriptionIDs) {
						db.runUpdateString("INSERT INTO PrescriptionFilled (purchaseID, prescriptionID)"
	         	                         + "VALUES (" + purchaseID + ", " + prescriptionID + ")");
					}
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				} catch (NumberFormatException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
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
