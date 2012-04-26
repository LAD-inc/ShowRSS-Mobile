package com.showrss.activitys;

import com.showrss.AllShows;
import com.showrss.YourShows;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class YourShowsActivity extends ListActivity{
	
	private static final String TAG = "YourShowActivity";
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        

    	super.onCreate(savedInstanceState);

        //TODO: This only needs to be done on app load i think
        AllShows.populateAllShows();
        
        YourShows.getShows();
        
        configureList();
        
        Log.d(TAG, YourShows.shows.toString());
    }
    
    

    
	private void configureList()
	{
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, YourShows.showsAsArray());
	
		setListAdapter(adapter);
	}
	

}
