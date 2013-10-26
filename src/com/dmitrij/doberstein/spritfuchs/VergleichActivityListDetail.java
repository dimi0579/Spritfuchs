package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
import com.dmitrij.doberstein.spritfuchs.dataclasses.MyExpandableAdapter;
import com.dmitrij.doberstein.spritfuchs.dataclasses.NavigateData;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Openingtime;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Price;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationBrand;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationItem;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;


/***
 * 
 * TODO - den TID-parameter aus der VergleichActivity übergeben...
 *  + Default-ImageView soll transparent oder leer sein...
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VergleichActivityListDetail extends Activity implements  MyListener {
	private ImageView ivTankstelleMarke;
	private TextView tvTankstelleDetail;
	private TextView tvFuelSorts;
	private TextView tvFuelSortsPrices;
	private TextView tvTagName;
	private TextView tvTagZeit;
	private ImageButton ibDetailNavigation;
	
	private String tid = "";
	
	private StationItem si;
	
	//***
	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	//***
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_vergleich_activity_list_detail);
			
			ActionBar ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);

			ivTankstelleMarke = (ImageView)findViewById(R.id.ivTankstelleMarke);
			tvTankstelleDetail = (TextView) findViewById(R.id.tvTankstelleDetail);
			tvFuelSorts = (TextView) findViewById(R.id.tvFuelSorts);
			tvFuelSortsPrices = (TextView) findViewById(R.id.tvFuelSortsPrices);
			tvTagName = (TextView)findViewById(R.id.tvTagName);
			tvTagZeit = (TextView)findViewById(R.id.tvTagZeit);

			ibDetailNavigation = (ImageButton)findViewById(R.id.ibDetailNavigation);

			si = (StationItem)this.getIntent().getSerializableExtra("stationitem");
			if(si == null){
				new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("Actually are no data available!")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            VergleichActivityListDetail.this.finish();
			        }
			     })
			     .show();
			}
			else{
				ivTankstelleMarke.setImageResource(Utils.getTankstellenImage(si.getMark()));
				// details
				Spanned details = Html.fromHtml("<b><big>" + si.getName() + "</big></b><br />" + 
		                si.getStreet() + " " + si.getHouseNumber() + "<br />" +
						si.getCityCode() + " " + si.getCity());
				tvTankstelleDetail.setText(details);
				// preise
				String sorte = "";
				String sortenPreis = "";
				List<Price> prices = si.getPrices();
				for(int i = 0; i < prices.size(); i++){
					Price price = prices.get(i);
					sorte += price.getFuel().name() + "\n";
					sortenPreis += "€ " + price.getPrice() + "\n";
				}
				tvFuelSorts.setText(sorte);
				tvFuelSortsPrices.setText(sortenPreis);
				// oeffnungszeiten
				String tagName = "";
				String tagZeit = "";
				List<Openingtime> ops = si.getOpeningTimes();
				for(int i = 0; i < ops.size(); i++){
					Openingtime op = ops.get(i);
					tagName += Utils.getTagName(op.getDay()) + "\n";
					tagZeit += op.getFrom() + " - " + op.getTill() + "\n";
				}
				tvTagName.setText(tagName);
				tvTagZeit.setText(tagZeit);
				// navigationsbutton
				NavigateData nd = new NavigateData(0.0, 0.0, 
		        		si.getLatitude(), si.getLongtitude());
				ibDetailNavigation.setTag(nd);
			}
			
			//***
//			ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.elvZeiten);
//			expandableList.setDividerHeight(2);
//			expandableList.setGroupIndicator(null);
//			expandableList.setClickable(true);
//
//			setGroupParents();
//			setChildData();
//
//			MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);
//
//			adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
//			expandableList.setAdapter(adapter);
//			expandableList.setOnChildClickListener(new OnChildClickListener() {
//				
//				@Override
//				public boolean onChildClick(ExpandableListView parent, View v,
//						int groupPosition, int childPosition, long id) {					
//					return false;
//				}
//			});
			//***
			
//			tid = getIntent().getStringExtra("tid");
//			if(tid == null || tid.isEmpty()){
//				new AlertDialog.Builder(this)
//			    .setTitle("Error")
//			    .setMessage("Actually are no data available!")
//			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			        public void onClick(DialogInterface dialog, int which) { 
//			            VergleichActivityListDetail.this.finish();
//			        }
//			     })
//			     .show();
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vergleich_activity_list_detail, menu);
		return true;
	}

	public void getData(){

		CheckWifiGpsConnectivity cc = new CheckWifiGpsConnectivity(this);
		final boolean check = cc.checkConnectivity();
		if(!check){
			this.finish();
		}
		
		String params = this.tid;
		
		AsyncCallWSGetHttp task1 = new AsyncCallWSGetHttp("", "", params, this);
		task1.setListener(this);
		task1.execute(); 
    }

	@Override
	public void setListView(ArrayList<TankstellenPosition> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListDetailView(ArrayList<TankstellenPosition> data) {
		// only for test
		String anschrift = "Testtankstelle\nTeststrass 8\n\n88990 Testort";
//		tvTankstelleDetail.setText(anschrift);
//		
//		tvOZTag.setText("Montag" +
//				"\nDienstag" + 
//				"\nMittwoch" + 
//				"\nDonnerstag" + 
//				"\nFreitag" + 
//				"\nSamstag" + 
//				"\nSonntag" + 
//				"\n und Feiertage");
//		tvOZVonBis.setText("00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\ngeschlossen");
		
		
		
		//**********************************************************************
//		if (data != null && data.size() > 0) {
//			String anschrift = data.get(0).getTankstelleName() + "\n"
//					+ data.get(0).getTankstelleStrasse() + " "
//					+ data.get(0).getTankstelleHausnr() + "\n\n"
//					+ data.get(0).getTankstellePlz() + " - " + data.get(0).getTankstelleOrt();
//			tvTankstelleDetail.setText(anschrift);
//			
//			tvOZTag.setText(data.get(0).getTankstelleZeiten().getTagString());
//			tvOZVonBis.setText(data.get(0).getTankstelleZeiten().getVonBisString());
//		}	
//		else{
//			new AlertDialog.Builder(this)
//		    .setTitle("Error")
//		    .setMessage("Actually are no data available!")
//		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//		        public void onClick(DialogInterface dialog, int which) { 
//		            VergleichActivityListDetail.this.finish();
//		        }
//		     })
//		     .show();
//		}
	}
	
	@Override
	protected void onStart(){
		super.onStart();

//		this.getData();
	}

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	onBackPressed();
	    	break;
	    default:
	      break;
	    }

	    return true;
	  } 
//	public String myGetHttp(){
//		String ret = "";
//		
//		// Create http cliient object to send request to server
//        HttpClient Client = new DefaultHttpClient();
//    
//     // Create URL strinG
//      String URL = "http://spritfuchs.somee.com/WebService.aspx?Aktion=GETTANKSTELLE";
//      Log.i("httpget", URL);
//		
//      try
//      {
//          // Create Request to server and get response  
//            HttpGet httpget = new HttpGet(URL);
//           ResponseHandler<String> responseHandler = new BasicResponseHandler();
//           ret = Client.execute(httpget, responseHandler);
//       }
//     catch(Exception ex)
//        {
//    	 Log.i("error", ex.toString());
//         }
//		return ret;
//	}
	
	//***
	public void setGroupParents() {
		parentItems.add("Öffnungszeiten");
//		parentItems.add("Core Java");
//		parentItems.add("Desktop Java");
//		parentItems.add("Enterprise Java");
	}

	public void setChildData() {
		// Android
		ArrayList<String> child = new ArrayList<String>();
		
		List<Openingtime> li = si.getOpeningTimes();
		if(li != null){
			for(int i = 0; i < li.size(); i++){
				Openingtime ot = li.get(i);
				if(ot != null){
					String temp = ot.getDay() + "\t" + ot.getFrom() + " - " + ot.getTill();
					child.add(temp);
				}
			}
		}
//		child.add("Core");
//		child.add("Games");
		childItems.add(child);

		// Core Java
//		child = new ArrayList<String>();
//		child.add("Apache");
//		child.add("Applet");
//		child.add("AspectJ");
//		child.add("Beans");
//		child.add("Crypto");
//		childItems.add(child);

	}
	//***
	public void navigateOnClickHandler(View v) {
        Location loc = null;
        if(MainActivityMenu.locationManager.getProvider(LocationManager.GPS_PROVIDER) != null){
        	loc = MainActivityMenu.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else if(MainActivityMenu.locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null){
        	loc = MainActivityMenu.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        
        NavigateData nd = (NavigateData) v.getTag();
        
    	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
    			Uri.parse("http://maps.google.com/maps?saddr=" + loc.getLatitude() + 
    					","+ loc.getLongitude() + "&daddr=" + nd.getDestLatitude() + 
    					"," + nd.getDestLongtitude()));
    	startActivity(intent);
	}
}




