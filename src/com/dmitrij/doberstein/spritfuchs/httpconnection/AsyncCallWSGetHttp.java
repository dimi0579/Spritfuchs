package com.dmitrij.doberstein.spritfuchs.httpconnection;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.dmitrij.doberstein.spritfuchs.MyListener;
import com.dmitrij.doberstein.spritfuchs.VergleichActivity;
import com.dmitrij.doberstein.spritfuchs.VergleichActivityListDetail;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;
import com.dmitrij.doberstein.spritfuchs.utils.Utils;
import com.dmitrij.doberstein.spritfuchs.utils.Utils.ObjectTypes;

// For async SOAP connection
//AsyncCallWS task = new AsyncCallWS();
//task.execute(); 

public class AsyncCallWSGetHttp extends AsyncTask<Void, Void, Void> {
	private static final String VA = "com.dmitrij.doberstein.spritfuchs.VergleichActivity";
	private static final String VALD = "com.dmitrij.doberstein.spritfuchs.VergleichActivityListDetail";
	@SuppressWarnings("unused")
	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
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
	private String params = "";
	private Object myActivity;
	private ProgressDialog progDialog;
	
	private String returnString = "";
	private String xlat = "";
	private String xlong = "";
	private String umkreis = "";
	private String kraftstoff= "";
	
	private String tid = "";
	
	AsyncCallWSGetHttp(){
		
	}
	public AsyncCallWSGetHttp(String namespace, String url, String params, Object activity){
		this.namespace = namespace;
		this.url = url;
		this.params = params;
		this.myActivity = activity;
		
		if(VA.equalsIgnoreCase(myActivity.getClass().getName())){
			// parameter extrahieren bzw setzen**********************
			if(params != null && params.length() > 0){
				String[] temp = params.split(";");
				if(temp.length == 4){
					xlat = temp[0];
					xlong = temp[1];
					umkreis = temp[2];
					kraftstoff = temp[3];
				}
			}
			//********************************************************
    	}
    	else if(VALD.equalsIgnoreCase(myActivity.getClass().getName())){
    		// parameter extrahieren bzw setzen**********************
    		if(params != null && params.length() > 0){
    			tid = params;
//    			String[] temp = params.split(";");
//    			if(temp.length == 1){
//    				tid = temp[0];
//    			}
    		}
    		//********************************************************
    	}
		
		
		
		this.returnString = "";
	}
    @Override
    protected Void doInBackground(Void... params) {
        Log.i(TAG, "doInBackground");
        connect(params);
        return null;
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void onPostExecute(Void result) {
    	super.onPreExecute(); 
        try { 
        	String objectClassName = this.myActivity.getClass().getName();

        	if(VA.equalsIgnoreCase(objectClassName)){
				ArrayList<TankstellenPosition> tankstellen = new ArrayList<TankstellenPosition>();
        		if(this.returnString != null){
        			tankstellen = (ArrayList<TankstellenPosition>)Utils.getObjects(ObjectTypes.TANKSTELLENLISTE, this.returnString);
        		}
        		
        		listener.setListView(tankstellen);
        	}
        	else if(VALD.equalsIgnoreCase(objectClassName)){
        		ArrayList<TankstellenPosition> tankstellen = new ArrayList<TankstellenPosition>();
        		if(this.returnString != null){
        			tankstellen = (ArrayList<TankstellenPosition>)Utils.getObjects(ObjectTypes.TANKSTELLEDATEN, this.returnString);
        		}
        		
        		listener.setListDetailView(tankstellen);
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
      String URL = "";
	
      if(VA.equalsIgnoreCase(myActivity.getClass().getName())){
    	  URL = "http://spritfuchs.somee.com/WebService.aspx?Aktion=GETTANKSTELLEN&lat=" + 
        		  xlat + "&long=" + xlong + "&umkreis=" + umkreis + "&kraftstoff=" + kraftstoff;
	  }
	  else if(VALD.equalsIgnoreCase(myActivity.getClass().getName())){
		  URL = "http://spritfuchs.somee.com/WebService.aspx?Aktion=GETTANKSTELLENPOSITION&tid=" + this.tid;
		  URL = "http://192.168.178.20:8080/backend/rest/query";
	  }
      
      
      Log.i("httpget", URL);
		
      try
      {
          // Create Request to server and get response  
           HttpGet httpget = new HttpGet(URL);
           ResponseHandler<String> responseHandler = new BasicResponseHandler();
           returnString = Client.execute(httpget, responseHandler);
           
           // extrahiere xml aus dem response
           returnString = Utils.extracXml(returnString);
           
       }
     catch(Exception ex)
        {
    	 returnString = null;
    	 Log.i("error", ex.toString());
         }
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
	@SuppressWarnings("unused")
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