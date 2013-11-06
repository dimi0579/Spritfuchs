package com.dmitrij.doberstein.spritfuchs.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.MonthDisplayHelper;

import com.dmitrij.doberstein.spritfuchs.R;
import com.dmitrij.doberstein.spritfuchs.VergleichActivity;
import com.dmitrij.doberstein.spritfuchs.adapters.CustomListAdapterNew;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Day;
import com.dmitrij.doberstein.spritfuchs.dataclasses.FuelSort;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Oeffnungszeiten;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Price;
import com.dmitrij.doberstein.spritfuchs.dataclasses.SortePreis;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationBrand;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationItem;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Utils {
	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String PREFNAME = "myprefs";
//	private static final String TANKSTELLENLISTE = "alletankstellen";
//	private static final String TANSTELLEDATEN = "tankstelledaten";
	public static enum ObjectTypes {
		TANKSTELLENLISTE,
		TANKSTELLEDATEN, STATIONITEMLIST
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
				e.printStackTrace();
			}
		}
		return doc;
	}
	
	public static ArrayList<?> getObjects(ObjectTypes objectType, String xmlString){
		
		if(objectType != null){
			Document doc = null;
			ArrayList<TankstellenPosition> atp = null;
			ArrayList<StationItem> asi = null;
			
			switch (objectType){
			case STATIONITEMLIST:
				asi = new ArrayList<StationItem>();

				Gson gson = new Gson();
				try {
					 
					JsonParser parser = new JsonParser();
				    JsonArray Jarray = parser.parse(xmlString).getAsJsonArray();
				    
				    for(JsonElement obj : Jarray )
				    {
						//convert the json string back to object
						StationItem si = gson.fromJson(obj, StationItem.class);
						if(si != null){
							asi.add(si);
						}
						System.out.println(obj);
				    }
			 
			 
				} catch (Exception e) {
					e.printStackTrace();
				}
				return asi;
//				break;
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
	
	public static FuelSort getSettingsFuelSort(Context context){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String strKraftstoff = prefs.getString("prefKraftstoff", "1");
        int intKraftstoff = 1;
        try {
			intKraftstoff = Integer.parseInt(strKraftstoff);
		} 
        catch (Exception e) {
		}
        
        if(intKraftstoff == 3){
        	return FuelSort.DIESEL;
        }
        else if(intKraftstoff == 2){
        	return FuelSort.E10;
        }
        else{
        	return FuelSort.E5;
        }
		
	}
	

	
	public static Day getDayFromDate(Date date){
		if(date == null){
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);		
		
		switch(cal.get(Calendar.DAY_OF_WEEK)){
		case Calendar.MONDAY:
			return Day.MONDAY;
		case Calendar.TUESDAY:
			return Day.TUESDAY;
		case Calendar.WEDNESDAY:
			return Day.WEDNESDAY;
		case Calendar.THURSDAY:
			return Day.THURSDAY;
		case Calendar.FRIDAY:
			return Day.FRIDAY;
		case Calendar.SATURDAY:
			return Day.SATURDAY;
		case Calendar.SUNDAY:
			return Day.SUNDAY;
		default:
			return Day.HOLLIDAY;
		}
	}
	
	public static class Sortation{
		public static final int SORTPRICE = 1;
		public static final int SORTDESTINATION = 2;
		public static final int SORTPRICETIMESTAMP = 3;
		// Sortierung in den Prefs
		//	1 - Preis
		//	2 - Entferung
		//	3 - Zeit
		public static int getSettingsSortaion(Context context){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	        int intSortation = prefs.getInt("prefSortation", -1);

	        if(intSortation == -1){
	        	intSortation = 1;

				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("prefSortation", intSortation);
				editor.commit();
	        }
	        
	        return intSortation;
		}
		public static void setSettingsSortation(Context context, int sortation){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = prefs.edit();
			sortation = (sortation == -1) ? 1 : sortation;
			editor.putInt("prefSortation", sortation);
			editor.commit();
		}
		
		public static int index = 0;
		public static ArrayList<StationItem> sortStationItemListOnPrice(ArrayList<StationItem> data, FuelSort fs){
			ArrayList<StationItem> al = new ArrayList<StationItem>();			
			StationItem[] siDataArray = data.toArray(new StationItem[data.size()]);
			
			index = 0;
			switch(fs){
			case DIESEL:
				index = 2;
				break;
			case E10:
				index = 1;
				break;
			default:
				index = 0;
				break;
			}
			
			Arrays.sort( siDataArray, new Comparator<StationItem>(){
		        public int compare( StationItem a, StationItem b ){
		        	double priceA = a.getPrices().get(index).getPrice();
		        	double priceB = b.getPrices().get(index).getPrice();
		        	
		        	return (int) (new Double(priceA)).compareTo(new Double(priceB));
//		            return ((int) (priceA - priceB) * 1000);
		        }
		    });
			index = 0;
			
			
			try {
				al.addAll((List<StationItem>) Arrays.asList(siDataArray));				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return al;
		}
		public static ArrayList<StationItem> sortStationItemListOnPriceTimestamp(ArrayList<StationItem> data, FuelSort fs){
			ArrayList<StationItem> al = new ArrayList<StationItem>();			
			StationItem[] siDataArray = data.toArray(new StationItem[data.size()]);
			
			index = 0;
			switch(fs){
			case DIESEL:
				index = 2;
				break;
			case E10:
				index = 1;
				break;
			default:
				index = 0;
				break;
			}
			
			Arrays.sort( siDataArray, new Comparator<StationItem>(){
		        public int compare( StationItem a, StationItem b ){
		        	Date dateA = a.getPrices().get(index).getTimestamp();
		        	Date dateB = b.getPrices().get(index).getTimestamp();
		        	
		        	return dateB.compareTo(dateA);		        	
//		            return ((int) (dateB.getTime() - dateA.getTime()));
		        }
		    });
			index = 0;
			
			
			try {
				al.addAll((List<StationItem>) Arrays.asList(siDataArray));				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return al;
		}
		public static ArrayList<StationItem> sortStationItemListOnDestination(ArrayList<StationItem> data){
			ArrayList<StationItem> al = new ArrayList<StationItem>();			
			StationItem[] siDataArray = data.toArray(new StationItem[data.size()]);

			Arrays.sort( siDataArray, new Comparator<StationItem>(){
		        public int compare( StationItem a, StationItem b ){
		        	double destinationA = a.getDestination();
		        	double destinationB = b.getDestination();
		        	
		        	return (int)(new Double(destinationA).compareTo(new Double(destinationB)));
//		            return ((int) (destinationA - destinationB) * 1000000);
		        }
		    });
			index = 0;
			
			
			try {
				al.addAll((List<StationItem>) Arrays.asList(siDataArray));				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return al;
		}
	}
}
