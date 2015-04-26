package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class AnalyticsUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	
	public AnalyticsUI(JFrame parent, SQLConnection db) {
		this.parent = parent;
		this.db = db;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Analytics");

		JButton back = new JButton("Back");
		back.setBounds(10, 330, 90, 40);
		frame.add(back);
		
		JTextArea queryLabel = new JTextArea("Enter Query:");
		queryLabel.setEditable(false);
		queryLabel.setBounds(20, 20, 260, 20);
		frame.add(queryLabel);
		JTextArea query = new JTextArea();
		JScrollPane queryScroll = new JScrollPane(query);
		query.setBounds(20, 40, 260, 100);
		queryScroll.setBounds(20, 40, 260, 100);
		query.setLineWrap(true);
		frame.add(queryScroll);
		
		// Preset Queries will go here
		

		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

}
