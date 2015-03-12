package vza.vaz.readdbf;

//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import vza.vaz.XmlSettings.ParseXmlSettings;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
//import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;

public class MainRead {

	private static File stream2file(SmbFileInputStream in) throws IOException {
		final File tempFile = File.createTempFile("stream2file", ".tmp");
		tempFile.deleteOnExit();
		try (SmbFileOutputStream out = new SmbFileOutputStream(tempFile)) {
			
		} 
		return null;
	}
	
	
    public static void main(String[] args) {
        String db_path = "/home/velenteenko/WorkDir/m1.DBF";
        
        HashMap<String, ArrayList<String>> mapPath = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("path", "name", "p"));
        for (Map.Entry<String, ArrayList<String>> entryset : mapPath.entrySet()) {
			String value = entryset.getValue().get(0);
			db_path = value;
		}
       
        try {
			SmbFile file = new SmbFile(db_path);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Table table = new Table(new File(db_path),"CP1251");
        try {
            table.open(IfNonExistent.ERROR);
//            List<Field> fields = table.getFields();
//            for (Field field : fields)
//            {
//                System.out.println("Name:         " + field.getName());
//                System.out.println("Type:         " + field.getType());
//                System.out.println("Length:       " + field.getLength());
//                System.out.println("DecimalCount: " + field.getDecimalCount());
//                System.out.println();
//            }

        Iterator<Record> iterator = table.recordIterator();

         List<String> old = new ArrayList<String>();
           // old = new LinkedList<String>();
         Set<String> newset = new HashSet<String>();

            while (iterator.hasNext())
            {
                Record record = iterator.next();
               String ch = record.getStringValue("MN");
                if (ch != null) {
                    // System.out.println(ch);
                    // System.out.println("\n");
                    old.add(ch);
                    newset.add(ch);
                }
            }
            String st = old.get(2);
            int len = st.length();

            System.out.println("Duplicates:");

            System.out.println("Array: "+old.size()+" Len: "+len);
            System.out.println("HashSet: "+newset.size());
            int deff = old.size() - newset.size();
            System.out.println("Deff: "+deff);
            
            int i=0;
            for (String string : newset) {
            	i++;
				System.out.println(i+": "+string);
			}
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

    }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        catch(CorruptedTableException ex){
            System.out.println(ex.getMessage());
        }

        finally {
            try {
                table.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
 }
    }
}
