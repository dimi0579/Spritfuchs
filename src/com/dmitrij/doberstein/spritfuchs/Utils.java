package com.dmitrij.doberstein.spritfuchs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dmitrij.doberstein.spritfuchs.dataclasses.Day;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationBrand;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

public class Utils {
	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//	private static final String TANKSTELLENLISTE = "alletankstellen";
//	private static final String TANSTELLEDATEN = "tankstelledaten";
	public static enum ObjectTypes {
		TANKSTELLENLISTE,
		TANKSTELLEDATEN
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
			Document doc = null;
			ArrayList<TankstellenPosition> atp = null;
			
			switch (objectType){
			case TANKSTELLENLISTE:
				atp = new ArrayList<TankstellenPosition>();
				doc = Utils.getDocObject(xmlString);
				if (doc != null) {
					NodeList nodes = doc.getElementsByTagName("tankstelle");
					for (int i = 0; i < nodes.getLength(); i++) {
						Node node = nodes.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element) node;

							TankstellenPosition tp = new TankstellenPosition();
							tp.setTankstelleId(getValue("id", element));
							tp.setTankstelleName(getValue("name", element));
							tp.setTankstelleMarke(getValue("marke", element));
							tp.setTankstelleLatitude(getValue("latitude",
									element));
							tp.setTankstelleLongtitude(getValue("longtitude",
									element));
							tp.setTankstelleStrasse(getValue("strasse", element));
							tp.setTankstelleHausnr(getValue("hausnr", element));
							tp.setTankstellePlz(getValue("plz", element));
							tp.setTankstelleOrt(getValue("ort", element));
							tp.setTankstelleGemeldet(getValue("gemeldet",
									element));

							// distnaz auf zwei nachkommastellen kürzen
							String distanz = getValue("distanz", element);
							float dist = Float.parseFloat(distanz);
							tp.setTankstelleEntfernung(String.format("%8.2f",
									dist));
							//********************************************************

							Element lastNode = (Element) node.getLastChild();
							String lastNodeName = lastNode.getNodeName();
							if ("sortepreise".equalsIgnoreCase(lastNodeName)) {
								NodeList tnl = lastNode.getChildNodes();

								List<SortePreis> spListe = new ArrayList<SortePreis>();
								for (int j = 0; j < tnl.getLength(); j++) {
									Node pNode = tnl.item(j);
									Element pElement = (Element) pNode;

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
				}
				return atp;
//				break;
			case TANKSTELLEDATEN:
				atp = new ArrayList<TankstellenPosition>();
				doc = Utils.getDocObject(xmlString);
				if (doc != null) {
					NodeList nodes = doc.getElementsByTagName("tankstelle");
					for (int i = 0; i < nodes.getLength(); i++) {
						Node node = nodes.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element) node;

							TankstellenPosition tp = new TankstellenPosition();
							tp.setTankstelleId(getValue("id", element));
							tp.setTankstelleName(getValue("name", element));
							tp.setTankstelleMarke(getValue("marke", element));
//							tp.setTankstelleLatitude(getValue("latitude",
//									element));
//							tp.setTankstelleLongtitude(getValue("longtitude",
//									element));
							tp.setTankstelleStrasse(getValue("strasse", element));
							tp.setTankstelleHausnr(getValue("hausnr", element));
							tp.setTankstellePlz(getValue("plz", element));
							tp.setTankstelleOrt(getValue("ort", element));
							tp.setTankstelleGemeldet(getValue("gemeldet",
									element));

							// distnaz auf zwei nachkommastellen kürzen
//							String distanz = getValue("distanz", element);
//							float dist = Float.parseFloat(distanz);
//							tp.setTankstelleEntfernung(String.format("%8.2f",
//									dist));
							//********************************************************

							Element lastNode = (Element) node.getLastChild();
							String lastNodeName = lastNode.getNodeName();
							if ("oeffnungszeiten".equalsIgnoreCase(lastNodeName)) {
								NodeList tnl = lastNode.getChildNodes();

								Oeffnungszeiten oz = new Oeffnungszeiten();
								for (int j = 0; j < tnl.getLength(); j++) {
									Node pNode = tnl.item(j);
									Element pElement = (Element) pNode;

//									Oeffnungszeiten oz = new Oeffnungszeiten();
									String tag = getValue("tag", pElement);
									String von = getValue("von", pElement);
									String bis = getValue("bis", pElement);
									if(tag != null && !tag.isEmpty()){
										int iTag = Integer.parseInt(tag);
										switch(iTag){
										case 1:
											oz.setOzMontag(von + " - " + bis);
											break;
										case 2:
											oz.setOzDienstag(von + " - " + bis);
											break;
										case 3:
											oz.setOzMittwoch(von + " - " + bis);
											break;
										case 4:
											oz.setOzDonnerstag(von + " - " + bis);
											break;
										case 5:
											oz.setOzFreitag(von + " - " + bis);
											break;
										case 6:
											oz.setOzSamstag(von + " - " + bis);
											break;
										case 7:
											oz.setOzSonntagFeiertag(von + " - " + bis);
											break;
										default:
											break;
										}
									}
									
//									SortePreis sp = new SortePreis();
//									sp.setPreisId(getValue("preisid", pElement));
//									sp.setSorteId(getValue("sorteid", pElement));
//									sp.setSorte(getValue("sorte", pElement));
//									sp.setPreis(getValue("preis", pElement));

//									ozListe.add(oz);
								}
								tp.setTankstelleZeiten(oz);
							}
							atp.add(tp);
						}
					}
				}
				return atp;
//				break;
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
	public static int getTankstellenImage(StationBrand marke){
		int ret = 0;
		switch(marke){
		case AGIP:
			ret = R.drawable.agip64;
			break;
		case ARAL:
			ret = R.drawable.aral64;
			break;
		case AVIA:
			ret = R.drawable.avia64;
			break;
		case BP:
			ret = R.drawable.bp64;
			break;
		case ESSO:
			ret = R.drawable.esso64;
			break;
		case JET:
			ret = R.drawable.jet64;
			break;
		case OMV:
			ret = R.drawable.omv64;
			break;
		case SHELL:
			ret = R.drawable.shell64;
			break;
		default:
			ret = R.drawable.tankstelle64;
			break;
		}
		return ret;
	}
	public static int getTankstellenImageSmall(StationBrand marke){
		int ret = 0;
		switch(marke){
		case AGIP:
			ret = R.drawable.agip24;
			break;
		case ARAL:
			ret = R.drawable.aral24;
			break;
		case AVIA:
			ret = R.drawable.avia24;
			break;
		case BP:
			ret = R.drawable.bp24;
			break;
		case ESSO:
			ret = R.drawable.esso24;
			break;
		case JET:
			ret = R.drawable.jet24;
			break;
		case OMV:
			ret = R.drawable.omv24;
			break;
		case SHELL:
			ret = R.drawable.shell24;
			break;
		default:
			ret = R.drawable.tankstelle24;
			break;
		}
		return ret;
	}
	public static String getTagName(Day day){
		String ret = "";
		switch (day) {
		case MONDAY:
			ret = "Montag";
			break;

		case TUESDAY:
			ret = "Dienstag";
			break;

		case WEDNESDAY:
			ret = "Mittwoch";
			break;

		case THURSDAY:
			ret = "Donnerstag";
			break;

		case FRIDAY:
			ret = "Freitag";
			break;

		case SATURDAY:
			ret = "Samstag";
			break;

		case SUNDAY:
			ret = "Sonntag";
			break;

		case HOLLIDAY:
			ret = "Feiertage";
			break;

		default:
			ret ="";
			break;
		}
		return ret;
	}
}
