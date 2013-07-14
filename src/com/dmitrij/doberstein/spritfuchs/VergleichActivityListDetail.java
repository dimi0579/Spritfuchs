package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class VergleichActivityListDetail extends Activity implements  MyListener {
	private TextView tvFirmenName;
	private TextView tvFirmenAnschrift;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_vergleich_activity_list_detail);
			
			tvFirmenName = (TextView)findViewById(R.id.tvDetailName);
			tvFirmenAnschrift = (TextView) findViewById(R.id.tvDetailAnschrift);
			
			tvFirmenAnschrift.setText(myGetHttp());
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
		AsyncCallWS_VergleichListDetail task = new AsyncCallWS_VergleichListDetail("", "", "", this);
		task.setListener(this);
		task.execute(); 
		

		AsyncCallWSGetHttp task1 = new AsyncCallWSGetHttp("", "", "", this);
		task1.setListener(this);
		task1.execute(); 
    }

	@Override
	public void setListView(ArrayList<TankstellenPosition> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListDetailView(TankstellenPosition data) {
		// TODO Auto-generated method stub
		tvFirmenName.setText(data.getTankstelleName());
		
		String anschrift = data.getTankstelleStrasse() + " " + data.getTankstelleHausnr() + 
				"\n" + data.getTankstellePlz() + " - " + data.getTankstelleOrt();
		tvFirmenAnschrift.setText(anschrift);
		
	}
	@Override
	protected void onStart(){
		super.onStart();

		this.getData();
	}
	
	public String myGetHttp(){
		String ret = "";
		
		// Create http cliient object to send request to server
        HttpClient Client = new DefaultHttpClient();
    
     // Create URL strinG
      String URL = "http://spritfuchs.somee.com/WebService.aspx?Aktion=GETTANKSTELLE";
      Log.i("httpget", URL);
		
      try
      {
          // Create Request to server and get response  
            HttpGet httpget = new HttpGet(URL);
           ResponseHandler<String> responseHandler = new BasicResponseHandler();
           ret = Client.execute(httpget, responseHandler);
       }
     catch(Exception ex)
        {
    	 Log.i("error", ex.toString());
         }
		return ret;
	}
}




