package ua.com.vza.XmlSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException {

		try{
		HashMap<String, ArrayList<String>> hashMap  = new HashMap<String, ArrayList<String>>(ParseXmlSettings.readXML("table","name","column"));
		
		for (Map.Entry<String, ArrayList<String>> confs : hashMap.entrySet()) 
			System.out.println("Table: "+confs.getKey()+" Columns: "+confs.getValue().get(0));
		
		}
		catch(Exception ex)
		{   
			extracted();
			System.out.println(ex.getMessage());
		}		
	}

	private static void extracted() {
		{
			System.out.println("Hash Map is null!!\nCheck config file!!!");
		return;
		}
	}

}