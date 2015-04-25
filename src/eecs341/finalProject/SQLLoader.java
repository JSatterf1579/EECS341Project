package eecs341.finalProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdk.nashorn.internal.objects.PrototypeObject;


public class SQLLoader {
	
	public static void main(String[] args) {
		
		SQLConnection conn;
		
		conn = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.database, DBInfo.account, DBInfo.password );
		try {
			conn.initializeConnection();
		} catch (SQLConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("1 for manufacturers \n 2 for Members \n 3 for products \n 4 for Scripts"
				+ "\n 5 for Filling locations \n 6 for Amounts Stocked");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch (input) {
		case "1":
			loadDistribData(conn);
			break;
		case "2":
			loadMemData(conn);
		case "3":
			loadItemData(conn);
		case "4":
			loadPrescriptionData(conn);
		case "5":
			loadFilledAt(conn);
		case "6":
			loadStocked(conn);
		}
		
		
	}
	
	public static void loadDistribData(SQLConnection conn) {
		
		String[] name1 = {"Stark", "Snow", "Bolton", "Lannister", "Greyjoy", "Martel", "Tyrell", "Targaryen", "Baratheon"};
		
		String[] name2 = {"Imports", "Sales", "Distributors", "Wholesale", "Warehouse", "Farms", "Fields", "Pastures", "Bakery"};
		
		String[] name3 = {"ltd", "company", "corp", "corporation"};
		
		String[] addr1 = {"Pittsburgh", "Cleveland", "Cincinatti"};
		
		String phone = "1-555-555-5555";
		
		String queryBase = "Insert into Supplier \n"
				+ "values (?, ?, ?, ?);";
		
		for(int i = 1; i < 11; i++) {
			String companyName = name1[(int)(9*Math.random())] + " " + name2[(int)(9 * Math.random())] + " " + name3[(int)(4 * Math.random())];
			String addr = addr1[(int)(3 * Math.random())];
			try {
				Connection connection = conn.getActiveConnection();
				PreparedStatement state = connection.prepareStatement(queryBase);
				state.setInt(1, i);
				state.setString(2, companyName);
				state.setString(3, addr);
				state.setString(4, phone);
				state.executeUpdate();
				
			} catch (SQLConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void loadMemData(SQLConnection conn) {
		String[] name1 = {"Bob", "Jon", "Steve", "Justin", "Joe", "Jub", "Mike", "Ross", "Joey", "James"};
		String[] name2 = {"Bobson", "Evans", "Steves", "Justinson", "Josephs", "Jub", "Mike", "Ross", "Joey", "James"};
		String[] addr1 = {"Pittsburgh", "Cleveland", "Cincinatti", "Mombasa"};
		
		String phone = "1-555-555-5555";
		
		int credits = 0;
		
		String queryBase = "Insert into AwardsClubMember \n"
				+ "values (?, ?, ?, ?, ?);";
		
		for(int i = 1; i < 41; i++) {
			String pName = name1[(int)(9*Math.random())] + " " + name2[(int)(9 * Math.random())];
			String addr = addr1[(int)(3 * Math.random())];
			try {
				Connection connection = conn.getActiveConnection();
				PreparedStatement state = connection.prepareStatement(queryBase);
				state.setInt(1, i);
				state.setString(2, pName);
				state.setString(3, addr);
				state.setString(4, phone);
				state.setInt(5, credits);
				state.executeUpdate();
				
			} catch (SQLConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void loadItemData(SQLConnection conn) {
		String[] brands = {"Funco", "Phil and Watson", "Patterson", "Pacemaker", "Popsee", "Cheezer","Funser", "Pipes and Co.", "Orange", "GZ", "Doors",
				"Blah", "Brand Co", "Microsoft"};
		
		String[] prodTypes = {"Food", "Home Items", "Medicine"};
		
		String query1 = "Insert into Items \n"
				+ "values (?, ?, ?, ?);";
		
		String queryFood = "Insert into Food \n"
				+ "values (?, ?, ?)";
		
		String queryHG = "Insert into HomeItems \n"
				+ " values (?, ?)";
		
		String queryD = "Insert into Medicine \n"
				+ " values (?, ?, ?, ?)";
		
		for(int i = 2; i < 101; i++) {
			int type = (int)(3*Math.random()) + 1;
			
			int distid = (int)(10*Math.random()) + 1;
			
			
			double price = Math.round(50*Math.random() * 100.0) / 100.0;
			
			String name = getInput("Picking " + prodTypes[type - 1] + " enter name");
			
			PreparedStatement p1 = null;
			Connection conn1;
			
			try {
				conn1 = conn.getActiveConnection();
				p1 = conn1.prepareStatement(query1);
				p1.setInt(1, i);
				p1.setString(2, name);
				p1.setInt(3, distid);
				p1.setString(4, Double.toString(price));
				
				
			} catch (SQLConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(type) {
			case 1: {
				String brand = brands[(int)(brands.length * Math.random())];
				Date expry = new Date(Calendar.getInstance().getTimeInMillis() + (long)(Math.random() * 2628000000L));
				
				
				try {
					conn1 = conn.getActiveConnection();
					PreparedStatement p2 = conn1.prepareStatement(queryFood);
					p2.setInt(1, i);
					p2.setString(2, brand);
					p2.setDate(3, expry);
					p1.executeUpdate();
					p2.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Done");
				break;
			}
			case 2: {
				String brand = brands[(int)(brands.length * Math.random())];
				
				try {
					conn1 = conn.getActiveConnection();
					PreparedStatement p2 = conn1.prepareStatement(queryHG);
					p2.setInt(1, i);
					p2.setString(2, brand);
					p1.executeUpdate();
					p2.executeUpdate();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Done");
				break;
				
				
			}
			case 3: {
				String[] chem1 = {"Hydrogen", "Oxygen", "Nitrogen", "Silicon", "Rubidium", "Cesium", "Iron", "Boron", "Florine", "Magnesium", "Aluminium", "Uranium"};
				String[] chem2 = {"Trichloride", "Dichloride", "Monochloride", "Hydride", "Sulfide", "Dihydride", "Cyclohexane", "Octane", "Decane", "Ethanol", "Methylamine"};
				String cname = chem1[(int)(Math.random() * chem1.length)] + " " + chem2[(int)(Math.random() * chem2.length)];
				String manufacturer = brands[(int)(brands.length * Math.random())];
				String strength = ((int)(Math.random() * 150) + 10) + " mg";
				
				try {
					conn1 = conn.getActiveConnection();
					PreparedStatement p2 = conn1.prepareStatement(queryD);
					p2.setInt(1, i);
					p2.setString(2, cname);
					p2.setString(3, manufacturer);
					p2.setString(4, strength);
					p1.executeUpdate();
					p2.executeUpdate();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Done");
				break;
				
				
			}
			default: {
				i--;
				break;
			}
			}
		}
	}
	
	public static void loadPrescriptionData(SQLConnection conn) {
		String[] name1 = {"Bob", "Jon", "Steve", "Justin", "Joe", "Jub", "Mike", "Ross", "Joey", "James"};
		String[] name2 = {"Bobson", "Evans", "Steves", "Justinson", "Josephs", "Jub", "Mike", "Ross", "Joey", "James"};
		String unit = "mg";
		String[] frequency = {"monthly", "weekly"}; 
		String medsQuery = "Select itemID from Medicine";
		String scriptsInsert = "Insert into Prescription \n"
				+ "values (?, ?, ?, ?, ?, ?)";
		Integer[] items = getAllValues(medsQuery, "itemID", conn);
		
		for(int i = 1; i < 30; i++) {
			String pName = name1[(int)(9*Math.random())] + " " + name2[(int)(9 * Math.random())];
			String freq = frequency[(int)(2*Math.random())];
			try {
				Connection c1 = conn.getActiveConnection();
				PreparedStatement p1 = c1.prepareStatement(scriptsInsert);
				p1.setInt(1, i);
				p1.setInt(2, items[(int)(Math.random()*items.length)]);
				p1.setString(3, pName);
				p1.setInt(4, (int)(Math.random() * 30) + 10);
				p1.setString(5, ((int)(Math.random() * 30) + 80) + " mg" );
				p1.setString(6, freq);
				p1.executeUpdate();
			} catch (SQLConnectionException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void loadFilledAt(SQLConnection conn) {
		String queryStores = "Select storeID from Stores";
		String queryScripts = "Select prescriptionID from Prescription";
		String filled = "Insert into FilledAt \n"
				+ "values (?, ?)";
		
		Integer[] storeIDs = getAllValues(queryStores, "storeID", conn);
		Integer[] scriptIDs = getAllValues(queryScripts, "prescriptionID", conn);
		
		for(int i: scriptIDs){
			try {
				Connection c1 = conn.getActiveConnection();
				PreparedStatement p1 = c1.prepareStatement(filled);
				p1.setInt(1, storeIDs[(int)(Math.random()*storeIDs.length)]);
				p1.setInt(2, i);
				p1.executeUpdate();
			} catch (SQLConnectionException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void loadStocked (SQLConnection conn) {
		String queryStores = "Select storeID from Stores";
		String queryItems = "Select itemID from Items";
		String insertQuery = "Insert into AmountStocked values (?, ?, ?)";
		
		Integer[] storeIDs = getAllValues(queryStores, "storeID", conn);
		Integer[] itemIDs = getAllValues(queryItems, "itemID", conn);
		
		for(int i = 0; i < 200; i++) {
			try {
				Connection c1 = conn.getActiveConnection();
				PreparedStatement p1 = c1.prepareStatement(insertQuery);
				p1.setInt(1, itemIDs[(int)(itemIDs.length * Math.random())]);
				p1.setInt(2, itemIDs[(int)(storeIDs.length * Math.random())]);
				p1.setInt(3, 15 + (int)(35* Math.random()));
				p1.executeUpdate();
			} catch (SQLConnectionException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static Integer[] getAllValues(String query, String col, SQLConnection conn) {
		PreparedStatement p1;
		Connection c1;
		List<Integer> values = new ArrayList<>();
		try {
			c1 = conn.getActiveConnection();
			p1 = c1.prepareStatement(query);
			ResultSet set = conn.runPreparedQuery(p1);
			while(set.next()) {
				values.add(set.getInt(col));
			}
			
		} catch (SQLConnectionException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer[] retArray = new Integer[values.size()];
		return values.toArray(retArray);
	}
	
	public static String getInput(String prompt){
		System.out.println(prompt);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return input;
	}

}
