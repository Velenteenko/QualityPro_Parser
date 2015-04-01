package ua.com.vza.velenteenko.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import ua.com.vza.velenteenko.ParseDbf.Parse;
import ua.com.vza.velenteenko.XmlSettings.ParseXmlSettings;

public class DBParseProcess {

	private String db_driver;
	private String db_connection;
	private String db_username;
	private String db_password;
	private String db_charSet;

	private String selectName;
	private String updateName;
	private String selectSpGOST;
	private String updateSpGOST;
	private String selectSpOST;
	private String updateSpOST;
	private String selectSpTU;
	private String updateSpTU;
	private String selectSpDSTU;
	private String updateSpDSTU;
	private String selectProductType;
	private String updateProductType;
	private String selectCountRecord;
	private String updateCountRecord;

	private List<String> fieldsRecords;

	private Connection dbConnection;
	private Statement StatementSelect;
	private PreparedStatement preparedStatementInsert;
	private PreparedStatement preparedStatementUpdate;

	private Parse fromParser;

	public DBParseProcess() {
		this.db_connection = "";
		this.db_driver = "";
		this.db_password = "";
		this.db_username = "";
		readConfig();
		if (dbConnection != null) {
			try {
				closeConnections();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loadDriver();
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
			case "charSetEncoding":
				db_charSet = value.get(0);
				break;
			case "selectName":
				selectName = value.get(0);
				break;
			case "insertName":
				updateName = value.get(0);
				break;
			case "selectSpGOST":
				selectSpGOST = value.get(0);
				break;
			case "insertSpGOST":
				updateSpGOST = value.get(0);
				break;
			case "selectSpOST":
				selectSpOST = value.get(0);
				break;
			case "insertSpOST":
				updateSpOST = value.get(0);
				break;
			case "selectSpTU":
				selectSpTU = value.get(0);
				break;
			case "insertSpTU":
				updateSpTU = value.get(0);
				break;
			case "selectSpDSTU":
				selectSpDSTU = value.get(0);
				break;
			case "insertSpDSTU":
				updateSpDSTU = value.get(0);
				break;
			case "selectProductType":
				selectProductType = value.get(0);
				break;
			case "insertProductType":
				updateProductType = value.get(0);
				break;
			case "selectCountRecord":
				selectCountRecord = value.get(0);
				break;
			case "updateCountRecord":
				updateCountRecord = value.get(0);
				break;
			case "fieldsRecords":
				fieldsRecords = new ArrayList<String>(value);
				break;
			}
		}
	}

