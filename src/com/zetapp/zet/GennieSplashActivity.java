package com.zetapp.zet;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;



public class GennieSplashActivity extends GennieActivity {
    /** Called when the activity is first created. */
	RelativeLayout rellay;
	TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	super.onCreate(savedInstanceState);
        rellay = new RelativeLayout(this);
        rellay.setBackgroundColor(Color.GRAY);
        rellay.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));	
        tv = new TextView(this);
        tv.setText("This is splashscreen");
        tv.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        tv.setLayoutParams(param);
        rellay.addView(tv);
        setContentView(rellay);
    }
    
    public ArrayList<String> listfiles(File file) {
    	ArrayList<String> outputfiles = new ArrayList<String>();
        File[] list = file.listFiles();
        if(list != null){
	        for (int i = 0; i < list.length; i++) {
	            File temp_file = new File(file.getAbsolutePath(),list[i].getName());
	            if (temp_file.isDirectory() && temp_file.listFiles() != null) {
	                ArrayList<String> insidefiles = listfiles(temp_file);
	                outputfiles.addAll(insidefiles);
	            } else {
	                if (list[i].getName().toLowerCase().endsWith(".mp3")){
	                 	Log.i("Filexx", i+":" + list[i].getAbsolutePath());
	                 	outputfiles.add(list[i].getAbsolutePath());
	                }
	            }
	        }
        }
		return outputfiles;
    }
        
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	//Gennie API methods
	
	
	protected void setImageSplash(int resid) {
		tv.setVisibility(View.GONE);
		rellay.setBackgroundResource(resid);
	}
	
	
}