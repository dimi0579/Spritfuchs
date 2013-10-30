package com.dmitrij.doberstein.spritfuchs.httpconnection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.dmitrij.doberstein.spritfuchs.VergleichActivityListDetail;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;
import com.dmitrij.doberstein.spritfuchs.listeners.MyListener;

// For async SOAP connection
//AsyncCallWS task = new AsyncCallWS();
//task.execute(); 

public class AsyncCallWS_VergleichListDetail extends AsyncTask<Void, Void, Void> {

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
	private VergleichActivityListDetail myActivity;
	private ProgressDialog progDialog;
	
	AsyncCallWS_VergleichListDetail(){
		
	}
	AsyncCallWS_VergleichListDetail(String namespace, String url, String methodename, VergleichActivityListDetail activity){
		this.namespace = namespace;
		this.url = url;
		this.methodename = methodename;
		this.myActivity = activity;
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
        	listener.setListDetailView(null);
        	  progDialog.dismiss(); 
        }  
        catch (Exception e) { 
             e.printStackTrace(); 
        }  
        Log.i(TAG, "onPostExecute");
    }

    @Override
    protected void onPreExecute() {
    	try { 
            progDialog = ProgressDialog.show(myActivity, "Info", "Daten werden geladen!", true, true); 
       }  
       catch (Exception e) { 
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
//	      WebResponse webResponse = new WebResponse();
	      final String NAMESPACE = "http://tempuri.org/";
	      final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	      final String SOAP_ACTION = "http://tempuri.org/CelsiusToFahrenheit";
	      final String METHOD_NAME = "CelsiusToFahrenheit";
	      
//	      final String ERROR_MSG = "ErrorMessage";
//	      final String ERROR_SQL = "SqlErrorMessage";
//	      final String DATA_SEG = "Data";
	   

	    //Create request
//	      SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	      SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	      //Property which holds input parameters
	      PropertyInfo celsiusPI = new PropertyInfo();
	      //Set Name
	      celsiusPI.setName("Celsius");
	      //Set Value
	      celsiusPI.setValue("23");
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
//	      HttpTransportSE androidHttpTransport = new HttpTransportSE("http://spritfuchs.somee.com/WebService.aspx?Aktion=GETTANKSTELLE");
	   
	      try {
	          //Invole web service
	          androidHttpTransport.call(SOAP_ACTION, envelope);
	          //Get the response
	          @SuppressWarnings("unused")
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	           } catch (Exception e) { 
//	                publishProgress("Product search failed."); 
                   
                Log.d(SOAP_ACTION, e.getMessage()); 
//	                webResponse.setErrorState(true); 
//	                webResponse.setErrorMessage(e.getMessage()); 
//	                   
//	                return webResponse; 
	           } 
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