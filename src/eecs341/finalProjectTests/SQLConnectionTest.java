package eecs341.finalProjectTests;

import static org.junit.Assert.*;

import org.junit.Test;

import eecs341.finalProject.*;

public class SQLConnectionTest {
	
	String projectServer = "ec2-52-4-49-69.compute-1.amazonaws.com";
	
	String port = "3306";
	
	String account = "projectAccount";
	
	String password = "V-P5astEgU";
	
	String database = "314_project";
	
	SQLConnection conn;
	

	public SQLConnection firstRun(){
		return new SQLConnection(projectServer, port, database, account, password);
	}

	@Test
	public void testInitializeConnection() {
		try {
			conn = firstRun();
			conn.initializeConnection();
		} catch (SQLConnectionException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetActiveConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testRunQueryString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRunPreparedQuery() {
		fail("Not yet implemented");
	}

}
