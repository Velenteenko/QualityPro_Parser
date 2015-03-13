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
import vza.vaz.ParseDbf.ReadDbf;
import vza.vaz.XmlSettings.ParseXmlSettings;
//import nl.knaw.dans.common.dbflib.CorruptedTableException;
////import nl.knaw.dans.common.dbflib.Field;
//import nl.knaw.dans.common.dbflib.IfNonExistent;
//import nl.knaw.dans.common.dbflib.Record;
//import nl.knaw.dans.common.dbflib.Table;

public class MainRead {

	private static String stream2file(String path) throws IOException {
		 SmbFile remoteFile = new SmbFile(path);
		 OutputStream os = new FileOutputStream("stream2file.tmp");
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
		 return "stream2file.tmp";
	}
	
	
    public static void main(String[] args) throws IllegalArgumentException, IOException {
    	
    	ReadDbf dbf = new ReadDbf();
    	
        String db_path = "/home/velenteenko/WorkDir/m1.DBF";
        HashMap<String, ArrayList<String>> mapPath = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("field", "name", "p"));
        for (Map.Entry<String, ArrayList<String>> entryset : mapPath.entrySet()) {
			String value = entryset.getValue().get(0);
			if(entryset.getKey().equals("basePath")){
			db_path = value;
			}
		}
       
        DBFReader dbfreader;
		try {
			dbfreader = new DBFReader("stream2file.DBF");
			 //DBFReader dbfreader = new DBFReader("E:\\hexiongshare\\test.dbf");
	        int j=0;
	        for (j = 0; j < dbfreader.getFieldCount(); j++) {
	          System.out.print(dbfreader.getField(j).getName()+"  ");
	        }
	        System.out.print("\n");
	        for(j = 0; dbfreader.hasNextRecord(); j++)
	        {
	            Object aobj[] = dbfreader.nextRecord(Charset.forName("CP1251"));
//	            for (int k=0; k < aobj.length; k++)
	              System.out.print(aobj[2]+"  |  ");
	            System.out.print("\n");
	        }

	        System.out.println("Total Count: " + j);
		} catch (JDBFException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      

        
//        File tableFile = null;
//        try {
//			SmbFile file = new SmbFile(db_path);
//			
//			tableFile = stream2file(file);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
        
        
//        Table table = new Table(new File(stream2file(db_path)),"CP1251");
////        Table table = new Table(new File("stream2file.DBF"), "CP1251");
//        try {
//            table.open(IfNonExistent.ERROR);
//            List<Field> fields = table.getFields();
//            for (Field field : fields)
//            {
//                System.out.println("Name:         " + field.getName());
//                System.out.println("Type:         " + field.getType());
//                System.out.println("Length:       " + field.getLength());
//                System.out.println("DecimalCount: " + field.getDecimalCount());
//                System.out.println();
//            }

//        Iterator<Record> iterator = table.recordIterator();
//
//         List<String> old = new ArrayList<String>();
//           // old = new LinkedList<String>();
//         Set<String> newset = new HashSet<String>();
//
//            while (iterator.hasNext())
//            {
//                Record record = iterator.next();
//               String ch = record.getStringValue("MN");
//                if (ch != null) {
//                    // System.out.println(ch);
//                    // System.out.println("\n");
//                    old.add(ch);
//                    newset.add(ch);
//                }
//            }
//            String st = old.get(2);
//            int len = st.length();
//
//            System.out.println("Duplicates:");
//
//            System.out.println("Array: "+old.size()+" Len: "+len);
//            System.out.println("HashSet: "+newset.size());
//            int deff = old.size() - newset.size();
//            System.out.println("Deff: "+deff);
            
//            int i=0;
//            for (String string : newset) {
//            	i++;
//				System.out.println(i+": "+string);
//			}
//
//            String regex = "(\\w+(ГОСТ)+\\w*)";//(\\w+)ДСТУ(\\w+)ОТС(\\w+)ТУ(\\w+)*" ;
//            String s = "Лист 1.5х700х1400 ГОСТ 19904-90 / 17Х18Н9Н ТУ 14-1-2186-77";
//            Pattern p2 = Pattern. compile (regex);
//            Matcher m2 = p2.matcher(s);
//            while (m2.find()) {
//                System.out.println("e-mail: " + m2.group());
//            }
//        while (m2.find()) {
//
//            System.out.println("1: " + m2.group(1));
//            System.out.println("2: " + m2.group(2));
//        }

//    }catch (IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        catch(CorruptedTableException ex){
//            System.out.println(ex.getMessage());
//        }
//
//        finally {
//            try {
//                table.close();
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
// }
    }
}
