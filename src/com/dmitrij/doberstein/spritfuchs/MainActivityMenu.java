package com.dmitrij.doberstein.spritfuchs;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityMenu extends Activity {
	public static String kraftstoff = "";
	public static String umkreis = "";
	
	private static final int RESULT_SETTINGS = 1;	
	Button btnVergleich;
	Button btnInfo;
	Button btnConf;
	
	TextView textView;
	LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_menu);
		
		btnVergleich = (Button)findViewById(R.id.btnVergleich);
		btnInfo = (Button)findViewById(R.id.btnInfo);
		btnConf = (Button)findViewById(R.id.btnConf);
		
		textView = (TextView)findViewById(R.id.textView1);
		
//		showUserSettings();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		kraftstoff = sharedPref.getString("prefKraftstoff", "Diesel");
		umkreis = sharedPref.getString("prefUmkreis", "Diesel");
		
		umkreis += "";
		
		// gps location
		try {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, this.locationListener);

		} catch (Exception e) {
			// TODO: handle exception
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.settings, menu);
        return true;
	} 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 
        case R.id.menu_settings:
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
            break;
 
        }
 
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SETTINGS:
            showUserSettings();
            break;
        }
    }
    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
 
        StringBuilder builder = new StringBuilder();
 
        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));
 
        builder.append("\n Send report:"
                + sharedPrefs.getBoolean("prefSendReport", false));
 
        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
 
        TextView settingsTextView = (TextView) findViewById(R.id.textView1);
 
        settingsTextView.setText(builder.toString());
    }
    
	public void onClick(View v){
		Intent intent = null;
		switch (v.getId()) {
			case R.id.btnVergleich:
				CheckWifiGpsConnectivity c = new CheckWifiGpsConnectivity(this);
				boolean check = c.checkConnectivity();
				if(check){
					intent = new Intent(MainActivityMenu.this, VergleichActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.btnInfo:
				intent = new Intent(MainActivityMenu.this, InfoActivity.class);
				startActivity(intent);
		        break;
			case R.id.btnConf:
				intent = new Intent(MainActivityMenu.this, SettingsActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

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
        	
            textView.setText("New GPS location: \n"
                    + String.format("%9.6f", location.getLatitude()) + ", "
                    + String.format("%9.6f", location.getLongitude()) + "\n" + 
                    datum);

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
        	
            textView.setText("New network location: \n"
                    + String.format("%9.9f", location.getLatitude()) + ", "
                    + String.format("%9.9f", location.getLongitude()) + "\n" + 
                    datum);

        }
    };
}
