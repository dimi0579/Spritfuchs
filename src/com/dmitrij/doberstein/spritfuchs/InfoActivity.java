package com.dmitrij.doberstein.spritfuchs;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class InfoActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
        
	}

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	onBackPressed();
	    	break;
	    default:
	      break;
	    }

	    return true;
	  } 
}