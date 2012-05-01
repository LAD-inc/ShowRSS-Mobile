package com.showrss.activitys;

import com.showrss.AllShows;
import com.showrss.YourShows;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class YourShowsActivity extends ListActivity{
	
	private static final String TAG = "YourShowActivity";
	private ListView list;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        

    	super.onCreate(savedInstanceState);

        //TODO: This only needs to be done on app load i think
        AllShows.populateAllShows();
        
        YourShows.getShows();
        
        configureList();
        
        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) 
        	{
        		changeToShowConfigActivity((String)list.getItemAtPosition(position));
			}
        });
        
        Log.d(TAG, YourShows.shows.toString());
    }
    
    

    
	private void configureList()
	{
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, YourShows.showsAsArray());
	
		setListAdapter(adapter);
	}
	
	private void changeToShowConfigActivity(String argShowName)
    {
    	Intent intent = new Intent(this, ShowConfigActivity.class);
    	intent.putExtra("showName", argShowName);
    	startActivityForResult(intent, 0);
    }
}
