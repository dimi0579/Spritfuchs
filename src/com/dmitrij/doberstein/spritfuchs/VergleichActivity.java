package com.dmitrij.doberstein.spritfuchs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
import com.dmitrij.doberstein.spritfuchs.dataclasses.CustomListAdapter;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;


public class VergleichActivity extends Activity implements  MyListener{
	private ListView lv1;
	LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vergleich);

        lv1 = (ListView) findViewById(R.id.list_tankstellen);
//        lv1.setAdapter(new CustomListAdapter(this, image_details));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	TankstellenPosition tp = (TankstellenPosition) lv1.getItemAtPosition(position);
            	Toast.makeText(VergleichActivity.this, "Selected: " + tp.getTankstelleName(), Toast.LENGTH_LONG).show();
            }
 
        });
        
     // gps location
     		try {
     			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//     			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, this.locationListener);

     		} catch (Exception e) {
     			// TODO: handle exception
     		}		
	}
	public void setListView(ArrayList<TankstellenPosition> data){
		lv1.setAdapter(new CustomListAdapter(this, data));
	}
	@SuppressWarnings("unused")
	private ArrayList<TankstellenPosition> getListData() {
        ArrayList<TankstellenPosition> results = new ArrayList<TankstellenPosition>();
        
        TankstellenPosition tp = new TankstellenPosition();
        tp.setTankstelleName("Test1");
        tp.setTankstelleStrasse("teststr.");
        tp.setTankstelleHausnr("1");
        tp.setTankstellePlz("11111");
        tp.setTankstelleOrt("testort 1");
        tp.setTankstelleEntfernung("111");
        tp.setTankstelleDiesel("1,30");
        tp.setTankstelleE5("1,50");
        tp.setTankstelleE10("1,60");
        results.add(tp);

        tp = new TankstellenPosition();
        tp.setTankstelleName("Test2");
        tp.setTankstelleStrasse("teststr.");
        tp.setTankstelleHausnr("2");
        tp.setTankstellePlz("22222");
        tp.setTankstelleOrt("testort 2");
        tp.setTankstelleEntfernung("222");
        tp.setTankstelleDiesel("1,33");
        tp.setTankstelleE5("1,53");
        tp.setTankstelleE10("1,63");
        results.add(tp);

        tp = new TankstellenPosition();
        tp.setTankstelleName("Test3");
        tp.setTankstelleStrasse("teststr.");
        tp.setTankstelleHausnr("3");
        tp.setTankstellePlz("333");
        tp.setTankstelleOrt("testort 3");
        tp.setTankstelleEntfernung("333");
        tp.setTankstelleDiesel("1,36");
        tp.setTankstelleE5("1,56");
        tp.setTankstelleE10("1,66");
        results.add(tp);
        
        return results;
	}
	
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
            locationManager.removeUpdates(networkLocationListener);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.GERMAN);
        	String datum = sdf.format(Calendar.getInstance().getTime());
        	
           String str = "New GPS location: \n"
                    + String.format("%9.6f", location.getLatitude()) + ", "
                    + String.format("%9.6f", location.getLongitude()) + "\n" + 
                    datum;
           
           Toast.makeText(VergleichActivity.this, str, Toast.LENGTH_LONG).show();
           
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
        	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);
        	String datum = sdf.format(Calendar.getInstance().getTime());
        	
            String str = "New network location: \n"
                    + String.format("%9.9f", location.getLatitude()) + ", "
                    + String.format("%9.9f", location.getLongitude()) + "\n" + 
                    datum;
            
            Toast.makeText(VergleichActivity.this, str, Toast.LENGTH_LONG).show();
            
            getData();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 1, networkLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, gpsLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(networkLocationListener);
        locationManager.removeUpdates(gpsLocationListener);
    }
    
    public void getData(){

		CheckWifiGpsConnectivity cc = new CheckWifiGpsConnectivity(this);
		final boolean check = cc.checkConnectivity();
		if(!check){
			this.finish();
		}
		AsyncCallWS task = new AsyncCallWS("", "", "", this);
		task.setListener(this);
		task.execute(); 
    }
	@Override
	public void setListDetailView(TankstellenPosition data) {
		// TODO Auto-generated method stub
		
	}
}