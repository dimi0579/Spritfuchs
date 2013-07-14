package com.dmitrij.doberstein.spritfuchs.connectivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

public class CheckGPSConnectivity implements ICheckConnecivity{
	LocationManager locMan;
	Activity activity;
	
	CheckGPSConnectivity(Activity activity){
		this.activity = activity;
		this.locMan = (LocationManager) this.activity.getSystemService(Activity.LOCATION_SERVICE);
	}
	
	private boolean isGPSEnabled(){
		if(locMan != null){
			return locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		return false;
	}
	
//	@Override
	public boolean checkConnectivity(){
		if(locMan != null){			
			if (!isGPSEnabled()) {
//				showGPSConnecitivityDisabledAlertToUser();
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
//	public void showGPSConnecitivityDisabledAlertToUser(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?");
		alertDialogBuilder.setCancelable(false);
		
		alertDialogBuilder.setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){
					Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					activity.startActivity(callGPSSettingIntent);
				}
			});
		
		alertDialogBuilder.setNegativeButton("Cancel\n", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				dialog.cancel();
				activity.finish();
			}
		});
		
		AlertDialog alert = alertDialogBuilder.create();
		
		alert.show();
	}
}
