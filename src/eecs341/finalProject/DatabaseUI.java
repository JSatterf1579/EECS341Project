package eecs341.finalProject;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUI {
	private JFrame frame;
	protected SQLConnection db;
	
	public DatabaseUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				db = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.database, DBInfo.account, DBInfo.password);
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
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Dobis Database");
		
		String[] stores;
		try {
			stores = getStoreAddresses();
		} catch (SQLConnectionException e) {
			new PopupUI(e.toString(), e.getMessage());
			frame.dispose();
			return;
		} catch (SQLException e) {
			new PopupUI(e.toString(), e.getMessage());
			frame.dispose();
			return;
		}
		
		JComboBox<String> dropDown = new JComboBox<String>(stores);
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
				new ControlStockUI();
			}
		});
		makePurchaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakePurchaseUI(DatabaseUI.this);
			}
		});
		
		makePrescriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PrescriptionUI(db);
			}
		});
		
		makeMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakeMemberUI();
			}
		});
		
		analyticsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AnalyticsUI();
			}
		});
	}
	
	private String[] getStoreAddresses() throws SQLConnectionException, SQLException {
		ResultSet rs = db.runQueryString("SELECT address FROM Stores");
		String[] addresses = resultSetCol(rs, 1);
		return addresses;
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