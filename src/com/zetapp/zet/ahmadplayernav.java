package com.zetapp.zet;

import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ahmadplayernav extends Activity{

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		showDialog(1);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		super.onCreateDialog(id);
		
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setOnCancelListener(new DialogInterface.OnCancelListener(){
			@Override
			public void onCancel(DialogInterface arg0) {
				ahmadplayernav.this.finish();
			}
        	
        });
        
        alert.setTitle("Ahmad Player").setMessage("Navigation");
        alert.setNegativeButton("Prev", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	Intent in = new Intent(ahmadplayernav.this,audioservice.class);
          	   	in.putExtra("action", "prev");
          	    startService(in);
          	    dialog.cancel();
            }
        }).setPositiveButton("next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	Intent in = new Intent(ahmadplayernav.this,audioservice.class);
          	   	in.putExtra("action", "next");
          	    startService(in);
          	    dialog.cancel();
            }
        }).setNeutralButton("Stop", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	Intent in = new Intent(ahmadplayernav.this,audioservice.class);
          	   	in.putExtra("action", "stop");
          	    startService(in);
          	    dialog.cancel();
            }
        });
        AlertDialog dlg = alert.create();
        dlg.setCanceledOnTouchOutside(true);
        return dlg;
	}

}
