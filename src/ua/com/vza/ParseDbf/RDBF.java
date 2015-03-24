package ua.com.vza.ParseDbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.opencsv.CSVReader;

import ua.com.vza.XmlSettings.ParseXmlSettings;
import jcifs.smb.SmbFile;

public class RDBF {

	private String pathToBase;
	private String tmpFileName;
	private String tmpDirName;
	private String tmpConvertFileName;
	private String charSet;
	private int collumnIndex;
	private int countSortedLines;
	private int countNoSortedLines;

	private ArrayList<String> collectionRows;
	private CSVReader reader;

	public RDBF() {
		readConfig();
		try {
			stream2file();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.countNoSortedLines = 0;
		this.countNoSortedLines = 0;
	}

	public RDBF(String pahToBase) {
		readConfig();
		this.pathToBase = pahToBase;
		this.countSortedLines = 0;
		this.countNoSortedLines = 0;

	}

	public RDBF(String pahToBase, int collumnIndex) {
		this(pahToBase);
		this.collumnIndex = collumnIndex;
	}

	private void deleteTempFiles() {
		File file;
		file = new File(tmpDirName + tmpFileName);
		if (file.exists()) {
			file.delete();
		}
		file = new File(tmpDirName + tmpConvertFileName);
		if (file.exists()) {
			file.delete();
		}
	}

	private void stream2file() throws IOException {
		SmbFile remoteFile = new SmbFile(pathToBase);
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
			CommandExecutorLinux.execute("dbf_dump --fs=\",\" " + tmpDirName
					+ tmpFileName + " > " + tmpDirName + tmpConvertFileName);
//		}
	}

	private void readConfig() {
		String key = null;
		ArrayList<String> value = null;
		HashMap<String, ArrayList<String>> mapPath = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("field", "name", "p"));
		for (Map.Entry<String, ArrayList<String>> entryset : mapPath.entrySet()) {
			key = entryset.getKey();
			value = new ArrayList<String>(entryset.getValue());
			switch (key) {
			case "basePath":
				this.pathToBase = value.get(0);
				break;
			case "tempFile":
				this.tmpFileName = value.get(0);
				break;
			case "charset":
				this.charSet = value.get(0);
				break;
			case "columnIndex":
				this.collumnIndex = Integer.parseInt(value.get(0));
				break;
			case "tempDir":
				this.tmpDirName = value.get(0);
				break;
			case "tempConvertFile":
				this.tmpConvertFileName = value.get(0);
				break;
			}
		}
	}


	public ArrayList<String> getCollectionRows()
			throws IOException {

		HashSet<String> tmpDuplicates = new HashSet<String>();
		// countLines = 0;
		char quotechar = '\'';
		char separator = ',';
		int startLine = 0;
		countNoSortedLines = 0;
		reader = new CSVReader(new InputStreamReader(new FileInputStream(
				tmpDirName + tmpConvertFileName), charSet), separator,
				quotechar, startLine);
		String[] stringOfData;
		while ((stringOfData = reader.readNext()) != null) {
			countNoSortedLines++;
			String name = stringOfData[collumnIndex];
			tmpDuplicates.add(name);
		}
		deleteTempFiles();
		collectionRows = new ArrayList<String>(tmpDuplicates);
			Collections.sort(collectionRows);
		return collectionRows;
	}

	public void setCollectionRows(ArrayList<String> collectionRows) {
		this.collectionRows = collectionRows;
	}

	public int getCountSortedLines() {
		if (collectionRows.size() > 0) {
			countSortedLines = collectionRows.size();
		}
		return countSortedLines;
	}

	public int getCollumnIndex() {
		return collumnIndex;
	}

	public void setCollumnIndex(int collumnIndex) {
		this.collumnIndex = collumnIndex;
	}

	public int getCountNoSortedLines() {
		return countNoSortedLines;
	}

}
