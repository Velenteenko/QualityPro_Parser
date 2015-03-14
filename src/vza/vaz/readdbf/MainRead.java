package vza.vaz.readdbf;

//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.hexiong.jdbf.DBFReader;
import com.hexiong.jdbf.JDBFException;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import vza.vaz.ParseDbf.RDBF;
import vza.vaz.XmlSettings.ParseXmlSettings;

public class MainRead {

	private static String stream2file(String path) throws IOException {
		SmbFile remoteFile = new SmbFile(path);
		OutputStream os = new FileOutputStream("stream2file.tmp");
		InputStream is = remoteFile.getInputStream();
		int bufferSize = 5096;
		byte[] b = new byte[bufferSize];
		int noOfBytes = 0;
		while ((noOfBytes = is.read(b)) != -1) {
			os.write(b, 0, noOfBytes);
		}
		os.close();
		is.close();
		return "stream2file.tmp";
	}

	public static void main(String[] args) throws IllegalArgumentException,
			IOException {

		RDBF dbf = new RDBF();

		String db_path = "/home/velenteenko/WorkDir/m1.DBF";
		HashMap<String, ArrayList<String>> mapPath = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("field", "name", "p"));
		for (Map.Entry<String, ArrayList<String>> entryset : mapPath.entrySet()) {
			String value = entryset.getValue().get(0);
			if (entryset.getKey().equals("basePath")) {
				db_path = value;
			}
		}

		DBFReader dbfreader;
		try {
			dbfreader = new DBFReader("stream2file.DBF");
			// DBFReader dbfreader = new
			// DBFReader("E:\\hexiongshare\\test.dbf");
			int j = 0;
			for (j = 0; j < dbfreader.getFieldCount(); j++) {
				System.out.print(dbfreader.getField(j).getName() + "  ");
			}
			System.out.print("\n");
			for (j = 0; dbfreader.hasNextRecord(); j++) {
				Object aobj[] = dbfreader.nextRecord(Charset.forName("CP1251"));
				// for (int k=0; k < aobj.length; k++)
				System.out.print(aobj[2] + "  |  ");
				System.out.print("\n");
			}

			System.out.println("Total Count: " + j);
		} catch (JDBFException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
