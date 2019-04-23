package com.zetapp.zet;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class audiolistitemadapter extends BaseAdapter{
	Context ctx;
	String[] images;
	String[] titles;
	String[] sources;
	String[] descriptions;
	int count;
	public audiolistitemadapter(Context ctx,String[] sources, String[] titles) {
		this.sources = sources;
		this.titles = titles;
		count = titles.length;
		this.ctx = ctx;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = ((Activity)ctx).getLayoutInflater().inflate(R.layout.itemlist, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.title);
		tv.setText(titles[position]);
     return convertView;
	}
}
