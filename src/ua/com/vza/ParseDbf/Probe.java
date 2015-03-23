package ua.com.vza.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public class Probe {


	public static void main(String[] args) throws IOException {

		System.out.println("Start Regex:");
		long starttime = System.currentTimeMillis();
		
		Parse parses = new Parse();
		ArrayList<String> probeList, probelist2;
		probeList = new ArrayList<String>(parses.getNames(false));
		probelist2 = new ArrayList<String>(parses.getGosts(false));

		for (String string : probeList) {
			System.out.println(string);
		}
		long endtime = System.currentTimeMillis();
		long totaltime = (endtime - starttime) / 1000 ;
		System.out.println("Total before dulpicate delete lines: "+parses.getNoSortedCountRows());
		System.out.println("Total after dulpicate delete lines: "+parses.getCountNamedRows());
		System.out.println("Total lines process: "+probeList.size());
		System.out.println("Total time "+totaltime+" seconds.");

		 /// ГОСТ /////////////////////////
//		 String g = "ГОСТ\\s?\\d+((\\-|\\:)\\d+)+";
//		 //////////////////////////////////
//		 String ost = "ОСТ\\s?\\d+((\\-|\\:)\\d+)+";
//		 //////////////////////////////////
//		 //////////////////////////////////((ГОСТ)|(ОСТ)|(ТУ)|(ДСТУ))";
//		 String dstu = "ДСТУ\\s?\\d+((\\-|\\:)\\d+)+";
		 /////////////////////////////////
//		 String mark = ".*(?=ГОСТ)";
		 /////////////////////////////////

//		 String m = "Д КР Н 24х2000 ГОСТ 2060-2006 / Л59-1 ГОСТ 15527-70";
//		 String osts = "Круг В-НД 30 ГОСТ 2590-2006 / ОСТ 2377 45 Б-М3-ТВ2-НГ ГОСТ 1050-88";
//		 Pattern pp = Pattern.compile(mark);
//		 Matcher mm = pp.matcher(m);
//		 while(mm.find()){
//		 System.out.println(mm.group());
//		 }
		 System.out.println("End Regex:");
	}
}
