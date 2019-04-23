package com.zetapp.zet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class nextplayerreceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
	   Intent in = new Intent(context,audioservice.class);
	   in.putExtra("action", "next");
	   context.startService(in);
	}

}
