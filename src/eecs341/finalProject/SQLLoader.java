package eecs341.finalProject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		System.out.println("1 for manufacturers");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(input.equals("1")) {
			loadDistribData(conn);
		} else if(input.equals("2")) {
			loadMemData(conn);
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
				"Blah", "Coka", "Maek"};
		
		String query1 = "Insert into Items \n"
				+ "(?, ?, ?, ?);";
		
		String queryFood = "Insert into Food \n"
				+ "(?, ?, ?)";
		
		String queryHG = "Insert into HomeGoods \n"
				+ "(?, ?)";
		
		String queryD = "Insert into Medicine \n"
				+ "(?, ?, ?, ?)";
		
		for(int i = 1; i < 2; i++) {
			int type = (int)(1*Math.random()) + 1;
			
			int distid = (int)(10*Math.random()) + 1;
			
			
			String sPrice = getInput("Picking " + type + " enter price.");
			double price = Double.parseDouble(sPrice);
			
			String name = getInput("Picking " + type + " enter name");
			
			PreparedStatement p1;
			Connection conn1;
			
			try {
				conn1 = conn.getActiveConnection();
				p1 = conn1.prepareStatement(query1);
				p1.setInt(1, i);
				p1.setString(2, name);
				p1.setInt(3, (int)(10*Math.random()) + 1);
				p1.setDouble(4, price);
				
				
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
				java.util.Date d = new java.util.Date();
				Date expry = new Date(d.getDate());
				
				try {
					conn1 = conn.getActiveConnection();
					PreparedStatement p2 = conn1.prepareStatement(queryFood);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			case 2: {
				
			}
			case 3: {
				
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
