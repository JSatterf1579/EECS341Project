package eecs341.finalProject;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ResultUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private SQLConnection db;
	private ResultSet rs;
	
	public ResultUI(SQLConnection db, ResultSet rs) {
		this.db = db;
		this.rs = rs;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchDisplay();
			}
		});
	}
	
	private void launchDisplay() {
		frame.getContentPane().setLayout(null);
		frame.setTitle("Query Result");
		
		JTable resultTable = new JTable();
		resultTable.setBounds(10,10,480,350);
		//frame.add(resultTable);
		resultTable.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane resultScroll = new JScrollPane(resultTable);
		resultScroll.setBounds(10,10,480,350);
		frame.add(resultScroll);
		
		try {
			resultTable.setModel(buildTableModel(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.setSize(500, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
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
