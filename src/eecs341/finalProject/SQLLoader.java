package eecs341.finalProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import jdk.nashorn.internal.objects.PrototypeObject;


public class SQLLoader {
	
	
	
	public static void main(String[] args) {
		
		String projectServer = "ec2-52-4-49-69.compute-1.amazonaws.com";
		
		String port = "3306";
		
		String account = "projectAccount";
		
		String password = "V-P5astEgU";
		
		String database = "314_project";
		
		SQLConnection conn;
		
		conn = new SQLConnection(projectServer, port, database, account, password);
		try {
			conn.initializeConnection();
		} catch (SQLConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("1 for manufacturers \n 2 for Members \n 3 for products");
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
