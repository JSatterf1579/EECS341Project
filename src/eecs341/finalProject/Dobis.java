package eecs341.finalProject;

import java.sql.SQLException;

public class Dobis {
	
	public static SQLConnection db = new SQLConnection(DBInfo.server, DBInfo.port, DBInfo.database, DBInfo.account, DBInfo.password);
	
	public static void main(String[] args) {
		try {
			db.initializeConnection();
			DatabaseUI dbUI = new DatabaseUI();
		} catch (SQLConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
