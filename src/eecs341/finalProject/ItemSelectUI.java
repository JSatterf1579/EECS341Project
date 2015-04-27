package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.*;

public class ItemSelectUI extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	private PreparedStatement ps;
	private int itemID;
	
	
	public ItemSelectUI(JFrame parent, SQLConnection db, PreparedStatement ps) {
		this.parent = parent;
		this.db = db;
		this.ps = ps;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}

	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Item Select");
		
		JTextField itemID = new JTextField();
		JTextArea itemIDLabel = new JTextArea("Select Item ID");
		itemIDLabel.setBounds(55, 10, 90, 30);
		itemID.setBounds(55, 50, 90, 30);
		
		itemIDLabel.setEditable(false);
		
		JButton back = new JButton("Back");
		JButton execute = new JButton("Execute");
		back.setBounds(55, 230, 90, 40);
		execute.setBounds(55, 100, 90, 40);
		frame.add(back);
		frame.add(execute);
		frame.add(itemIDLabel);
		frame.add(itemID);

		frame.setSize(200, 300);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ps.setInt(1, Integer.parseInt(itemID.getText()));
					ps.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
					ResultSet rs = ps.executeQuery();
					new ResultUI(db, rs);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	
}