	private void loadDriver() {
		try {
			Class.forName(db_driver);
		} catch (ClassNotFoundException e) {
			// System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	}

	private void getConnection() {
		dbConnection = null;
		try {
			Properties prop = new Properties();
			prop.setProperty("characterEncoding", db_charSet);
			prop.setProperty("user", db_username);
			prop.setProperty("password", db_password);
			dbConnection = DriverManager.getConnection(db_connection, prop);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private void closeConnections() throws SQLException {
		if (preparedStatementInsert != null) {
			preparedStatementInsert.close();
		}

		if (StatementSelect != null) {
			StatementSelect.close();
		}

		if (preparedStatementUpdate != null) {
			preparedStatementUpdate.close();
		}

		if (dbConnection != null) {
			dbConnection.close();
		}
	}

	private void updateValueOfCountRows(String query, Integer[] paramValues)
			throws SQLException {
		// closeConnection();
		// getConnection();
		try {
			dbConnection.setAutoCommit(false);
			preparedStatementUpdate = dbConnection.prepareStatement(query);
			for (int i = 1; i <= paramValues.length; i++) {
				preparedStatementUpdate.setInt(i, paramValues[i - 1]);
			}
			preparedStatementUpdate.executeUpdate();
			dbConnection.commit();

		} catch (SQLException ex) {
			dbConnection.rollback();
			ex.printStackTrace();
		}
		// finally {
		// closeConnection();
		// }
	}

	private void insertValues(String query, Set<String> updData)
			throws SQLException {
		// closeConnection();
		// getConnection();
		try {
			for (String ins : updData) {
				dbConnection.setAutoCommit(false);
				preparedStatementInsert = dbConnection.prepareStatement(query);
				preparedStatementInsert.setString(1, ins);
				preparedStatementInsert.executeUpdate();
				dbConnection.commit();
			}
		} catch (SQLException e) {
			dbConnection.rollback();
			e.printStackTrace();
		}

	}

	private List<String> selectValues(String query, String typeFields,
			String[] namesOfFileds) throws SQLException {
		// closeConnection();
		// getConnection();

		StatementSelect = null;
		ArrayList<String> tmp = new ArrayList<String>();
		try {
			dbConnection.setAutoCommit(false);
			StatementSelect = (Statement) dbConnection.createStatement();
			ResultSet set1 = StatementSelect.executeQuery(query);
			while (set1.next()) {
				switch (typeFields) {
				case "String":
					if (namesOfFileds.length > 1) {
						for (int i = 0; i < namesOfFileds.length; i++) {
							tmp.add(set1.getString(namesOfFileds[i]));
						}
					} else {

						tmp.add(set1.getString(namesOfFileds[0]));
					}
					break;

				case "Integer":
					if (namesOfFileds.length > 1) {
						for (int i = 0; i < namesOfFileds.length; i++) {
							tmp.add(String.valueOf(set1
									.getInt(namesOfFileds[i])));
						}
					} else {

						tmp.add(String.valueOf(set1.getString(namesOfFileds[0])));
					}
					break;
				}
			}

			dbConnection.commit();

		} catch (SQLException e) {
			dbConnection.rollback();
			e.printStackTrace();
			return new ArrayList<String>();
		}
		// closeConnection();
		return tmp;
	}

	private Set<String> findNewRecords(Set<String> parseList, Set<String> dbList) {
		final Set<String> setToReturn = new HashSet<String>();
		Iterator<String> itr = parseList.iterator();
		while (itr.hasNext()) {
			String rec = itr.next();
			if (dbList.add(rec)) {
				setToReturn.add(rec);
			}
		}
		return setToReturn;
	}

	// private void checkTable(int countRows, Set<String> dataFromParse){
	//
	// }

	public void updateData() throws SQLException {
		closeConnections();
		getConnection();
		List<String> dbFieldRecordsValues = new ArrayList<String>(selectValues(
				selectCountRecord, "Integer",
				fieldsRecords.toArray(new String[fieldsRecords.size()])));
		// this is a fix array size
		Integer[] paramsUpdateCounter = new Integer[] {
				Integer.valueOf(dbFieldRecordsValues.get(0)),//name
				Integer.valueOf(dbFieldRecordsValues.get(1)),//mark
				Integer.valueOf(dbFieldRecordsValues.get(2)),//gost
				Integer.valueOf(dbFieldRecordsValues.get(3)),//ost
				Integer.valueOf(dbFieldRecordsValues.get(4)),//tu
				Integer.valueOf(dbFieldRecordsValues.get(5)) };//dstu
		////////////////////////////

		fromParser = new Parse(false);
		// Name
		Set<String> fromParse = new HashSet<String>(fromParser.getNames());
		if (fromParse.size() > paramsUpdateCounter[0]) {
			Set<String> fromDB = new HashSet<String>(selectValues(selectName,
					"String", new String[] { "name" }));
			insertValues(updateName, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[0] = fromParse.size();
			// cNewName = fromParse.size();
		}

		// Mark
		fromParse = new HashSet<String>(fromParser.getMark());
		if (fromParse.size() > paramsUpdateCounter[1]) {
			Set<String> fromDB = new HashSet<String>(selectValues(
					selectProductType, "String", new String[] { "name" }));
			insertValues(updateProductType, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[1] = fromDB.size();
		}

		// GOST
		fromParse = new HashSet<String>(fromParser.getGosts());
		if (fromParse.size() > paramsUpdateCounter[2]) {
			Set<String> fromDB = new HashSet<String>(selectValues(selectSpGOST,
					"String", new String[] { "gost" }));
			insertValues(updateSpGOST, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[2] = fromDB.size();
		}

		// OST
		fromParse = new HashSet<String>(fromParser.getOst());
		if (fromParse.size() > paramsUpdateCounter[3]) {
			Set<String> fromDB = new HashSet<String>(selectValues(selectSpOST,
					"String", new String[] { "ost" }));
			insertValues(updateSpOST, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[3] = fromDB.size();
		}

		// TU
		fromParse = new HashSet<String>(fromParser.getTu());
		if (fromParse.size() > paramsUpdateCounter[4]) {
			Set<String> fromDB = new HashSet<String>(selectValues(selectSpTU,
					"String", new String[] { "tu" }));
			insertValues(updateSpTU, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[4] = fromDB.size();
		}

		// DSTU
		fromParse = new HashSet<String>(fromParser.getDstu());
		if (fromParse.size() > paramsUpdateCounter[5]) {
			Set<String> fromDB = new HashSet<String>(selectValues(selectSpDSTU,
					"String", new String[] { "dstu" }));
			insertValues(updateSpDSTU, findNewRecords(fromParse, fromDB));
			paramsUpdateCounter[5] = fromDB.size();
		}
		updateValueOfCountRows(updateCountRecord, paramsUpdateCounter);
		closeConnections();
	}
}
