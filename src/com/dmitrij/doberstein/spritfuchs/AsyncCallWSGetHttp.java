package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

// For async SOAP connection
//AsyncCallWS task = new AsyncCallWS();
//task.execute(); 

public class AsyncCallWSGetHttp extends AsyncTask<Void, Void, Void> {
	private static final String VA = "VergleichActivity";
	private static final String VALD = "VergleichActivityListDetail";
	
	private MyListener listener;
	public void setListener(MyListener listener) {
        this.listener = listener;
    }
	private String TAG ="Vik";
	@SuppressWarnings("unused")
	private String namespace = "";
	@SuppressWarnings("unused")
	private String url = "";
	@SuppressWarnings("unused")
	private String methodename = "";
	private Object myActivity;
	private ProgressDialog progDialog;
	
	private String returnString = "";
	
	AsyncCallWSGetHttp(){
		
	}
	AsyncCallWSGetHttp(String namespace, String url, String methodename, Object activity){
		this.namespace = namespace;
		this.url = url;
		this.methodename = methodename;
		this.myActivity = activity;
		
		this.returnString = "";
	}
    @Override
    protected Void doInBackground(Void... params) {
        Log.i(TAG, "doInBackground");
        connect(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
    	super.onPreExecute(); 
        try { 
        	String objectClassName = this.myActivity.getClass().getName();

        	if(VA.equalsIgnoreCase(objectClassName)){
        		listener.setListView(getListData());
        	}
        	else if(VALD.equalsIgnoreCase(objectClassName)){
        		listener.setListDetailView(getListDetailData());
        	}
        	 
        	progDialog.dismiss(); 
        }  
        catch (Exception e) { 
             // TODO Auto-generated catch block 
             e.printStackTrace(); 
        }  
        Log.i(TAG, "onPostExecute");
    }

    @Override
    protected void onPreExecute() {
    	try { 
    		if(VA.equalsIgnoreCase(myActivity.getClass().getName())){
    			progDialog = ProgressDialog.show((VergleichActivity)myActivity, "Info", "Daten werden geladen!", true, true);
        	}
        	else if(VALD.equalsIgnoreCase(myActivity.getClass().getName())){
        		progDialog = ProgressDialog.show((VergleichActivityListDetail)myActivity, "Info", "Daten werden geladen!", true, true);
        	}
    		
    		
             
       }  
       catch (Exception e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
       }
        Log.i(TAG, "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    	super.onProgressUpdate(values);
        Log.i(TAG, "onProgressUpdate");
    }

	public void connect(Void... params){
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
           returnString = Client.execute(httpget, responseHandler);
       }
     catch(Exception ex)
        {
    	 Log.i("error", ex.toString());
         }
	}

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
	private TankstellenPosition getListDetailData() {
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
        
        return tp;
	}
}