/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.vza.ParseDbf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.com.vza.XmlSettings.ParseXmlSettings;

/**
 *
 * @author Dmitri
 */
public class Parse {

	private Set<String> namesL;
	private Set<String> gostsL;
	private Set<String> ostL;
	private Set<String> dstuL;
	private Set<String> tuL;
	private Set<String> markL;

	private ArrayList<String> specifications;
	private HashMap<String, String> kvSpecifications;
	private HashMap<String, String> kvGroupSpecifications;

	private final String GOST = "gost";
	private final String OST = "ost";
	private final String TU = "tu";
	private final String DSTU = "dstu";
	private final String MARK = "mark";
	// private int countNamedRows;

	private RDBF readDBFLines;
	private ArrayList<String> lines;

	public Parse() {
		readConfig();
		readDBFLines = new RDBF();
		try {
			lines = new ArrayList<String>(readDBFLines.getCollectionRows());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parse(String pathToFile) {
		readConfig();
		readDBFLines = new RDBF(pathToFile);
		try {
			lines = new ArrayList<String>(readDBFLines.getCollectionRows());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readConfig() {

		// get specification
		HashMap<String, ArrayList<String>> mapConst = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("const", "name", "con"));
		specifications = new ArrayList<String>();
		for (Map.Entry<String, ArrayList<String>> setmap : mapConst.entrySet()) {
			ArrayList<String> value = new ArrayList<String>(setmap.getValue());
			specifications.add(value.get(0));
		}

		// get regular expression for specifications
		mapConst = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("regex", "name", "r"));
		kvSpecifications = new HashMap<String, String>();
		for (Map.Entry<String, ArrayList<String>> regexList : mapConst
				.entrySet()) {
			kvSpecifications.put(regexList.getKey(), regexList.getValue()
					.get(0));
		}

		// get group regular expressions
		mapConst = new HashMap<String, ArrayList<String>>(
				ParseXmlSettings.readXML("regexGroup", "name", "g"));
		kvGroupSpecifications = new HashMap<String, String>();
		for (Map.Entry<String, ArrayList<String>> regexGroup : mapConst
				.entrySet()) {
			kvGroupSpecifications.put(regexGroup.getKey(), regexGroup
					.getValue().get(0));
		}

	}

	private HashSet<String> readRegexLines(String pattern, int group) {
		ArrayList<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++) {
			// String retSt = "";
			String line = lines.get(i);
			Pattern pp = Pattern.compile(pattern);
			Matcher mm = pp.matcher(line);
			if (group <= 0) {
				while (mm.find()) {
					tmpList.add(mm.group());
				}
			} else {
				while (mm.find()) {
					tmpList.add(mm.group(group));
					// retSt+=mm.group(group);
				}
				// tmpList.add(retSt);
			}
		}
		return new HashSet<String>(tmpList);
	}

	private String getSpecifications(HashMap<String, String> entryMap,
			String keySpecification) {
		String tmpValueRegex = "";
		for (Map.Entry<String, String> findGost : entryMap.entrySet()) {
			String key = findGost.getKey();
			String value = findGost.getValue();
			if (key.equals(keySpecification)) {
				tmpValueRegex = value;
				break;
			}
		}
		return tmpValueRegex;
	}

	public Set<String> getNames() {
		HashSet<String> setOfString = new HashSet<String>();
		for (String st : lines) {
			if ((st == "") | (st == null))
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
			} else {
				setOfString.add(st.trim());
			}
		}
		namesL = new HashSet<String>(setOfString);
//		if (sort) {
//			Collections.sort(namesL);;
//		}
		return namesL;
	}

	public Set<String> getGosts() {
		gostsL = new HashSet<String>(
				readRegexLines(getSpecifications(kvSpecifications, GOST),
						Integer.valueOf(getSpecifications(
								kvGroupSpecifications, GOST))));
//		if (sort) {
//			Collections.sort(gostsL);
//		}
		return gostsL;
	}

	public Set<String> getOst() {
		ostL = new HashSet<String>(readRegexLines(
				getSpecifications(kvSpecifications, OST),
				Integer.valueOf(getSpecifications(kvGroupSpecifications, OST))));
//		if (sort) {
//			Collections.sort(ostL);
//		}
		return ostL;
	}

	public Set<String> getDstu() {
		dstuL = new HashSet<String>(
				readRegexLines(getSpecifications(kvSpecifications, DSTU),
						Integer.valueOf(getSpecifications(
								kvGroupSpecifications, DSTU))));
//		if (sort) {
//			Collections.sort(dstuL);
//		}
		return dstuL;
	}

	public Set<String> getTu() {
		tuL = new HashSet<String>(readRegexLines(
				getSpecifications(kvSpecifications, TU),
				Integer.valueOf(getSpecifications(kvGroupSpecifications, TU))));
//		if (sort) {
//			Collections.sort(tuL);
//		}
		return tuL;
	}

	public Set<String> getMark() {
		markL = new HashSet<String>(
				readRegexLines(getSpecifications(kvSpecifications, MARK),
						Integer.valueOf(getSpecifications(
								kvGroupSpecifications, MARK))));
//		if (sort) {
//			Collections.sort(markL);
//		}
		return markL;
	}

	public int getCountNamedRows() {
		return namesL.size();
	}

	public int getNoSortedCountRows() {
		return readDBFLines.getCountNoSortedLines();
	}
}
