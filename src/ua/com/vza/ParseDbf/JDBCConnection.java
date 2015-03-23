package ua.com.vza.ParseDbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ua.com.vza.XmlSettings.ParseXmlSettings;

public class JDBCConnection {

	private String db_driver;
	private String db_connection;
	private String db_usename;
	private String db_password;
	
	private Connection dbConnection;
	private PreparedStatement preparedStatementSelect;
	private PreparedStatement preparedStatementInsert;
	
	public JDBCConnection()
	{
		readConfig();
	}

	private void readConfig() {
		String key = "";
		ArrayList<String> value = new ArrayList<String>();
		HashMap<String, ArrayList<String>> mapSettings = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("db", "name", "p"));
		for (Map.Entry<String, ArrayList<String>> entry : mapSettings
				.entrySet()) {
			key = entry.getKey();
			value = new ArrayList<String>(entry.getValue());
			switch (key) {
			case "DBDriver":
				db_driver = value.get(0);
				break;
			case "DBConnectionString":
				db_connection = value.get(0);
				break;
			case "DBUserName":
				db_usename = value.get(0);
				break;
			case "DBPassword":
				db_password = value.get(0);
				break;
			default:
				break;
			}
		}
	}

	private void loadDriver() {
		try {
			Class.forName(db_driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	}

	private Connection getConnection() {
		db_connection = null;
		loadDriver();
		try {

			dbConnection = DriverManager.getConnection(db_connection,
					db_usename, db_password);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;
	}
	
	private void closeConnection() throws SQLException{
		if (preparedStatementInsert != null) {
			preparedStatementInsert.close();
		}

		if (preparedStatementSelect != null) {
			preparedStatementSelect.close();
		}

		if (dbConnection != null) {
			dbConnection.close();
		}
	}

}
