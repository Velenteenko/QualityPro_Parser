package vza.vaz.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;

//import org.jamel.dbf.processor.DbfProcessor;

public class Probe {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Create object.");
		RDBF rdbf = new RDBF();
		System.out.println("Object created.");
		ArrayList<String> list = new ArrayList<String>(rdbf.getCollectionRows(true));
		System.out.println("Get Strings.");
		System.out.println("Count Lines: "+rdbf.getCountLines());
		for (String string : list) {
			System.out.println(string);
		}
		System.out.println("Command Successfull!!!");
	}
}
