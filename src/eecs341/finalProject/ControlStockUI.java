package eecs341.finalProject;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ControlStockUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	private int storeID;

	public ControlStockUI(JFrame parent, SQLConnection db, int storeID) {
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
		frame.setTitle("Control Stock");
		JButton button1 = new JButton("Add item");
		JButton button2 = new JButton("Lookup item");
		JButton button3 = new JButton("Back");
		
		button1.setBounds(50, 75, 200, 50);
		button2.setBounds(50, 150, 200, 50);
		button3.setBounds(50, 225, 200, 50);
		
		frame.add(button1);
		frame.add(button2);
		frame.add(button3);
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddItemUI(ControlStockUI.this, db, storeID);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LookupItemUI(ControlStockUI.this, db, storeID);
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

}
