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
	private HashSet<String> gostsL;
	private HashSet<String> ostL;
	private HashSet<String> dstuL;
	private HashSet<String> tuL;

	private String gost;
	private String ost;
	private String tu;
	private String dstu;
	private String newType;

	private int countRows;

	private RDBF readLines;
	private ArrayList<String> lines;

	public Parse() {
		this.gost = "-";
		this.ost = "-";
		this.tu = "-";
		this.dstu = "-";
		readConfig();
		readLines = new RDBF();
		try {
			lines = new ArrayList<String>(readLines.getCollectionRows(true));
			this.countRows = lines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parse(String pathToFile) {
		this.gost = "-";
		this.ost = "-";
		this.tu = "-";
		this.dstu = "-";
		readConfig();
		readLines = new RDBF(pathToFile);
		try {
			lines = new ArrayList<String>(readLines.getCollectionRows(true));
			this.countRows = lines.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readConfig() {
		HashMap<String, ArrayList<String>> mapConst = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("const", "name", "con"));
		for (Map.Entry<String, ArrayList<String>> setmap : mapConst.entrySet()) {
			String key = setmap.getKey();
			ArrayList<String> value = new ArrayList<String>(setmap.getValue());
			switch (key) {
			case "gost":
				gost = value.get(0);
				break;
			case "dstu":
				dstu = value.get(0);
				break;
			case "tu":
				tu = value.get(0);
				break;
			case "ost":
				ost = value.get(0);
				break;
			default:
				newType = value.get(0);
				break;
			}
		}
	}

	public ArrayList<String> getNames() {
		HashSet<String> setOfString = new HashSet<String>();
		for (String st : lines) {
			if ((st.contains(gost) | st.contains(ost) | st.contains(dstu)
					| st.contains(tu)) & (st!="") ) {
				int posG, posD, posT = 0;
				posG = st.indexOf(gost);
				posD = st.indexOf(dstu);
				posT = st.indexOf(tu);
				Integer[] posl = { posD, posG, posT };
				ArrayList<Integer> maxOfMax = new ArrayList<Integer>();
				for (int i = 0; i < posl.length; i++) {
					if (posl[i] != -1) {
						maxOfMax.add(posl[i]);
					}
				}
				if(maxOfMax.size() != 0){
				int minOf = Collections.min(maxOfMax);
				setOfString.add(st.substring(0, minOf));
				}
			} else {
				setOfString.add(st.trim());
			}
		}
		namesL = new ArrayList<String>(setOfString);
		Collections.sort(namesL);
		return namesL;
	}

	public HashSet<String> getGosts() {
		return gostsL;
	}

	public HashSet<String> getOst() {

		return ostL;
	}

	public HashSet<String> getDstu() {
		return dstuL;
	}

	public HashSet<String> getTu() {
		return tuL;
	}

	public int getCountRows() {
		return countRows;
	}
}
