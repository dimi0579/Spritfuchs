package com.dmitrij.doberstein.spritfuchs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Utils {
	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//	private static final String TANKSTELLENLISTE = "alletankstellen";
//	private static final String TANSTELLEDATEN = "tankstelledaten";
	public static enum ObjectTypes {
		TANKSTELLENLISTE,
		TANSTELLEDATEN
	};
	
	public static String extracXml(String source){
		String ret = "";
		
		if(source != null && !source.isEmpty()){
			 int pos = source.indexOf("<!DOCTYPE");
	         ret = XMLHEADER + source.substring(0,pos-4);
		}
		
		return ret;
	}
	
	public static Document getDocObject(String xmlString){
		Document doc = null;
		if(xmlString != null && !xmlString.isEmpty()){
			try {
				// convert String into InputStream
				InputStream is = new ByteArrayInputStream(xmlString.getBytes());
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(is);
				doc.getDocumentElement().normalize();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}
	
	public static ArrayList<?> getObjects(ObjectTypes objectType){
		
		if(objectType != null){
			switch (objectType){
			case TANKSTELLENLISTE:
				
				break;
			case TANSTELLEDATEN:
				break;
			default:
				break;
			}
		}
		return null;
	}
}
