package com.zetapp.zet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;



public class GennieActivity extends Activity{
	String navID = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Log.w("Activity", "GennieActivity:onCreate");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.w("Activity", "GennieActivity:onStart");
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.w("Activity", "GennieActivity:onResume");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.w("Activity", "GennieActivity:onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.w("Activity", "GennieActivity:onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isTaskRoot()){
		}

		Log.w("Activity", "GennieActivity:onDestroy");
	}
	//***********************************************************
	
	//****************event activity******************************
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.w("Activity", "GennieActivity:onActivityResult");
	}
	//***********************************************************
	
	//****************cycle window and content******************************

	
	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
		Log.w("Activity", "GennieActivity:onContentChanged");
	}
	//***********************************************************

	//****************key mapping******************************
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.w("Activity", "GennieActivity:onKeyDown");
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.w("Activity", "GennieActivity:onKeyUp");
		return super.onKeyUp(keyCode, event);
	}
	//***********************************************************

	//****************configuration and save-restore instance**********************
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.w("Activity", "GennieActivity:onConfigurationChanged");
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		Log.w("Activity", "GennieActivity:onRetainNonConfigurationInstance");
		return super.onRetainNonConfigurationInstance();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.w("Activity", "GennieActivity:onSaveInstanceState");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.w("Activity", "GennieActivity:onRestoreInstanceState");
	}
	//***********************************************************

	
	
	//gennie core API***********************
	

	
}
