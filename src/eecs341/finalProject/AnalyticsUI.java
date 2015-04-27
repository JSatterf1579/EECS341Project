package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
		JButton execute = new JButton("Execute");
		back.setBounds(20, 330, 120, 40);
		execute.setBounds(160, 330, 120, 40);
		frame.add(back);
		frame.add(execute);
		
		JButton query1 = new JButton("Query 1");
		JButton query2 = new JButton("Query 2");
		JButton query3 = new JButton("Query 3");
		JButton query4 = new JButton("Query 4");
		JButton query5 = new JButton("Query 5");
		JButton query6 = new JButton("Query 6");
		JButton query7 = new JButton("Query 7");
		JButton query8 = new JButton("Query 8");
		query1.setBounds(300, 20, 380, 30);
		frame.add(query1);
		query2.setBounds(300, 60, 380, 30);
		frame.add(query2);
		query3.setBounds(300, 100, 380, 30);
		frame.add(query3);
		query4.setBounds(300, 140, 380, 30);
		frame.add(query4);
		query5.setBounds(300, 180, 380, 30);
		frame.add(query5);
		query6.setBounds(300, 220, 380, 30);
		frame.add(query6);
		query7.setBounds(300, 260, 380, 30);
		frame.add(query7);
		query8.setBounds(300, 300, 380, 30);
		frame.add(query8);
		
		JTextArea queryLabel = new JTextArea("Enter Query:");
		queryLabel.setEditable(false);
		queryLabel.setBounds(20, 20, 260, 20);
		frame.add(queryLabel);
		JTextArea query = new JTextArea();
		JScrollPane queryScroll = new JScrollPane(query);
		query.setBounds(20, 40, 260, 270);
		queryScroll.setBounds(20, 40, 260, 270);
		query.setLineWrap(true);
		frame.add(queryScroll);
		frame.setSize(700, 400);
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
			public void actionPerformed(ActionEvent event) {
				ResultSet rs;
				try {
					rs = db.runQueryString(query.getText());
					new ResultUI(db, rs);
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
				}
			}
		});
		
		query1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
		
		query8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// TODO: SQL RUN QUERY
			}
		});
	}

}
