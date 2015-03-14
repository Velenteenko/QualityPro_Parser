package vza.vaz.ParseDbf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Probe {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		ArrayList<String> collect = new ArrayList<String>();
		RDBF rdbf = new RDBF("./tmpdb/m1.DBF");
		collect.addAll(rdbf.getCollectionRows());
		for (String string : collect) {
			System.out.println(string);
		}
	}

}
