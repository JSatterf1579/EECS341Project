package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class LookupItemUI {
	private JFrame frame;
	
	public LookupItemUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}

	private void launchDisplay() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Lookup Item");
		
		JTextArea itemIDLabel = new JTextArea("Item ID:");
		JTextField itemID = new JTextField();
		JTextArea detailsList = new JTextArea();
		JButton search = new JButton("Search");
		JButton back = new JButton("Back");
		
		itemIDLabel.setBounds(20, 20, 80, 20);
		itemID.setBounds(110, 20, 80, 20);
		detailsList.setBounds(20, 60, 260, 250);
		search.setBounds(200, 20, 80, 20);
		back.setBounds(10, 330, 90, 40);
		
		detailsList.setEditable(false);
		detailsList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		itemIDLabel.setEditable(false);
		
		frame.add(itemIDLabel);
		frame.add(itemID);
		frame.add(detailsList);
		frame.add(search);
		frame.add(back);
		
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: SQL FILL IN DATA FROM QUERY INTO detailsList
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ControlStockUI();
			}
		});
	}
	
	public static void main(String[] args) {
		new LookupItemUI();
	}

}
