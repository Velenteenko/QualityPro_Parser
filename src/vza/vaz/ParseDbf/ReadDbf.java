package vza.vaz.ParseDbf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import vza.vaz.XmlSettings.ParseXmlSettings;
import jcifs.smb.SmbFile;

public class ReadDbf {

	private String pathToBase;
	private String tmpFileName;
	private String charSetName;
	private int collumnIndex;
	//private ArrayList<String> countTags;
	private int countLines;
	
	public ReadDbf()
	{
		readConfig();
		try {
			stream2file();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.collumnIndex = -1;
		this.countLines = 0;
		this.charSetName = "UTF-8";
	}
	
	private ArrayList<String> collectionRows;
	
	private void readConfig()
	{
		String key = null;
		ArrayList<String> value = null;
        HashMap<String, ArrayList<String>> mapPath = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("field", "name", "p"));
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
			}
		}
	}
	
	private void stream2file() throws IOException
	{
		SmbFile remoteFile = new SmbFile(pathToBase);
		 OutputStream os = new FileOutputStream(tmpFileName);
		 InputStream is = remoteFile.getInputStream();
		 int bufferSize = 5096;
		 byte[] b = new byte[bufferSize];
		 int noOfBytes = 0;
		  while( (noOfBytes = is.read(b)) != -1 )
		    {
		      os.write(b, 0, noOfBytes);
		    }
		 os.close();
		 is.close();
	}

	public ArrayList<String> getRows() {
	
		DBFReader dbfreader;
		this.collectionRows = new ArrayList<String>();
		try {
			dbfreader = new DBFReader("./"+tmpFileName);
	        int j=0;
	        for (j = 0; j < dbfreader.getFieldCount(); j++) {
	          System.out.print(dbfreader.getField(j).getName()+"  ");
	        }
	        System.out.print("\n");
			int i = 0;
			HashSet<String> tmpDuplicates = new HashSet<String>();
	        for( i = 0; dbfreader.hasNextRecord(); i++)
	        {
	            Object aobj[] = dbfreader.nextRecord(Charset.forName(this.charSetName));
	            for (int k=0; k < aobj.length; k++)
	            	 tmpDuplicates.add((String) aobj[this.collumnIndex]);
	        }

//	        System.out.println("Total Count: " + i);
	        this.countLines = i;
	        this.collectionRows.addAll(tmpDuplicates);
		} catch (JDBFException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return collectionRows;
	}
	
	
	public void setRows(ArrayList<String> collectionRows) {
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
