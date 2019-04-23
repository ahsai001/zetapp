package com.zetapp.zet;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class GennieApp extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("GennieApp", "onCreate");
	}

	@Override
	public void onLowMemory() {
		Log.e("LOW_MEMORY", "low memory occured");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.e("GennieApp", "onTerminate");
	}

}
