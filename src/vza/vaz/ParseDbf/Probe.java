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
		String g = "ГОСТ\\s+\\d+(\\-\\d+)+";
		////////////////////////////////////
		String o = "[^Г]СТ(.)+(\\d)+\\s{1}";
		String m = "Лист 0.8х500х1000 ГОСТ 19903-74-9-97776768768-67607-67 / ОК360В-2-III-Н-Ст3кп ДСТУ 2834-94 (ГОСТ 16523-97)";
		Pattern pp = Pattern.compile(g);
		Matcher mm = pp.matcher(m);
		while(mm.find()){
			System.out.println(mm.group());
		}
		System.out.println("End Regex:");
	}
}
