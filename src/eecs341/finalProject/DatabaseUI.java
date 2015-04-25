package eecs341.finalProject;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class DatabaseUI {
	private JFrame frame;
	private SQLConnection db;
	
	public DatabaseUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				db = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.account, DBInfo.password, DBInfo.database);
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("JMJ Database");
		
		
		String[] stores = {"none", "Store 1", "Store 2", "Store 3", "Store 4"};
		
		JComboBox<String> dropDown = new JComboBox<String>(stores);
		JButton button1 = new JButton("Control Stock");
		JButton button2 = new JButton("Make Purchase");
		JButton button3 = new JButton("Make Prescription");
		JButton button4 = new JButton("Make JMJ Member");
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
	}


	public static void main(String[] args) {
		new DatabaseUI();
	}
}