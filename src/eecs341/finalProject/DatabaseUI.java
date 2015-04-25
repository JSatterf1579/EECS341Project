package eecs341.finalProject;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUI {
	private JFrame frame;
	private SQLConnection db;
	
	private String[] resultSetCol(ResultSet rs, int col) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		while(rs.next()) {
		    String row = rs.getString(col);
		    result.add(row);
		}
		return result.toArray(new String[result.size()]);
	}
	
	public DatabaseUI() {
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
	
	private void launchDisplay() throws SQLConnectionException, SQLException {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("DOBIS Database");
		
		ResultSet rs = db.runQueryString("SELECT address FROM Stores");
		String[] stores = resultSetCol(rs, 1);
		
		JComboBox<String> dropDown = new JComboBox<String>(stores);
		JButton button1 = new JButton("Control Stock");
		JButton button2 = new JButton("Make Purchase");
		JButton button3 = new JButton("Make Prescription");
		JButton button4 = new JButton("Make Dobis Member");
		JButton button5 = new JButton("Analytics");
		
		dropDown.setBounds(50, 5, 200, 50);
		button1.setBounds(50, 60, 200, 50);
		button2.setBounds(50, 115, 200, 50);
		button3.setBounds(50, 170, 200, 50);
		button4.setBounds(50, 225, 200, 50);
		button5.setBounds(50, 280, 200, 50);
		
		frame.add(dropDown);
		frame.add(button1);
		frame.add(button2);
		frame.add(button3);
		frame.add(button4);
		frame.add(button5);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ControlStockUI();
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MakePurchaseUI();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//new PrescriptionUI(); TODO
			}
		});
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MakeMemberUI();
			}
		});
	}


	public static void main(String[] args) {
		new DatabaseUI();
	}
}