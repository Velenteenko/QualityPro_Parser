package ua.com.vza.ParseDbf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jcifs.smb.SmbFile;
import ua.com.vza.XmlSettings.ParseXmlSettings;

public class ReadDBFDB {

	private String dbDriverClassName;
	private String dbConnectionString;
	private String dbSMBPathToBase;
	private String dbQuery;
	private String dbCollumnName;

	private int countCollectionsRows;

	private String tmpDirName;
	private String tmpFileName;
	private String dbCharSet;

	private Connection dbConnection;
	private Statement dbStatement;
	private ResultSet dbResultSet;

	private List<String> collectionRows;

	public ReadDBFDB() {
		readConfig();
		try {
			stream2file();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadDriver();
		this.countCollectionsRows = 0;
	}
	
	public ReadDBFDB(String pathToFileTableDir, String smbTargetFile){
		this();
		this.dbConnectionString = pathToFileTableDir;
		this.dbSMBPathToBase = smbTargetFile;
	}

	private void deleteTempFiles() {
		File file;
		file = new File(tmpDirName + tmpFileName);
		if (file.exists()) {
			file.delete();
		}
	}

	private void stream2file() throws IOException {
		SmbFile remoteFile = new SmbFile(dbSMBPathToBase);
		if (!new File(tmpDirName + tmpFileName).exists()) {
			File dir = new File(tmpDirName);
			dir.mkdir();
		}
		OutputStream os = new FileOutputStream(tmpDirName + tmpFileName);
		InputStream is = remoteFile.getInputStream();
		int bufferSize = 5096;
		byte[] b = new byte[bufferSize];
		int noOfBytes = 0;
		while ((noOfBytes = is.read(b)) != -1) {
			os.write(b, 0, noOfBytes);
		}
		os.close();
		is.close();
	}

	private void readConfig() {
		String key = "";
		List<String> value;

		HashMap<String, ArrayList<String>> mapSettings = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("db", "name", "p"));
		for (Map.Entry<String, ArrayList<String>> entry : mapSettings
				.entrySet()) {
			key = entry.getKey();
			value = new ArrayList<String>(entry.getValue());
			switch (key) {
			case "DBFDriverName":
				dbDriverClassName = value.get(0);
				break;
			case "DBFConnectionString":
				dbConnectionString = value.get(0);
				break;
			case "DBFCollumnName":
				dbCollumnName = value.get(0);
				break;
			case "DBFQuery":
				dbQuery = value.get(0);
				break;
			}
		}

		mapSettings = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("field", "name", "p"));

		for (Map.Entry<String, ArrayList<String>> entry : mapSettings
				.entrySet()) {
			key = entry.getKey();
			value = new ArrayList<String>(entry.getValue());
			switch (key) {
			case "basePath":
				dbSMBPathToBase = value.get(0);
				break;
			case "tempFile":
				tmpFileName = value.get(0);
				break;
			case "tempDir":
				tmpDirName = value.get(0);
				break;
			case "charSet":
				dbCharSet = value.get(0);
				break;
			}
		}

	}

	private void loadDriver() {
		try {
			Class.forName(dbDriverClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your DBF JDBC Driver?");
			e.printStackTrace();
			return;
		}
	}

	private void getConnection() {
		dbConnection = null;
		loadDriver();
		try {
			Properties properties=new Properties();
			properties.setProperty("charSet",dbCharSet);
			dbConnection = DriverManager.getConnection(dbConnectionString, properties);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private void closeConnection() throws SQLException {
		if (dbStatement != null) {
			dbStatement.close();
		}

		if (dbResultSet != null) {
			dbResultSet.close();
		}

		if (dbConnection != null) {
			dbConnection.close();
		}
	}

	public int getCountCollectionsRows() {
		if ((collectionRows != null) | (collectionRows.size() > 0)) {
			countCollectionsRows = collectionRows.size();
		}
		return countCollectionsRows;
	}

	public List<String> getCollectionRows() {
		List<String> tmpRecList = new ArrayList<String>();
		getConnection();
		try {
			dbStatement = dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(dbQuery);
			while (dbResultSet.next()) {
				tmpRecList.add(dbResultSet.getString(dbCollumnName));
			}
			deleteTempFiles();
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		collectionRows = new ArrayList<String>(tmpRecList);
		return collectionRows;
	}

	// public void setCollectionRows(List<String> collectionRows) {
	// this.collectionRows = collectionRows;
	// }

}
