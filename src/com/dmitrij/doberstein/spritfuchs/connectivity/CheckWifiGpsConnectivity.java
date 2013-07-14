package com.dmitrij.doberstein.spritfuchs.connectivity;


import android.app.Activity;

public class CheckWifiGpsConnectivity implements ICheckConnecivity{

	private Activity activity;
	CheckWifiGpsConnectivity(){
		
	}
	public CheckWifiGpsConnectivity(Activity activity){
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
			
			// only for test
//			isWiFiEnabled = true;
			
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
