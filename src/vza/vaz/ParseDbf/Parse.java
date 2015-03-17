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
    
	private  ArrayList<String> namesL;
	private  HashSet<String> gostsL;
	private  HashSet<String> ostL;
	private  HashSet<String> dstuL;
	private  HashSet<String> tuL;
	
	private String gost;
	private String ost;
	private String tu;
	private String dstu;
	private String newType;
	
	private int countRows;
	
	public Parse()
	{
		this.gost = "-";
		this.ost = "-";
		this.tu = "-";
		this.dstu = "-";
		readConfig();
	}
	
	
	private void readConfig()
	{
		HashMap<String, ArrayList<String>> mapConst = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("const", "name", "con"));
		for (Map.Entry<String, ArrayList<String>> setmap : mapConst.entrySet()) 
		{
			String key = setmap.getKey();
			ArrayList<String> value = new ArrayList<String>(setmap.getValue());
			switch (key)
			{
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
	
	public  ArrayList<String> getNames()
	{
			
		return namesL;
	}
	
	public  HashSet<String> getGosts()
	{
		return gostsL;
	}
    
	public  HashSet<String> getOst() {
		
		return ostL;
	}

	public  HashSet<String> getDstu() {
		return dstuL;
	}

	public  HashSet<String> getTu() {
		return tuL;
	}


	public int getCountRows() {
		return countRows;
	}
}
