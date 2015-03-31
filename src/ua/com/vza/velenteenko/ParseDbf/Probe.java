package ua.com.vza.velenteenko.ParseDbf;

import java.io.IOException;
//import java.sql.Connection;
import java.sql.SQLException;
//import java.util.ArrayList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ua.com.vza.velenteenko.DBUtils.DBParseProcess;

//import ua.com.vza.DBUtils.DBProcess;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class Probe {

	public static void main(String[] args) throws IOException, SQLException {

		System.out.println("Start:");
		 long starttime = System.currentTimeMillis();
		// RDBF rdbf = new RDBF();
		// ArrayList<String> rows = new
		// ArrayList<String>(rdbf.getCollectionRows());
		// Parse parses = new Parse();
		// ArrayList<String> probeList, probelist2;
		// probeList = new ArrayList<String>(parses.getNames(false));
		// String[] arr = probeList.toArray(new String[probeList.size()]);

		// probelist2 = new ArrayList<String>(parses.getGosts(false));
		//
		// for (String string : probeList) {
		// System.out.println(string);
		// }
		// CommandExecutorLinux.execute("dbf_dump --fs=\",\" /home/velenteenko/WorkDir/m1.DBF"+" > "+"/home/velenteenko/WorkDir/m111.csv");
//		 long endtime = System.currentTimeMillis();
//		 long totaltime = (endtime - starttime) ;
		// System.out.println("Total before dulpicate delete lines: "+parses.getNoSortedCountRows());
		// System.out.println("Total after dulpicate delete lines: "+parses.getCountNamedRows());
		// System.out.println("Total lines process: "+probeList.size());
//		 System.out.println("Total time "+totaltime+" seconds.");

		// System.out.println("start connection");
		// DBProcess con = new DBProcess();
		// con.updateData();
		Set<String> db = new HashSet<String>();
		db.add("1");
		db.add("2");
		db.add("3");
		db.add("4");
		Set<String> parse = new HashSet<String>();
		parse.add("1");
		parse.add("2");
		parse.add("3");
		parse.add("4");
		parse.add("5");

		Set<String> res = new HashSet<String>(findNewRecords(parse, db));
		for (String string : res) {
			System.out.println("New: " + string);
		}
		// ArrayList<String> list = new
		// ArrayList<String>(con.selectValues("SELECT af_parseName,af_parseMark,af_parseGOST,af_parseOST,af_parseTU,af_parseDSTU FROM sprCountLinesInDBF WHERE id=1",
		// "int", new
		// String[]{"af_parseName","af_parseMark","af_parseGOST","af_parseOST","af_parseTU","af_parseDSTU"}));
		// for (String string : list) {
		// System.out.println(string);
		// }

		// con.insertQuery("INSERT INTO sprProductName (name) VALUES (?)", new
		// ArrayList<String>(Arrays.asList("Petya2","Vasya1","Dima1","Colya1")));
		// System.out.println("Insert successfull");
		// con.updateQuery("UPDATE sprCountLinesInDBF SET before_parse=?, after_parse=? WHERE id=1",
		// new String[]{"0","0"});
		// System.out.println("Update successfull");
		// System.out.println("connection closed...");

		// / ГОСТ /////////////////////////
		// String g = "ГОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		// //////////////////////////////////
		// String ost = "ОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		// //////////////////////////////////
		// //////////////////////////////////((ГОСТ)|(ОСТ)|(ТУ)|(ДСТУ))";
		// String dstu = "ДСТУ\\s?\\d+((\\-|\\:)\\d+)+";
		// ///////////////////////////////
		// String mark = ".*(?=ГОСТ)";
		// ///////////////////////////////

		// String m = "Д КР Н 24х2000 ГОСТ 2060-2006 / Л59-1 ГОСТ 15527-70";
		// String osts =
		// "Круг В-НД 30 ГОСТ 2590-2006 / ОСТ 2377 45 Б-М3-ТВ2-НГ ГОСТ 1050-88";
		// Pattern pp = Pattern.compile(mark);
		// Matcher mm = pp.matcher(m);
		// while(mm.find()){
		// System.out.println(mm.group());
		// }
	
//		ReadDBFDB rdbf = new ReadDBFDB();
//		List<String> lists = new ArrayList<String>(rdbf.getCollectionRows());
//		for (String string : lists) {
//			System.out.println(string);
//		}
//		System.out.println("Total lines: " + rdbf.getCountCollectionsRows());
		
//		Parse p = new Parse(false);
//		List<String> name = new ArrayList<String>(p.getNames());
//		List<String> mark = new ArrayList<String>(p.getMark());
//		List<String> gost = new ArrayList<String>(p.getGosts());
//		List<String> ost = new ArrayList<String>(p.getOst());
//		List<String> tu = new ArrayList<String>(p.getTu());
//		List<String> dstu = new ArrayList<String>(p.getDstu());
		 DBParseProcess process = new DBParseProcess();
		 process.updateData();
		 System.out.println("--------------\nDbProcess done!");
		System.out.println("Job done!\nAll data are parse!!!");
		
		 long endtime = System.currentTimeMillis();
		 long totaltime = (endtime - starttime) ;
//		 System.out.println("Total time "+totaltime+" seconds.");
//		 DBParseProcess process = new DBParseProcess();
//		 process.updateData();
//		 System.out.println("--------------\nDbProcess done!");
		System.out.println("End:");
	}

	private static Set<String> findNewRecords(Set<String> parseList,
			Set<String> dbList) {
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
}
