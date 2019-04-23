package com.zetapp.zet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class stopplayerreceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
	   /*Intent in = new Intent(context,audioservice.class);
	   context.stopService(in);*/
		Intent in = new Intent(context,ahmadplayernav.class);
		in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(in);
	}

}
