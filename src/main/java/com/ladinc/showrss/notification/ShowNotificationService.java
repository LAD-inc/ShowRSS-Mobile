package com.ladinc.showrss.notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShowNotificationService extends Service{

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
    	Log.d("AN9", "Starting Service" );
        alarm.SetAlarm(this);
		return super.onStartCommand(intent, flags, startId);
	}

	Alarm alarm = new Alarm();
    public void onCreate()
    {
        super.onCreate();       
    }

    public int onStartCommand(Context context,Intent intent, int startId)
    {
    	Log.d("AN9", "Starting Service" );
        alarm.SetAlarm(context);
        
		return startId;
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
}
