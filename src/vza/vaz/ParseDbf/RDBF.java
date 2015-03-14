package vza.vaz.ParseDbf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;

import vza.vaz.XmlSettings.ParseXmlSettings;
import jcifs.smb.SmbFile;

public class RDBF {

	private String pathToBase;
	private String tmpFileName;
	private String tmpDirName;
	private String charSetName;
	private int collumnIndex;
	// private ArrayList<String> countTags;
	private int countLines;
	
	private ArrayList<String> collectionRows;

	public RDBF() {
		readConfig();
		try {
			stream2file();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.countLines = 0;
	}

	public RDBF(String pahToBase) {
		readConfig();
		this.pathToBase = pahToBase;
		this.countLines = 0;

	}
	
	public RDBF(String pahToBase, int collumnIndex)
	{
		this(pahToBase);
		this.collumnIndex = collumnIndex;
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
				this.charSetName = value.get(0);
				break;
			case "columnIndex":
				this.collumnIndex = Integer.parseInt(value.get(0));
				break;
			case "tempDir":
				this.tmpDirName = value.get(0);
				break;
			}
		}
	}

	private void stream2file() throws IOException {
		SmbFile remoteFile = new SmbFile(pathToBase);
		OutputStream os = new FileOutputStream(tmpFileName);
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

	public ArrayList<String> getCollectionRows() {

		HashSet<String> tmpDuplicates = new HashSet<String>();
		countLines = 0;
		try (DbfReader read = new DbfReader(new File(tmpDirName + tmpFileName))) {
			Object[] row;
			while ((row = read.nextRecord()) != null) {
				countLines++;
				String name = new String(
						DbfUtils.trimLeftSpaces((byte[]) row[collumnIndex]),
						Charset.forName(charSetName));
				tmpDuplicates.add(name);
			}
		}
		collectionRows = new ArrayList<String>(tmpDuplicates);
		return collectionRows;
	}

	public void setCollectionRows(ArrayList<String> collectionRows) {
		this.collectionRows = collectionRows;
	}

	public int getCountLines() {
		return countLines;
	}

	public int getCollumnIndex() {
		return collumnIndex;
	}

	public void setCollumnIndex(int collumnIndex) {
		this.collumnIndex = collumnIndex;
	}

}
