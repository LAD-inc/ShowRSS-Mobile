package com.ladinc.showrss.notification;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.ladinc.showrss.R;

public class Alarm extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm");
		wl.acquire();

		
		Log.d("AN9", "Alarm Called" );
		// Generate a notification
		generateNotification(context);

		wl.release();
	}

	public void SetAlarm(Context context) {
		Log.d("AN9", "Set alarm" );
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, Alarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),30000, pi); // Millisec
																					// Minute
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
	
	public void generateNotification(Context context) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		int icon = R.drawable.tvrss;
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();

		RSSReader reader = new RSSReader();
		RSSFeed feed = null;
		
		// have to be set some where by the user or scraped from the website
		String uri = "http://feeds.bbci.co.uk/news/world/rss.xml";
		try {
			 feed = reader.load(uri);
		} catch (RSSReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Notification notification = new Notification(icon, tickerText, when);
		
		

	}
}