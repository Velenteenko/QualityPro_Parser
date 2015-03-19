package vza.vaz.XmlSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXmlSettings {
		
	private static final String PATH_TO_CONFIG_DIR = "./config";
	private static final String CONFIG_FILE = "config-settings.xml";
	
	private static ArrayList<String> collectList;
	private static Map<String, ArrayList<String>> resultMap;
	
	//public static String pathConfigFile;
	
	
	private static String GenerateConfigFile()
	{
		File dir = new File(PATH_TO_CONFIG_DIR);
		boolean createdDit = dir.mkdir();
		if (createdDit) {
			System.out.println("Directory create successfull!");
		}
		File file = new File(dir.getPath()+"/"+CONFIG_FILE);
		if (!file.exists()) {
			boolean createFile;
			try {
				createFile = file.createNewFile();
				if (createFile) {
					System.out.println("File create successfull!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file.getPath();
	}
	
	public static void createXML(String pathFileToSave)
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
		Document document = documentBuilder.newDocument();
		
		Element marketEl = document.createElement("market");
		document.appendChild(marketEl);
		
		Element depEl = document.createElement("department");
		//create attribute (ex)
		Attr attrDep = document.createAttribute("id");
		attrDep.setValue("123");
		depEl.setAttributeNode(attrDep);
		//
		marketEl.appendChild(depEl);
		
		Element prodEl = document.createElement("product");
		prodEl.setAttribute("pid", "02-12-55");
		depEl.appendChild(prodEl);
		
		Element titleEl = document.createElement("title");
		titleEl.appendChild(document.createTextNode("Product number 1"));
		//titleEl.setNodeValue("Product node value");
		prodEl.appendChild(titleEl);
		
		Element priceEl = document.createElement("price");
		priceEl.appendChild(document.createTextNode("124,50"));
		prodEl.appendChild(priceEl);
		
		Element amoEl = document.createElement("amount");
		amoEl.appendChild(document.createTextNode("count"));
		prodEl.appendChild(amoEl);
		
		// saving a document
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(pathFileToSave));
			
			transformer.transform(domSource, streamResult);
			System.out.println("Document saved!!!");
			
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, ArrayList<String>> readXML(String pathToConfigFile, String tag_Root_Element, String tag_Id_Attribute, String tag_Value)
	{	
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document;
				document = documentBuilder.parse(new File(pathToConfigFile));
//			String rootElString = document.getDocumentElement().getNodeName();
//			System.out.println("rootElString = "+rootElString);
			
			//Node	NodeList	
			NodeList listTitle = document.getElementsByTagName(tag_Root_Element);
			int listElemsCount = listTitle.getLength();
//			System.out.println("Number of Elements: <"+tag_Root_Element+">"+listTitle.getLength());
			
			// variables
			String tableName="";
			int colElement=0;
			Element iterElement = null;
			resultMap = new HashMap<String, ArrayList<String>>();
			collectList = null;
						
			if (listElemsCount > 1) {
				for(int i =0; i < listElemsCount;i++)
				{
					iterElement = (Element) listTitle.item(i);
					
					tableName = iterElement.getAttributes().getNamedItem(tag_Id_Attribute).getNodeValue();
					colElement = iterElement.getElementsByTagName(tag_Value).getLength();
					
					collectList = new ArrayList<String>();
					if (colElement > 1) {
						for (int j = 0; j < colElement; j++) 
						{
						    collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(j).getNodeValue());	
						} 
						resultMap.put(tableName, collectList);
					}
					else 
					{
						collectList = new ArrayList<String>();
						collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(0).getNodeValue());
						
						colElement = iterElement.getElementsByTagName(tag_Value).getLength();
						
						resultMap.put(tableName, collectList);
					}
				}
			} 
			else 
			{
				iterElement = (Element) listTitle.item(0);
				tableName = iterElement.getAttributes().getNamedItem(tag_Id_Attribute).getNodeValue();
				
				if (colElement > 1) {
					for (int j = 0; j < colElement; j++) 
					{
						collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(j).getNodeValue());
					} 
				}
				else 
				{
					collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(0).getNodeValue());
				}
				resultMap.put(tableName, collectList);	
			}
		return resultMap;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static Map<String, ArrayList<String>> readXML(String tag_Root_Element, String tag_Id_Attribute, String tag_Value) 
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document;
			if (!(new File(PATH_TO_CONFIG_DIR+"/"+CONFIG_FILE).exists())) {
				GenerateConfigFile();
				return null;
			}
			File checkFile = new File(PATH_TO_CONFIG_DIR+"/"+CONFIG_FILE);
			if(checkFile.length()==0)
				return null;
			document = documentBuilder.parse(checkFile);
//			String rootElString = document.getDocumentElement().getNodeName();
//			System.out.println("rootElString = "+rootElString);
			
			//Node	NodeList	
			NodeList listTitle = document.getElementsByTagName(tag_Root_Element);
			int listElemsCount = listTitle.getLength();
//			System.out.println("Number of Elements: <"+tag_Root_Element+">"+listTitle.getLength());
			
			// variables
			String tableName="";
			int colElement=0;
			Element iterElement = null;
			resultMap = new HashMap<String, ArrayList<String>>();
			collectList = null;
						
			if (listElemsCount > 1) {
				for(int i =0; i < listElemsCount;i++)
				{
					iterElement = (Element) listTitle.item(i);
					
					tableName = iterElement.getAttributes().getNamedItem(tag_Id_Attribute).getNodeValue();
					colElement = iterElement.getElementsByTagName(tag_Value).getLength();
					
					collectList = new ArrayList<String>();
					if (colElement > 1) {
						for (int j = 0; j < colElement; j++) 
						{
						    collectList.add(iterElement.getElementsByTagName(tag_Value).item(j).getChildNodes().item(0).getNodeValue());	
						} 
						resultMap.put(tableName, collectList);
					}
					else 
					{
						collectList = new ArrayList<String>();
						collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(0).getNodeValue());
						
						colElement = iterElement.getElementsByTagName(tag_Value).getLength();
						
						resultMap.put(tableName, collectList);
					}
				}
			} 
			else 
			{
				iterElement = (Element) listTitle.item(0);
				tableName = iterElement.getAttributes().getNamedItem(tag_Id_Attribute).getNodeValue();
				
				collectList = new ArrayList<String>();
				if (colElement > 1) {
					for (int j = 0; j < colElement; j++) 
					{
						collectList.add(iterElement.getElementsByTagName(tag_Value).item(j).getChildNodes().item(0).getNodeValue());
					} 
				}
				else 
				{
					collectList.add(iterElement.getElementsByTagName(tag_Value).item(0).getChildNodes().item(0).getNodeValue());
				}
				resultMap.put(tableName, collectList);	
			}
		return resultMap;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	
}
