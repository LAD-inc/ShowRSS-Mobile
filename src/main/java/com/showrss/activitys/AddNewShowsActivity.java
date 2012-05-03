package com.showrss.activitys;

import com.showrss.AllShows;
import com.showrss.R;
import com.showrss.YourShows;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewShowsActivity extends Activity implements OnClickListener{

	private static final String TAG = "AddNewShowActivity";
	
	private Button addShow;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewshows);
        
		Log.d(TAG, "Loading shows that are available to add");
		this.setupViews();
		this.setupListeners();
		new getAvailableShows().execute();
		
    }
    
	private void setupViews()
	{
		addShow = (Button)this.findViewById(R.id.addShowButton);

	}
    
    private void setupListeners()
    {
    	addShow.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.addShowButton:
				addShow();
				break;
		}
		
		
	}
	
	private void addShow()
	{
		Spinner s = (Spinner) findViewById(R.id.newShowSpinner);
		
		//Check is there anything selected
		if (s.getSelectedItem() == null)
			return;
		
		String selectedShow = (String) s.getSelectedItem();
		
		new addShow().execute(selectedShow);
		
		//Update available shows as one will have been removed from it
		new getAvailableShows().execute();
		
	}
	
	private void configureSpinner()
	{
		//TODO: move to a thread.
		//Load the most recent version
		YourShows.getShows();
		
    	Spinner s = (Spinner) findViewById(R.id.newShowSpinner);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapter = new ArrayAdapter(this,
		android.R.layout.simple_spinner_item, YourShows.availableShowsAsArray());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
	}
	
	private void displayToast(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	//Subclass for getting available shows
	class getAvailableShows extends AsyncTask<Object, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			//showLoadingDialog();
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
			configureSpinner();
			Log.d(TAG, "Successfully Loaded Shows to Add");
			//hideLoadingDialog();	
		}
		
	}
	
	//Subclass for adding shows
	class addShow extends AsyncTask<String, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			//showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... selectedShow)
		{
			
			YourShows.addShow(selectedShow[0]);
			
			return selectedShow[0];

		}
		
		@Override
		protected void onPostExecute(String selectedShow)
		{
			Log.d(TAG, "Successfully Added " + selectedShow );
			displayToast("Added " + selectedShow);
			//hideLoadingDialog();	
		}
		
	}

}
