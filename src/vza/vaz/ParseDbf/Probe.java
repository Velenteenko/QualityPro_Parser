package vza.vaz.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;

//import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;

//import org.jamel.dbf.processor.DbfProcessor;

public class Probe {

	public static void main(String[] args) throws IOException {

		 System.out.println("Start Regex:");
		
		Parse parses = new Parse();
		ArrayList<String> probeList;
		probeList = new ArrayList<String>(parses.getOst());

		for (String string : probeList) {
			System.out.println(string);
		}
		System.out.println("Total named lines: "+parses.getNoParseCountRows());
		System.out.println("Total lines process: "+parses.getOst().size());

		// /// ГОСТ /////////////////////////
		// String g = "ГОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		// //////////////////////////////////
		// String ost = "ОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		// //////////////////////////////////
		// //////////////////////////////////((ГОСТ)|(ОСТ)|(ТУ)|(ДСТУ))";
		// String dstu = "ДСТУ\\s?\\d+((\\-|\\:)\\d+)+";
		// /////////////////////////////////
		// String mark = "(/\\s?)(\\S+)";
		// /////////////////////////////////

//		 String m = "Д КР Н 24х2000 ГОСТ 2060-2006 / Л59-1 ГОСТ 15527-70";
//		 String osts = "Круг В-НД 30 ГОСТ 2590-2006 / ОСТ 2377 45 Б-М3-ТВ2-НГ ГОСТ 1050-88";
//		 Pattern pp = Pattern.compile(name);
//		 Matcher mm = pp.matcher(m);
//		 while(mm.find()){
//		 System.out.println(mm.group(1));
//		 }
		 System.out.println("End Regex:");
	}
}
