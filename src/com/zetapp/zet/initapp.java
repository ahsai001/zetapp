package com.zetapp.zet;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class initapp extends GennieSplashActivity {
    /** Called when the activity is first created. */

	CountDownTimer timer;
	String[] titles = null;
	String[] sources = null;
	AQuery aq;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageSplash(R.drawable.splash);
    }
    
    @Override
    protected void onResume() {
    	aq = new AQuery(this);
    	ProgressDialog dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setInverseBackgroundForced(false);
		//dialog.setCanceledOnTouchOutside(true);
		dialog.setMessage("Loading List...");
		Log.e("zetapp", "hahahahah");
	    aq.progress(dialog).ajax("https://dl.dropbox.com/u/56028961/zetappconfig.xml",String.class,new AjaxCallback<String>(){
	    	@Override
	    	public void callback(String url, String xml, AjaxStatus status) {
	    		if(status.getCode() < 200 || status.getCode() > 299){
	    			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
	    	        	ArrayList<String> mp3list = listfiles(new File("/sdcard"));
	    	        	titles = new String[mp3list.size()];
	    	    		sources = new String[mp3list.size()];
	    	        	if(mp3list != null && mp3list.size() > 0){
		    	        	for(int d = 0; d < (mp3list.size()); d++){
		    	        		sources[d] = mp3list.get(d);
		    	        		titles[d] = sources[d].substring(sources[d].lastIndexOf('/'), sources[d].lastIndexOf('.'));
		    	        	}
	    	    		}
	    			}
	    		}else{
	    			Log.e("zetapp", "sini");
	    			XMLParser xmlparser = new XMLParser(initapp.this);
    	    		Document xmldoc = xmlparser.getDomElement(xml);
    	    		Log.e("zetapp", "xml:"+xml);
    	    		
    	    		//cara 1:
    	    		NodeList directorys = xmldoc.getElementsByTagName("directory");
    	    		
    	    		ArrayList<String> mp3list = null;
    	    		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
    	    			mp3list = listfiles(new File(xmlparser.getElementValue(directorys.item(0))));
    	    		}
    	    			//convert into String[]
    	        	Log.e("zetapp", "NUMsdfasdfadsfa");
    	        	
    	    		//cara 2:
    	    		NodeList playlist = xmldoc.getElementsByTagName("item");
    	    		Log.e("zetapp", "NUM:"+playlist.getLength());
    	    		
    	    		int jumlahlist = 0;
    	    		int reg2 = 0;
    	    		if(mp3list != null && mp3list.size() > 0){
    	    			jumlahlist += mp3list.size();
    	    		}
    	    		if(playlist != null && playlist.getLength() > 0){
    	    			jumlahlist += playlist.getLength();
    	    			reg2 = playlist.getLength();
    	    		}
    	    		
    	    		titles = new String[jumlahlist];
    	    		sources = new String[jumlahlist];
    	    		
    	    		if(playlist != null && playlist.getLength() > 0){
	    	    		for(int i = 0; i < playlist.getLength(); i++){
	    	    			titles[i] = xmlparser.getElementValue(((Element)playlist.item(i)).getElementsByTagName("title").item(0));
	    	    			sources[i] = xmlparser.getElementValue(((Element)playlist.item(i)).getElementsByTagName("source").item(0));
	    	    		}
    	    		}
    	    		if(mp3list != null && mp3list.size() > 0){
	    	        	for(int d = reg2; d < (mp3list.size()+reg2); d++){
	    	        		sources[d] = mp3list.get(d-reg2);
	    	        		titles[d] = sources[d].substring(sources[d].lastIndexOf('/'), sources[d].lastIndexOf('.'));
	    	        	}
    	    		}
    	    		
    	    		//
    	    		xmldoc = null;
    	    		xmlparser = null;
	    		}
	    		timer = new CountDownTimer(300,300){
	    			@Override
	    			public void onFinish() {
	    				if(titles != null && titles.length > 0){
		    				Intent in = new Intent(initapp.this,AudioPlayer.class);
		    				in.putExtra("titles", titles);
		    				in.putExtra("sources", sources);
		    				startActivity(in);
	    				}else{
	    					Toast.makeText(initapp.this, "Your PlayerList is empty", Toast.LENGTH_SHORT).show();
	    				}
	    				finish();
	    			}

	    			@Override
	    			public void onTick(long millisUntilFinished) {
	    				// TODO Auto-generated method stub
	    			}
	            };
	            timer.start();
	    	}
	    });
    	
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	if(timer != null)
    		timer.cancel();
    	super.onPause();
    }
}