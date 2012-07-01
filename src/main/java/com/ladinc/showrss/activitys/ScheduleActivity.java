package com.ladinc.showrss.activitys;

//Using this tutorial http://www.androidhive.info/2011/08/android-tab-layout-tutorial/

import com.ladinc.showrss.R;
import com.ladinc.showrss.domain.PersonalSchedule;
import com.ladinc.showrss.utilities.LoadingDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScheduleActivity extends TabActivity{

	private static final String TAG = "ScheduleActivity";
	PersonalSchedule schedule;
	LoadingDialog loadingDialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        
        setupViews();
        schedule = new PersonalSchedule();
    }
	
	private void setupViews() 
    {
		tabViewSetup();
		loadingDialog = new LoadingDialog(this, getString(com.ladinc.showrss.R.string.loadSchedule));
	}
	
	
	public void tabViewSetup()
	{
        TabHost tabHost = getTabHost();
        
        // Tab for upcoming
        TabSpec upcomingspec = tabHost.newTabSpec("Upcoming");
        // setting Title and Icon for the Tab
        upcomingspec.setIndicator("Upcoming Episodes");
        Intent upcomingIntent = new Intent(this, UpcomingScheduleActivity.class);
        upcomingspec.setContent(upcomingIntent);
 
        // Tab for aired
        TabSpec airedspec = tabHost.newTabSpec("Aired");
        airedspec.setIndicator("Aired Episodes");
        Intent airedIntent = new Intent(this, AiredScheduleActivity.class);
        airedspec.setContent(airedIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(airedspec);
        tabHost.addTab(upcomingspec);
	}
	
	public void switchActivity( @SuppressWarnings("rawtypes") Class className)
    {
		Intent myIntent = new Intent(this, className);
		startActivity(myIntent);
    }
	
}
