package eecs341.finalProject;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	protected SQLConnection db;
	
	public DatabaseUI() {
		db = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.database, DBInfo.account, DBInfo.password);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					db.initializeConnection();
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
					return;
				}
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Dobis Database");
		
		Map<String, Integer> stores;
		try {
			stores = dbQueryStoreAddresses();
		} catch (SQLConnectionException e) {
			new PopupUI(e.toString(), e.getMessage());
			frame.dispose();
			return;
		} catch (SQLException e) {
			new PopupUI(e.toString(), e.getMessage());
			frame.dispose();
			return;
		}
		
		JComboBox<String> dropDown = new JComboBox<String>(stores.keySet().toArray(new String[stores.size()]));
		JButton controlStockButton = new JButton("Control Stock");
		JButton makePurchaseButton = new JButton("Make Purchase");
		JButton makePrescriptionButton = new JButton("Make Prescription");
		JButton makeMemberButton = new JButton("Make Dobis Member");
		JButton analyticsButton = new JButton("Analytics");
		
		dropDown.setBounds(50, 5, 200, 50);
		controlStockButton.setBounds(50, 60, 200, 50);
		makePurchaseButton.setBounds(50, 115, 200, 50);
		makePrescriptionButton.setBounds(50, 170, 200, 50);
		makeMemberButton.setBounds(50, 225, 200, 50);
		analyticsButton.setBounds(50, 280, 200, 50);
		
		frame.add(dropDown);
		frame.add(controlStockButton);
		frame.add(makePurchaseButton);
		frame.add(makePrescriptionButton);
		frame.add(makeMemberButton);
		frame.add(analyticsButton);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		controlStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int storeID = stores.get(dropDown.getSelectedItem().toString());
				new ControlStockUI(DatabaseUI.this, db, storeID);
			}
		});
		makePurchaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int storeID = stores.get(dropDown.getSelectedItem().toString());
				new MakePurchaseUI(DatabaseUI.this, db, storeID);
			}
		});
		
		makePrescriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int storeID = stores.get(dropDown.getSelectedItem().toString());
				new PrescriptionUI(DatabaseUI.this, db, storeID);
			}
		});
		
		makeMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakeMemberUI(DatabaseUI.this, db);
			}
		});
		
		analyticsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AnalyticsUI(DatabaseUI.this, db);
			}
		});
	}
	
	private Map<String, Integer> dbQueryStoreAddresses() throws SQLConnectionException, SQLException {
		ResultSet rs = db.runQueryString("SELECT storeID, address FROM Stores");
		Map<String, Integer> stores = new HashMap<String, Integer>();
		while (rs.next()) {
			stores.put(rs.getString(2), (int)rs.getShort(1));
		}
		return stores;
	}
	
	private String[] resultSetCol(ResultSet rs, int col) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		while(rs.next()) {
		    String row = rs.getString(col);
		    result.add(row);
		}
		return result.toArray(new String[result.size()]);
	}

	public static void main(String[] args) {
		new DatabaseUI();
	}
}