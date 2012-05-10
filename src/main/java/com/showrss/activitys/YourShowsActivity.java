package com.showrss.activitys;

import com.showrss.AllShows;
import com.showrss.LoadingDialog;
import com.showrss.YourShows;
import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class YourShowsActivity extends ListActivity{
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        //populate the list of shows in the background
        new getShows().execute();
		
	}


	private static final String TAG = "YourShowActivity";
	private ListView list;
	LoadingDialog loadingDialog;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        

    	super.onCreate(savedInstanceState);
        
        Log.d(TAG, "Getting Users Shows");
        this.setupViews();
        
    }
    
    private void setupViews() 
    {
		loadingDialog = new LoadingDialog(this, getString(com.showrss.R.string.getting_shows));
	}

    
	private void configureList()
	{
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, YourShows.showsAsArray());
	
		setListAdapter(adapter);
		
		list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) 
        	{
        		changeToShowConfigActivity((String)list.getItemAtPosition(position));
			}
        });
	}
	
	private void changeToShowConfigActivity(String argShowName)
    {
    	Intent intent = new Intent(this, ShowConfigActivity.class);
    	intent.putExtra("showName", argShowName);
    	startActivityForResult(intent, 0);
    }
	
	
	class getShows extends AsyncTask<Object, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(Object... notNeeded)
		{
		
	    	//Check is allshows populated , populate it if it is not.
	        if (AllShows.allshows == null)
	        	AllShows.populateAllShows();
			
			YourShows.getShows();
			
			return "";

		}
		
		@Override
		protected void onPostExecute(String result)
		{
			configureList();
			Log.d(TAG, "Successfully Loaded Shows");
			loadingDialog.hideLoadingDialog();	
		}
		
	}
}