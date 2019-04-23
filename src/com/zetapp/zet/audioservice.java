package com.zetapp.zet;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;


public class audioservice extends Service{
	private NotificationManager mNotificationManager;
	private static boolean isRunning = false;
	private MediaPlayer mp = null;
	private Messenger mMessenger = new Messenger(new IncomingMessageHandler());
	private Messenger UImessenger = null;
	
	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_SET_DATA_VALUE = 3;
	public static final int MSG_SET_STRING_VALUE = 4;
	
	int currentposplay = -1;
	boolean isplaying = false;
	String[] sources;
	String[] titles;
	String[] images;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if(mp == null){
			mp = new MediaPlayer();
		}
		showNotification();
		isRunning = true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mp!=null){
			if(mp.isPlaying()){
				mp.stop();
			}
			mp.reset();
			mp.release();
			mp = null;
		}
		mNotificationManager.cancel(GennieUtils.getIDResource(this, "string", "audioplaying"));
		isRunning = false;
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = GennieUtils.getStringIntent(intent, "action");
		if(action != null && action.equalsIgnoreCase("next")){
			//next song;
			nextSong();
		}else if(action != null && action.equalsIgnoreCase("prev")){
			//next song;
			prevSong();
		}else if(action != null && action.equalsIgnoreCase("stop")){
			//next song;
			 isplaying = false;
			   mp.stop();
			   mp.reset();
			   GennieUtils.Toast(this, "stop audio");
			   stopSelf();
		}else{
			sources = GennieUtils.getArrayStringIntent(intent, "songlist");
			//titles = Utils.getArrayStringIntent(intent, "titlelist");
			//images = Utils.getArrayStringIntent(intent, "imagelist");
			int position = GennieUtils.getIntIntent(intent, "position",0);
			 if(currentposplay == position && mp.isPlaying()){
				   //stop
				   isplaying = false;
				   mp.stop();
				   mp.reset();
				   GennieUtils.Toast(this, "stop audio");
				   stopSelf();
			 }else{
			   currentposplay = position;
			   playSong(sources[currentposplay]);
			   //sendMessageToUI(titles[currentposplay], images[currentposplay]);
			   GennieUtils.Toast(this, "play audio");
			 }
		}
		return START_STICKY;
		
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	public static boolean isRunning(){
		return isRunning;
	}
	
	private void sendMessageToUI(String title,String image) {
		Message msg = Message.obtain();
		Bundle bdl = new Bundle();
		bdl.putString("title", title);
		bdl.putString("image", image);
		msg.what = MSG_SET_DATA_VALUE;
		msg.setData(bdl);
		try {
			if(UImessenger != null)
				UImessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private class IncomingMessageHandler extends Handler { // Handler of incoming messages from clients.
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				UImessenger = msg.replyTo;
				break;
			case MSG_UNREGISTER_CLIENT:
				UImessenger = null;
				break;
			case MSG_SET_DATA_VALUE:
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}
	
	private void showNotification() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		CharSequence text = getText(GennieUtils.getIDResource(this, "string", "audioplaying"));
		Notification notification = new Notification(GennieUtils.getIDResource(this, "drawable", "icon"), text, System.currentTimeMillis());
		
		PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, stopplayerreceiver.class), 0);
		String title = (String) getText(getResources().getIdentifier("app_name", "string", getPackageName()));
		notification.setLatestEventInfo(this, title,text, contentIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		
		/*RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
		contentView.setImageViewResource(R.id.notification_image, GennieUtils.getIDResource(this, "drawable", "icon"));
		contentView.setTextViewText(R.id.notification_title, "My custom notification title");
		contentView.setTextViewText(R.id.notification_text, "My custom notification text");
		
		PendingIntent nextIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, nextplayerreceiver.class), 0);
		contentView.setOnClickPendingIntent(R.id.notification_image, nextIntent);
		
		notification.contentView = contentView;
		
		PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, stopplayerreceiver.class), 0);
		notification.contentIntent = contentIntent;*/
		
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		
		mNotificationManager.notify(GennieUtils.getIDResource(this, "string", "audioplaying"), notification);
	}
	
	private void playSong(String songPath) {
	    	   mp.reset();
			   if(songPath.startsWith("http://")){
				   mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				   try {
					   mp.setDataSource(songPath);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				   //Utils.Toast(ctx, "PLAY STREAM bawah");
			   }else if(songPath.startsWith("/")){
				   try {
					   File mp3File = new File(songPath);
					   Uri mp3Uri = Uri.fromFile(mp3File);
					   mp.setDataSource(this,mp3Uri);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				   //Utils.Toast(ctx, "PLAY STREAM bawah");
			   }else{
				   //Utils.Toast(ctx, "PLAY LOCAL atas");
				   //cara 1:play audio from assets
				    /*AssetFileDescriptor descriptor = null;
					try {
						descriptor = getAssets().openFd("audios/"+songPath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    try {
				    	mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength() );
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    try {
					 descriptor.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					//cara 2:audio from res/raw
					Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+ songPath.substring(0, songPath.lastIndexOf('.')));
					try {
						//configdata.mp.setDataSource("android.resource://"+ctx.getPackageName()+"/raw/"+ songPath.split("\\.")[0]);
						mp.setDataSource(this, uri);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   //Utils.Toast(ctx, "PLAY LOCAL bawah");
			   }
			   mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				   @Override
				   public void onPrepared(MediaPlayer mp) {
					   mp.start();
				   }
			   });
			   mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						nextSong();
					}
			   });
			   mp.prepareAsync();
	    }

		public void nextSong(){
			if (++currentposplay >= sources.length) {
             // Last song, just reset currentPosition
				currentposplay = 0;
				stopSelf();
	        } else {
	        	// Play next song
	            playSong(sources[currentposplay]);
	        }
		}
		
		public void prevSong(){
			if (--currentposplay < 0) {
             // Last song, just reset currentPosition
				currentposplay = 0;
				stopSelf();
	        } else {
	        	// Play next song
	            playSong(sources[currentposplay]);
	        }
		}
}
