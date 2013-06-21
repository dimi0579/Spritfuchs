package com.dmitrij.doberstein.spritfuchs;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivityMenu extends Activity {
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	private final String SOAP_ACTION = "http://tempuri.org/CelsiusToFahrenheit";
	private final String METHOD_NAME = "CelsiusToFahrenheit";
	
	Button btnVergleich;
	Button btnInfo;
	Button btnConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_menu);
		
		btnVergleich = (Button)findViewById(R.id.btnVergleich);
		btnInfo = (Button)findViewById(R.id.btnInfo);
		btnConf = (Button)findViewById(R.id.btnConf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		return true;
	}

	public void onClick(View v){
		Intent intent = null;
		switch (v.getId()) {
			case R.id.btnVergleich:
				intent = new Intent(MainActivityMenu.this, VergleichActivity.class);
				startActivity(intent);
				break;
			case R.id.btnInfo:
				intent = new Intent(MainActivityMenu.this, InfoActivity.class);
				startActivity(intent);
		        break;
			case R.id.btnConf:
				intent = new Intent(MainActivityMenu.this, ConfActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
