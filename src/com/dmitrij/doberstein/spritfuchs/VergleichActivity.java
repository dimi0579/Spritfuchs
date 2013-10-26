package com.dmitrij.doberstein.spritfuchs;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Calendar;
//import java.util.Locale;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toast;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
//import com.dmitrij.doberstein.spritfuchs.dataclasses.CustomListAdapter;
import com.dmitrij.doberstein.spritfuchs.dataclasses.CustomListAdapterNew;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Day;
import com.dmitrij.doberstein.spritfuchs.dataclasses.FuelSort;
import com.dmitrij.doberstein.spritfuchs.dataclasses.NavigateData;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Openingtime;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Price;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationBrand;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationItem;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VergleichActivity extends Activity implements  MyListener{
	private static final int RESULT_SETTINGS = 1;	
	
	private ListView lv1;
//	LocationManager locationManager;
	
	private String xlat = "";
	private String xlong = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vergleich);


		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		
        lv1 = (ListView) findViewById(R.id.list_tankstellen);
//        lv1.setAdapter(new CustomListAdapter(this, image_details));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//            	TankstellenPosition tp = (TankstellenPosition)a.getItemAtPosition(position);
            	StationItem tp = (StationItem)a.getItemAtPosition(position);
            	if(tp != null){
	            	Intent intent = new Intent(VergleichActivity.this, VergleichActivityListDetail.class);
	            	intent.putExtra("stationitem", tp);

					startActivity(intent);
            	}
            }
 
        });
        
     // gps location
 		try {
 			MainActivityMenu.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//     			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, this.locationListener);

 		} catch (Exception e) {
 			System.out.println("test");
 		}		
	}
		
	public void setListView(ArrayList<TankstellenPosition> data){
		if(data.size() == 0){
			lv1.setAdapter(new CustomListAdapterNew(this, getListData())); // -- new Adaprter test
//			lv1.setAdapter(new CustomListAdapter(this, getListData())); // -- old Adaprter test
//			lv1.setAdapter(new CustomListAdapter(this, data)); -- original
		}
		else{
			new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Actually are no data available!")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            VergleichActivity.this.finish();
		        }
		     })
		     .show();
		}
	}
	
	private ArrayList<StationItem> getListData(){
		ArrayList<StationItem> ret = new ArrayList<StationItem>();
		
		List<Openingtime> openingTimes = new ArrayList<Openingtime>();
		Openingtime ptMO = new Openingtime(Day.MONDAY, "00:00", "23:59");
		openingTimes.add(ptMO);
		Openingtime ptDI = new Openingtime(Day.TUESDAY, "00:00", "23:59");
		openingTimes.add(ptDI);
		Openingtime ptMI = new Openingtime(Day.WEDNESDAY, "00:00", "23:59");
		openingTimes.add(ptMI);
		Openingtime ptDO = new Openingtime(Day.THURSDAY, "00:00", "23:59");
		openingTimes.add(ptDO);
		Openingtime ptFR = new Openingtime(Day.FRIDAY, "00:00", "23:59");
		openingTimes.add(ptFR);
		Openingtime ptSA = new Openingtime(Day.SATURDAY, "00:00", "23:59");
		openingTimes.add(ptSA);
		Openingtime ptSO = new Openingtime(Day.SUNDAY, "00:00", "23:59");
		openingTimes.add(ptSO);
		Openingtime ptFE = new Openingtime(Day.HOLLIDAY, "00:00", "23:59");
		openingTimes.add(ptFE);
		
		List<Price> prices = new ArrayList<Price>();
		Price price = new Price(FuelSort.E5, 1.345, new Date().getTime());
		prices.add(price);
		price = new Price(FuelSort.E10, 1.345, new Date().getTime());
		prices.add(price);
		price = new Price(FuelSort.DIESEL, 1.345, new Date().getTime());
		prices.add(price);
		
		StationItem si = new StationItem(openingTimes, "1", "AGIP Überlingen", StationBrand.AGIP, 
				"NussdorferStr.", "1", "Überlingen", 88662, 47.766175, 9.170277, 0.5, prices);
		ret.add(si);
		
		si = new StationItem(openingTimes, "1", "ARAL Meersburg", StationBrand.ARAL, 
				"NussdorferStr.", "1", "Meersburg", 88662, 47.696957, 9.272724, (double)12, prices);
		ret.add(si);
		
		si = new StationItem(openingTimes, "1", "AVIA Stockach", StationBrand.AVIA, 
				"NussdorferStr.", "1", "Stockach", 88662, 47.853164, 9.009153, (double)9.5, prices);
		ret.add(si);
		
		si = new StationItem(openingTimes, "1", "BP Singen", StationBrand.BP, 
				"NussdorferStr.", "1", "Singen", 88662, 47.764064, 8.853396, (double)23.6, prices);
		ret.add(si);
		
		si = new StationItem(openingTimes, "1", "TEST Konstanz", StationBrand.DEFAULT, 
				"NussdorferStr.", "1", "Konstanz", 88662, 47.677950, 9.173238, (double)32.2, prices);
		ret.add(si);

//		StationItem(List<Openingtime> openingTimes, String id, String name,
//				String mark, String street, int houseNumber, String city,
//				int cityCode, double latitude, double longtitude, long destination,
//				Price price)
		return ret;
	}
	@SuppressWarnings("unused")
