package com.dmitrij.doberstein.spritfuchs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dmitrij.doberstein.spritfuchs.connectivity.CheckWifiGpsConnectivity;

public class MainActivity extends Activity {
//	private static final int RESULT_SETTINGS = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		showUserSettings();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
////		getMenuInflater().inflate(R.menu.settings, menu);
//        return true;
//	} 
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
// 
//        case R.id.menu_settings:
//            Intent i = new Intent(this, SettingsActivity.class);
//            startActivityForResult(i, RESULT_SETTINGS);
//            break;
// 
//        }
// 
//        return true;
//    }
// 
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
// 
//        switch (requestCode) {
//        case RESULT_SETTINGS:
//            showUserSettings();
//            break;
// 
//        }
// 
//    }
// 
//    private void showUserSettings() {
//        SharedPreferences sharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(this);
// 
//        StringBuilder builder = new StringBuilder();
// 
//        builder.append("\n Username: "
//                + sharedPrefs.getString("prefUsername", "NULL"));
// 
//        builder.append("\n Send report:"
//                + sharedPrefs.getBoolean("prefSendReport", false));
// 
//        builder.append("\n Sync Frequency: "
//                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
// 
//        TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
// 
//        settingsTextView.setText(builder.toString());
//    }
	

	
	@Override
	protected void onStart(){
		super.onStart();
		
		CheckWifiGpsConnectivity c = new CheckWifiGpsConnectivity(this);
		final boolean check = c.checkConnectivity();
		
		Thread mythread = new Thread() {
            public void run() {
                try {
//                    while (splashActive && ms < splashTime) {
                        while(!check)
                        	sleep(1000);
                        
                    	sleep(5000);
//                    }
                } catch(Exception e) {}
                finally {
                    Intent intent = new Intent(MainActivity.this, MainActivityMenu.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        };
        mythread.start();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	protected void onRestart(){
		super.onRestart();
	}

}
