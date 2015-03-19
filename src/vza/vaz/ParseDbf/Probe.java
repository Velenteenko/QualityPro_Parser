package vza.vaz.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.jamel.dbf.processor.DbfProcessor;

public class Probe {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		System.out.println("Create object.");
//		RDBF rdbf = new RDBF("/home/dmitri/HProjectFiles/m1.DBF");
//		System.out.println("Object created.");
//		ArrayList<String> list = new ArrayList<String>(rdbf.getCollectionRows(true));
//		System.out.println("Get Strings.");
//		System.out.println("Count Lines: "+rdbf.getCountLines());
//		for (String string : list) {
//			System.out.println(string);
//		}
//		System.out.println("Command Successfull!!!");
//		
//		System.out.println("After inject names: ");
//		
//		Parse parse = new Parse();
//		ArrayList<String> list2 = new ArrayList<String>(parse.getNames());
//		System.out.println("Count Lines: "+list2.size());
//		for (String string : list2) {
//			System.out.println(string);
//		}
		System.out.println("Start Regex:");
		///// ГОСТ /////////////////////////
		String g = "ГОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		////////////////////////////////////
		String ost = "ОСТ\\s?\\d+((\\-|\\:)\\d+)+";
		////////////////////////////////////
		String tu = "ТУ(.+)\\d(\\s|\\d)";
		////////////////////////////////////
		String dstu = "ДСТУ\\s?\\d+((\\-|\\:)\\d+)+";
		///////////////////////////////////
		String mark = "(/\\s?)(\\S+)";
		///////////////////////////////////
		
		String m = "Лента 2.0х50 ГОСТ 2283-79 / 60С2А-Т-С ГОСТ 14959-79";
		String osts = "Смола п/х хлорированная /ПСХ -ЛС ОСТ 6-031-37-79";
		Pattern pp = Pattern.compile(mark);
		Matcher mm = pp.matcher(osts);
		while(mm.find()){
			System.out.println(mm.group(2));
		}
		System.out.println("End Regex:");
	}
}
