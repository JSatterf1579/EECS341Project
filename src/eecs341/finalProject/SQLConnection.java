package eecs341.finalProject;

import java.sql.*;
import java.util.Properties;

public class SQLConnection {
	
	private Connection conn;
	
	private Properties connProps;
	
	private String connString;
	
	private boolean isInitialized;
	
	public SQLConnection(String serverName, String port, String dbName, String userName, String password) {
		connProps = new Properties();
		this.connProps.put("user", userName);
		this.connProps.put("password", password);
		this.connString = "jdbc:mysql://" + serverName + ":" + port + "/" + dbName;
		isInitialized = false;
	}
	
	public void initializeConnection() throws SQLConnectionException {
		if (!isInitialized) {
			try {
				conn = DriverManager.getConnection(connString, connProps);
				isInitialized = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new SQLConnectionException(e.getMessage());
			}
		} else {
			throw new SQLConnectionException("Attempting to reinitialize SQL connection");
		}
	}
	
	public Connection getActiveConnection() throws SQLConnectionException {
		if (isInitialized) {
			return conn;
		} else {
			throw new SQLConnectionException("Connection not yet established");
		}
	}
	
	public ResultSet runQueryString(String queryString) throws SQLConnectionException, SQLException {
		if (isInitialized) {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(queryString);
			
		} else {
			throw new SQLConnectionException("Cannot run query without establishing connection");
		}
	}
	
	public ResultSet runPreparedQuery(PreparedStatement stmt) throws SQLConnectionException, SQLException {
		if (isInitialized) {
			return stmt.executeQuery();
		} else {
			throw new SQLConnectionException("Cannont run query without establishing connection");
		}
	}

}
