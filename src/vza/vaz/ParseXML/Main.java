package vza.vaz.ParseXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import vza.vaz.ParseXML.Process.XMLProcessing;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException {

		try{
		HashMap<String, ArrayList<String>> hashMap  = new HashMap<String, ArrayList<String>>(XMLProcessing.readXML("table","name", "column"));
		
		for (Map.Entry<String, ArrayList<String>> confs : hashMap.entrySet()) 
			System.out.println("Table: "+confs.getKey()+" Columns: "+confs.getValue().toString());
		} catch(Exception ex)
		{
			extracted();
			System.out.println(ex.getMessage());
		}
//		if(hashMap == null)
//		{ 
//			extracted();
//		    return;
//		}
		
		
		
		
//		File file = new File("./src/congif3/file.xml");
		
//		//Если требуемого файла не существует.
//		if(!file.exists()) {
//		   //Создаем его.
//		   try {
//			   
//			file.createNewFile();
//			System.out.println("File created!");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
		
	}

	private static void extracted() {
		{
			System.out.println("Hash Map is null!!\nCheck config file!!!");
		return;
		}
	}

}
