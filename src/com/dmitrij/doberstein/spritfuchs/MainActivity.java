package com.dmitrij.doberstein.spritfuchs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		setContentView(com.dmitrij.doberstein.spritfuchs.R.layout.activity_main);
		
//		Thread mythread = new Thread() {
//            public void run() {
//                try {
//                    while (splashActive && ms < splashTime) {
//                        if(!paused)
//                            ms=ms+100;
//                        sleep(100);
//                    }
//                } catch(Exception e) {}
//                finally {
//                    Intent intent = new Intent(Splash.this, Home.class);
//                    startActivity(intent);
//                }
//            }
//        };
//        mythread.start();
//    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
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
                        

                    	sleep(7000);
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
		
		
//		if(check){
//			try {
//				Thread.sleep(10000);
//				
//				Intent intent = new Intent (this, MainActivityMenu.class);
//				startActivity(intent);
//				finish();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
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
