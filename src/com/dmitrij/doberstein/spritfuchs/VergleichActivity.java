package com.dmitrij.doberstein.spritfuchs;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class VergleichActivity extends TabActivity {
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	private final String SOAP_ACTION = "http://tempuri.org/CelsiusToFahrenheit";
	private final String METHOD_NAME = "CelsiusToFahrenheit";
	
	ProgressBar pbVergleich;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vergleich);
		
		
		TabHost tabHost = getTabHost();
        
        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Photos");
        // setting Title and Icon for the Tab
//        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_photos_tab));
        Intent photosIntent = new Intent(this, VergleichListeActivity.class);
        photospec.setContent(photosIntent);
         
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Songs");       
//        songspec.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_songs_tab));
        Intent songsIntent = new Intent(this, VergleichMapActivity.class);
        songspec.setContent(songsIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
		
		
		
		
		
		
//		pbVergleich = (ProgressBar)findViewById(R.id.pbVergleich);
//		pbVergleich.setVisibility(View.VISIBLE);
//		
//		tv = (TextView) findViewById(R.id.tvTextview);
		
		//Create instance for AsyncCallWS
        AsyncCallWS task = new AsyncCallWS();
        //Call execute
        task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vergleich, menu);
		return true;
	}

	// Private Class AsyncCallWS
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		private String TAG = "AsyncCallWS";
	    @Override
	    protected Void doInBackground(String... params) {
	        Log.i(TAG, "doInBackground");
	        getData(5.0);
	        return null;
	    }
	
	    @Override
	    protected void onPostExecute(Void result) {
	        Log.i(TAG, "onPostExecute");
//	        tv.setText(fahren + "° F");
//	        pbVergleich.setVisibility(View.GONE);
	        
	    }
	
	    @Override
	    protected void onPreExecute() {
	        Log.i(TAG, "onPreExecute");
//	        tv.setText("Calculating...");
	    }
	
	    @Override
	    protected void onProgressUpdate(Void... values) {
	        Log.i(TAG, "onProgressUpdate");
	    }
	
	}

	public void getData(double celsius) {
	    //Create request
	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	    //Property which holds input parameters
	    PropertyInfo celsiusPI = new PropertyInfo();
	    //Set Name
	    celsiusPI.setName("Celsius");
	    //Set Value
	    celsiusPI.setValue(celsius);
	    //Set dataType
	    celsiusPI.setType(double.class);
	    //Add the property to request object
	    request.addProperty(celsiusPI);
	    //Create envelope
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    envelope.dotNet = true;
	    //Set output SOAP object
	    envelope.setOutputSoapObject(request);
	    //Create HTTP call object
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	 
	    try {
	        //Invole web service
	        androidHttpTransport.call(SOAP_ACTION, envelope);
	        //Get the response
	        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	        //Assign it to fahren static variable
	        String test = response.toString();
	        tv.setText(test);
	        pbVergleich.setVisibility(View.GONE);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	        pbVergleich.setVisibility(View.GONE);
	    }
	}
}
