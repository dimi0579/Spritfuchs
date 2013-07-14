package com.dmitrij.doberstein.spritfuchs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class CheckWiFiConnectivity implements ICheckConnecivity{
	ConnectivityManager conMan;
	Activity activity;
	
	CheckWiFiConnectivity(Activity activity){
		this.conMan = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		this.activity = activity;
	}
	
	private State getMobileState(){
		if(conMan != null){
			return conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		}
		return null;
	}
	
	private State getWifiState(){
		if(conMan != null){
			return conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		}
		return null;
	}
	
	@Override
	public boolean checkConnectivity() {
		if(conMan != null){
			State mobile = this.getMobileState();
			State wifi = this.getWifiState();
			
			if (mobile != State.CONNECTED && mobile != State.CONNECTING && wifi != State.CONNECTED && wifi != State.CONNECTING) {
//				showConnecitivityDisabledAlertToUser();
				return false;
			}
			else{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void showDialog(){
//	public void showConnecitivityDisabledAlertToUser(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setMessage("WiFi is disabled in your device. Would you like to enable it?");
		alertDialogBuilder.setCancelable(false);
		
		alertDialogBuilder.setPositiveButton("WiFi", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					Intent callWiFiSettingIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
					activity.startActivity(callWiFiSettingIntent);
				}
			});
			//Goto Settings Page To Enable GPS
		alertDialogBuilder.setNeutralButton("Mobile", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						Intent callMobileSettingIntent = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
						activity.startActivity(callMobileSettingIntent);
					}
			});
		
		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				dialog.cancel();
				activity.finish();
			}
		});
		
		AlertDialog alert = alertDialogBuilder.create();
		
		alert.show();
	}
}
