package com.zetapp.zet;

import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class AudioPlayer extends GennieActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linlay = (LinearLayout)getLayoutInflater().inflate(R.layout.main, null);
		ListView lvplaylist = (ListView)linlay.findViewById(R.id.listplay);
		final String[] sources = getIntent().getExtras().getStringArray("sources");
		final String[] titles = getIntent().getExtras().getStringArray("titles");
		Log.e("dource", ""+sources);
		Log.e("titlessss", ""+titles);
		lvplaylist.setAdapter(new audiolistitemadapter(this,sources,titles));
		lvplaylist.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				   try{
					   //cara 1: player exist inside service
					   Intent in = new Intent(AudioPlayer.this,audioservice.class);
					   in.putExtra("position", position);
					   in.putExtra("songlist", sources);
					   AudioPlayer.this.startService(in);
				   } catch(Exception e){
					   // handle error here.. 
				   }
				}
			});
		setContentView(linlay);
	}
}
