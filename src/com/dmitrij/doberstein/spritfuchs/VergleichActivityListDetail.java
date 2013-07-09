package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class VergleichActivityListDetail extends Activity implements  MyListener {
	TextView tvFirmenName;
	TextView tvFirmenAnschrift;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vergleich_activity_list_detail);
		
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
}
