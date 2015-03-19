/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vza.vaz.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import vza.vaz.XmlSettings.ParseXmlSettings;

/**
 *
 * @author Dmitri
 */
public class Parse {

	private ArrayList<String> namesL;
	private ArrayList<String> gostsL;
	private ArrayList<String> ostL;
	private ArrayList<String> dstuL;
	private ArrayList<String> tuL;

	private ArrayList<String> specifications;
	
	private final String GOST = "gost";
	private final String OST = "ost";
	private final String TU = "tu";
	private final String DSTU = "dstu";

//	private int countNamedRows;

	private RDBF readLines;
	private ArrayList<String> lines;

	public Parse() {
		readConfig();
		readLines = new RDBF();
		try {
			lines = new ArrayList<String>(readLines.getCollectionRows(true));
//			this.countRows = lines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parse(String pathToFile) {
		readConfig();
		readLines = new RDBF(pathToFile);
		try {
			lines = new ArrayList<String>(readLines.getCollectionRows(true));
//			this.countRows = lines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readConfig() {
		HashMap<String, ArrayList<String>> mapConst = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("const", "name", "con"));
		specifications = new ArrayList<String>();
		for (Map.Entry<String, ArrayList<String>> setmap : mapConst.entrySet()) {
			ArrayList<String> value = new ArrayList<String>(setmap.getValue());
			specifications.add(value.get(0));
		}
	}

	public ArrayList<String> getNames() {
		HashSet<String> setOfString = new HashSet<String>();
		for (String st : lines) {
				if((st=="")|(st == null))
					continue;
				ArrayList<Integer> poss = new ArrayList<Integer>();
				for (String spec : specifications) {
					poss.add(st.indexOf(spec));
				}
				
				ArrayList<Integer> maxOfArray = new ArrayList<Integer>();
				for (Integer ints : poss) {
					if (ints != -1) {
						maxOfArray.add(ints);
					}
				}
				if (maxOfArray.size() != 0) {
					int minimunOf = Collections.min(maxOfArray);
					setOfString.add(st.substring(0, minimunOf));
				}else {
					setOfString.add(st.trim());
				}
		}
		namesL = new ArrayList<String>(setOfString);
		Collections.sort(namesL);
		return namesL;
	}

	public ArrayList<String> getGosts() {
		return gostsL;
	}

	public ArrayList<String> getOst() {

		return ostL;
	}

	public ArrayList<String> getDstu() {
		return dstuL;
	}

	public ArrayList<String> getTu() {
		return tuL;
	}

	public int getCountNamedRows() {
		return namesL.size();
	}
}
