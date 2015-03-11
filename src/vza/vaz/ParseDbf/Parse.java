/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vza.vaz.ParseDbf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import vza.vaz.XmlSettings.ParseXmlSettings;

/**
 *
 * @author Dmitri
 */
public class Parse {
    
//    final String GOST = "ГОСТ";
//    final String OST = "ОСТ";
//    final String DSTU ="ДСТУ";
//    final String TU = "ТУ";
	private static ArrayList<String> namesL;
	private static HashSet<String> gostsL;
	private static HashSet<String> ostL;
	private static HashSet<String> dstuL;
	private static HashSet<String> tuL;
	
	private String gost;
	private String ost;
	private String tu;
	private String dstu;
	private String newType;
	
	
	private void rearConfig()
	{
		HashMap<String, ArrayList<String>> mapConst = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("const", "name", "con"));
		for (Map.Entry<String, ArrayList<String>> setmap : mapConst.entrySet()) {
			String key = setmap.getKey();
			ArrayList<String> value = new ArrayList<String>(setmap.getValue());
			switch (key) {
			case "gost":
				gost=value.get(0);
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
	
	public static ArrayList<String> getNames()
	{
			
		return namesL;
	}
	
	public static HashSet<String> getGosts()
	{
		return gostsL;
	}
    
	public static HashSet<String> getOst() {
		
		return ostL;
	}

	public static HashSet<String> getDstu() {
		return dstuL;
	}

	public static HashSet<String> getTu() {
		return tuL;
	}
}