//	private ArrayList<TankstellenPosition> getListData() {
//        ArrayList<TankstellenPosition> results = new ArrayList<TankstellenPosition>();
//        
//        TankstellenPosition tp = new TankstellenPosition();
//        tp.setTankstelleName("Test1");
//        tp.setTankstelleStrasse("teststr.");
//        tp.setTankstelleHausnr("1");
//        tp.setTankstellePlz("11111");
//        tp.setTankstelleOrt("testort 1");
//        tp.setTankstelleEntfernung("111");
//        tp.setTankstelleDiesel("1,30");
//        tp.setTankstelleE5("1,50");
//        tp.setTankstelleE10("1,60");
//        results.add(tp);
//
//        tp = new TankstellenPosition();
//        tp.setTankstelleName("Test2");
//        tp.setTankstelleStrasse("teststr.");
//        tp.setTankstelleHausnr("2");
//        tp.setTankstellePlz("22222");
//        tp.setTankstelleOrt("testort 2");
//        tp.setTankstelleEntfernung("222");
//        tp.setTankstelleDiesel("1,33");
//        tp.setTankstelleE5("1,53");
//        tp.setTankstelleE10("1,63");
//        results.add(tp);
//
//        tp = new TankstellenPosition();
//        tp.setTankstelleName("Test3");
//        tp.setTankstelleStrasse("teststr.");
//        tp.setTankstelleHausnr("3");
//        tp.setTankstellePlz("333");
//        tp.setTankstelleOrt("testort 3");
//        tp.setTankstelleEntfernung("333");
//        tp.setTankstelleDiesel("1,36");
//        tp.setTankstelleE5("1,56");
//        tp.setTankstelleE10("1,66");
//        results.add(tp);
//        
//        return results;
//	}
	
	@Override
	protected void onStart(){
		super.onStart();
        //*****************************************************************************************************
		// Create a progress bar to display while the list loads
//		progressBar = new ProgressBar(this);
//		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		progressBar.setIndeterminate(true);
//		lv1.setEmptyView(progressBar);
//		
//		// Must add the progress bar to the root of the layout
//		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//		root.addView(progressBar);
		//******************************************************************************************************
//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 45000, 50, networkLocationListener);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 45000, 50, gpsLocationListener);
        
        Location loc = null;
        if(MainActivityMenu.locationManager.getProvider(LocationManager.GPS_PROVIDER) != null){
        	loc = MainActivityMenu.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	xlat = String.format("%9.6f", loc.getLatitude());
            xlong = String.format("%9.6f", loc.getLongitude());
        }
        else if(MainActivityMenu.locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null){
        	loc = MainActivityMenu.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        	xlat = String.format("%9.6f", loc.getLatitude());
            xlong = String.format("%9.6f", loc.getLongitude());
        }
        else{
        	return;
        }
		getData();
	}
	private final LocationListener gpsLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            case LocationProvider.AVAILABLE:
