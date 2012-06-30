package com.ladinc.showrss.activitys;

//Using this tutorial http://www.androidhive.info/2011/08/android-tab-layout-tutorial/

import com.ladinc.showrss.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScheduleActivity extends TabActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
 
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
 
//        // Tab for Videos
//        TabSpec videospec = tabHost.newTabSpec("Videos");
//        videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_videos_tab));
//        Intent videosIntent = new Intent(this, VideosActivity.class);
//        videospec.setContent(videosIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(airedspec);
        tabHost.addTab(upcomingspec); // Adding photos tab
//        tabHost.addTab(songspec); // Adding songs tab
//        tabHost.addTab(videospec); // Adding videos tab
    }
	
}
