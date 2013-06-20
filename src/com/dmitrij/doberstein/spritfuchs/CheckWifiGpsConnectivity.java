package com.dmitrij.doberstein.spritfuchs;

import android.app.Activity;

public class CheckWifiGpsConnectivity implements ICheckConnecivity{

	private Activity activity;
	CheckWifiGpsConnectivity(){
		
	}
	CheckWifiGpsConnectivity(Activity activity){
		this.activity = activity;
	}
	@Override
	public boolean checkConnectivity() {
		// TODO Auto-generated method stub
		try {
			CheckWiFiConnectivity cc = new CheckWiFiConnectivity(activity);
			CheckGPSConnectivity cgc = new CheckGPSConnectivity(activity);
			
			boolean isWiFiEnabled = cc.checkConnectivity();
			boolean isGPSEnabled = cgc.checkConnectivity();
				
			if(!isWiFiEnabled){
				cc.showDialog();
				return false;
			}
			else if(!isGPSEnabled){
				cgc.showDialog();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	@Override
	public void showDialog() {
		// TODO Auto-generated method stub
		
	}
}