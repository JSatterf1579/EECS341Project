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
		back.setBounds(20, 300, 120, 40);
		execute.setBounds(160, 300, 120, 40);
		frame.add(back);
		frame.add(execute);
		
		JTable resultTable = new JTable();
		resultTable.setBounds(20,160,260,120);
		frame.add(resultTable);
		
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
		
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ResultSet rs;
				try {
					rs = db.runQueryString(query.getText());
					resultTable.setModel(buildTableModel(rs));
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
				}
			}
		});
	}
	
	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
	    ResultSetMetaData metaData = rs.getMetaData();
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}

}
