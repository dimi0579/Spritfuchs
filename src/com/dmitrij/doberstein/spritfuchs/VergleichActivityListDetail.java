package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;


/***
 * 
 * TODO - den TID-parameter aus der VergleichActivity übergeben...
 *  + Default-ImageView soll transparent oder leer sein...
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VergleichActivityListDetail extends Activity implements  MyListener {
	private TextView tvTankstelleDetail;
	private TextView tvOZTag;
	private TextView tvOZVonBis;
	private String tid = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_vergleich_activity_list_detail);


			ActionBar ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			
			
			tvTankstelleDetail = (TextView) findViewById(R.id.tvTankstelleDetail);
			tvOZTag = (TextView) findViewById(R.id.tvOZTag);
			tvOZVonBis = (TextView) findViewById(R.id.tvOZVonBis);
			
			tid = getIntent().getStringExtra("tid");
			if(tid == null || tid.isEmpty()){
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
		tvTankstelleDetail.setText(anschrift);
		
		tvOZTag.setText("Montag" +
				"\nDienstag" + 
				"\nMittwoch" + 
				"\nDonnerstag" + 
				"\nFreitag" + 
				"\nSamstag" + 
				"\nSonntag" + 
				"\n und Feiertage");
		tvOZVonBis.setText("00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\n00:00 - 23:59\ngeschlossen");
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

		this.getData();
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
}




