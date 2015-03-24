package ua.com.vza.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ua.com.vza.XmlSettings.ParseXmlSettings;

public class DBProcess {

	private String db_driver;
	private String db_connection;
	private String db_username;
	private String db_password;
	
	private String selectName;
	private String updateName;
	private String selectSpecifications;
	private String updateSpecifications;
	private String selectProductType;
	private String updateProductType;
	
	private Connection dbConnection;
	private Statement preparedStatementSelect;
	private PreparedStatement preparedStatementInsert;
	
	public DBProcess()
	{
		this.db_connection = "";
		this.db_driver = "";
		this.db_password = "";
		this.db_username = "";
		readConfig();
		getConnection();
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
				db_username = value.get(0);
				break;
			case "DBPassword":
				db_password = value.get(0);
				break;
			case "selectName":
				selectName = value.get(0);
				break;
			case "updateName":
				updateName = value.get(0);
				break;
			case "selectSpecifications":
				selectSpecifications = value.get(0);
				break;
			case "updateSpecifications":
				updateSpecifications = value.get(0);
				break;
			case "selectProductType":
				selectProductType = value.get(0);
				break;
			case "updateProductType":
				updateProductType = value.get(0);
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

	private  void getConnection() {
		dbConnection = null;
		loadDriver();
		try {
			dbConnection = DriverManager.getConnection(db_connection,
					db_username, db_password);
		} catch (SQLException e) {

			e.printStackTrace();
		}
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
	
	 void updateQuery(String query, ArrayList<String> updData) throws SQLException{
		getConnection();
		try{
		dbConnection.setAutoCommit(false);
		
		} catch (SQLException ex){
			dbConnection.rollback();
			ex.printStackTrace();
		} finally{
			closeConnection();
		}
		
	}
	
	public ArrayList<String> selectFromQuery(String query) throws SQLException{
		preparedStatementSelect = null;
		ArrayList<String> tmp = new ArrayList<String>();
		preparedStatementSelect = (Statement) dbConnection.createStatement();
		ResultSet set1 = preparedStatementSelect.executeQuery(query);
		while(set1.next())
		{
			tmp.add(set1.getString("name"));
		}
		closeConnection();
		return tmp;
	}
	
	public static void updateData(){
		
	}
}
