package com.dmitrij.doberstein.spritfuchs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

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
	
	public static ArrayList<?> getObjects(ObjectTypes objectType, String xmlString){
		
		if(objectType != null){
			switch (objectType){
			case TANKSTELLENLISTE:
				ArrayList<TankstellenPosition> atp = new ArrayList<TankstellenPosition>();
				Document doc = Utils.getDocObject(xmlString);
				NodeList nodes = doc.getElementsByTagName("tankstelle");
				for(int i = 0; i < nodes.getLength(); i++){
					Node node = nodes.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE){
						Element element = (Element)node;
						
						TankstellenPosition tp = new TankstellenPosition();
						tp.setTankstelleId(getValue("id", element));
						tp.setTankstelleName(getValue("name", element));
						tp.setTankstelleMarke(getValue("marke", element));
						tp.setTankstelleLatitude(getValue("latitude", element));
						tp.setTankstelleLongtitude(getValue("longtitude", element));
						tp.setTankstelleStrasse(getValue("strasse", element));
						tp.setTankstelleHausnr(getValue("hausnr", element));
						tp.setTankstellePlz(getValue("plz", element));
						tp.setTankstelleOrt(getValue("ort", element));
						tp.setTankstelleGemeldet(getValue("gemeldet", element));
						
						Element lastNode = (Element)node.getLastChild();
						String lastNodeName = lastNode.getNodeName();
						if("sortepreise".equalsIgnoreCase(lastNodeName)){
							NodeList tnl = lastNode.getChildNodes();
							
							List<SortePreis> spListe = new ArrayList<SortePreis>();
							for(int j = 0; j < tnl.getLength(); j++){
								Node pNode = tnl.item(j);
								Element pElement = (Element)pNode;
								
								SortePreis sp = new SortePreis();
								sp.setPreisId(getValue("preisid", pElement));
								sp.setSorteId(getValue("sorteid", pElement));
								sp.setSorte(getValue("sorte", pElement));
								sp.setPreis(getValue("preis", pElement));
								
								spListe.add(sp);
							}
							tp.setTankstellePreise(spListe);
						}
						atp.add(tp);
					}
				}
				return atp;
//				break;
			case TANSTELLEDATEN:
				break;
			default:
				break;
			}
		}
		return null;
	}
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

}