//                textView.setText(textView.getText().toString() + "GPS available again\n");
                break;
            case LocationProvider.OUT_OF_SERVICE:
//                textView.setText(textView.getText().toString() + "GPS out of service\n");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                textView.setText(textView.getText().toString() + "GPS temporarily unavailable\n");
                break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
//            textView.setText(textView.getText().toString() + "GPS Provider Enabled\n");
        }

        @Override
        public void onProviderDisabled(String provider) {
//            textView.setText(textView.getText().toString() + "GPS Provider Disabled\n");
        }

        @Override
        public void onLocationChanged(Location location) {
        	MainActivityMenu.locationManager.removeUpdates(networkLocationListener);

//            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.GERMAN);
//        	String datum = sdf.format(Calendar.getInstance().getTime());
//        	
//           String str = "New GPS location: \n"
//                    + String.format("%9.6f", location.getLatitude()) + ", "
//                    + String.format("%9.6f", location.getLongitude()) + "\n" + 
//                    datum;
//           
//           Toast.makeText(VergleichActivity.this, str, Toast.LENGTH_LONG).show();
           
           xlat = String.format("%9.6f", location.getLatitude());
           xlong = String.format("%9.6f", location.getLongitude());
           getData();
        }
    };

    private final LocationListener networkLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            case LocationProvider.AVAILABLE:
//                textView.setText(textView.getText().toString() + "Network location available again\n");
                break;
            case LocationProvider.OUT_OF_SERVICE:
//                textView.setText(textView.getText().toString() + "Network location out of service\n");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                textView.setText(textView.getText().toString() + "Network location temporarily unavailable\n");
                break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
//            textView.setText(textView.getText().toString() + "Network Provider Enabled\n");
        }

        @Override
        public void onProviderDisabled(String provider) {
//            textView.setText(textView.getText().toString() + "Network Provider Disabled\n");
        }

        @Override
        public void onLocationChanged(Location location) {        	
//        	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);
//        	String datum = sdf.format(Calendar.getInstance().getTime());
//        	
//            String str = "New network location: \n"
//                    + String.format("%9.9f", location.getLatitude()) + ", "
//                    + String.format("%9.9f", location.getLongitude()) + "\n" + 
//                    datum;
//            
//            Toast.makeText(VergleichActivity.this, str, Toast.LENGTH_LONG).show();

            xlat = String.format("%9.6f", location.getLatitude());
            xlong = String.format("%9.6f", location.getLongitude());
            getData();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        MainActivityMenu.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 45000, 50, networkLocationListener);
        MainActivityMenu.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 45000, 50, gpsLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MainActivityMenu.locationManager.removeUpdates(networkLocationListener);
        MainActivityMenu.locationManager.removeUpdates(gpsLocationListener);
    }
    
    public void getData(){

		CheckWifiGpsConnectivity cc = new CheckWifiGpsConnectivity(this);
		final boolean check = cc.checkConnectivity();
		if(!check){
			this.finish();
		}
//		AsyncCallWS task = new AsyncCallWS("", "", "", this);
//		task.setListener(this);
//		task.execute(); 
		
		// TODO hier sollen nun die parameter übergeben werden
		// xlat;xlong;umkreis
		String params = "";
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		// get Umkreis -> default - 5 km
		String tempUmkreis = prefs.getString("prefUmkreis", "5");
		
		// get Kraftstoff -> default - Diesel (1)
		String tempKraftstoff = prefs.getString("prefKraftstoff", "1");
		
		xlat = xlat.replace(",", ".").trim();
		xlong = xlong.replace(",", ".").trim();
		params = xlat + ";" + xlong + ";" + tempUmkreis + ";" + tempKraftstoff;
		
		
		AsyncCallWSGetHttp task = new AsyncCallWSGetHttp("", "", params, this);
		task.setListener(this);
		task.execute();
    }
	@Override
	public void setListDetailView(ArrayList<TankstellenPosition> data) {
		// TODO Auto-generated method stub
		
	}
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.settings, menu);
	    return true;
	  } 
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_settings:
	    	Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
	      break;
	    case android.R.id.home:
	    	onBackPressed();
	    	break;
	    default:
	      break;
	    }

	    return true;
	  } 
	
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