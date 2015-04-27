package eecs341.finalProject;

import java.awt.Color;
import java.awt.event.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AnalyticsUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame = this;
	private JFrame parent;
	private SQLConnection db;
	private int storeID;
	
	public AnalyticsUI(JFrame parent, SQLConnection db, int storeID) {
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
		frame.setTitle("Analytics");

		JButton back = new JButton("Back");
		JButton execute = new JButton("Execute");
		back.setBounds(20, 330, 120, 40);
		execute.setBounds(160, 330, 120, 40);
		frame.add(back);
		frame.add(execute);
		
		JButton query1 = new JButton("Most Commonly Bought Together");
		JButton query2 = new JButton("Highest Spending Members");
		JButton query3 = new JButton("Most Profitable Stores");
		JButton query4 = new JButton("Number of Items From Supplier");
		JButton query5 = new JButton("All Items at this store");
		JButton query6 = new JButton("All members who shop here");
		JButton query7 = new JButton("All Prescriptions at this store");
		JButton query8 = new JButton("Currently Open Stores");
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
				ResultSet rs;
				try {
					rs = db.runQueryString("SELECT IP1.itemID, I1.name, IP2.itemID, I2.name, COUNT(*) "
							+ "FROM ItemsPurchased as IP1, Items as I1, ItemsPurchased as IP2, Items as I2 "
							+ "WHERE IP1.itemID = I1.itemID AND IP2.itemID = I2.itemID AND IP1.purchaseID = IP2.purchaseID "
							+ "AND IP1.itemID < IP2.itemID GROUP BY IP1.itemID, IP2.itemID ORDER BY COUNT(*) DESC LIMIT 10");
					new ResultUI(db, rs);
				} catch (SQLConnectionException e) {
					new PopupUI(e.toString(), e.getMessage());
				} catch (SQLException e) {
					new PopupUI(e.toString(), e.getMessage());
				}
			}
		});
		
		query2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = db.runQueryString("SELECT M.memberID, M.name, SUM(P.amountCharged) "
							+ "FROM AwardsClubMember as M, Purchase as P "
							+ "WHERE P.memberID = M.memberID "
							+ "GROUP BY M.memberID ORDER BY SUM(P.amountCharged) DESC LIMIT 10");
					new ResultUI(db, rs);
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = db.runQueryString("SELECT storeID, address, SUM(amountCharged) "
							+ "FROM Stores NATURAL JOIN PurchasedAt NATURAL JOIN Purchase "
							+ "GROUP BY storeID ORDER BY SUM(amountCharged) DESC LIMIT 10");
					new ResultUI(db, rs);
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = db.runQueryString("SELECT Supplier.name, COUNT(Items.itemID) "
							+ "FROM Items, Supplier "
							+ "WHERE Items.supplierID = Supplier.supplierID "
							+ "GROUP BY Supplier.supplierID ORDER BY Supplier.name");
					new ResultUI(db, rs);
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					PreparedStatement ps = db.getActiveConnection().prepareStatement("Select i.itemID, i.name, i.currentPrice, q.quantityStocked "
							+ "From Items as i, Stores as s, AmountStocked as q "
							+ "Where s.storeID = ? and s.storeID = q.storeID and i.itemID = q.itemID");
					ps.setInt(1, storeID);
					ResultSet rs = ps.executeQuery();
					new ResultUI(db, rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					PreparedStatement ps = db.getActiveConnection().prepareStatement("Select M.name, M.address "
							+ "From AwardsClubMember M, Purchase as P, PurchasedAt as Q	"
							+ "Where P.memberID = M.memberID and P.purchaseID = Q.purchaseID and Q.storeID = ?");
					ps.setInt(1, storeID);
					ResultSet rs = ps.executeQuery();
					new ResultUI(db, rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					PreparedStatement ps = db.getActiveConnection().prepareStatement("Select M.name, M.address "
							+ "From AwardsClubMember M, Purchase as P, PurchasedAt as Q	"
							+ "Where P.memberID = M.memberID and P.purchaseID = Q.purchaseID and Q.storeID = ?");
					ps.setInt(1, storeID);
					ResultSet rs = ps.executeQuery();
					new ResultUI(db, rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		query8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					PreparedStatement ps = db.getActiveConnection().prepareStatement("Select S.storeID, S.address "
							+ "From Stores as S, AmountStocked as q "
							+ "Where S.storeID = q.storeID and q.itemID = ? and q.quantityStocked > 0 "
							+ "and ? Between S.openTime And S.clostTime");

					new ItemSelectUI(AnalyticsUI.this, db, ps);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
