package com.ladinc.showrss.activitys;

import com.ladinc.showrss.R;
import com.ladinc.showrss.domain.PersonalSchedule;
import com.ladinc.showrss.utilities.LoadingDialog;
import com.ladinc.showrss.utilities.Utilities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class UpcomingScheduleActivity extends ListActivity{
	
	private static final String TAG = "UpcomingScheduleActivity";
	PersonalSchedule schedule;
	LoadingDialog loadingDialog;
	
	public void onCreate(Bundle savedInstanceState) {
        
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.upcoming_schedule);
	        schedule = new PersonalSchedule();
	        loadingDialog = new LoadingDialog(this, getString(com.ladinc.showrss.R.string.loadSchedule));
	        
	        if (Utilities.isOnline(this)) {
		        //populate the schedule in the background
		        new getSchedule().execute();
			} else {
				displayToast(getString(R.string.no_internet_connection));
			}
		}
		catch (Exception e)
		{
			// this is the line of code that sends a real error message to the log
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());
	 
			// this is the line that prints out the location in
			// the code where the error occurred.
			e.printStackTrace();
		}
		
    }
	
	public void switchActivity( @SuppressWarnings("rawtypes") Class className)
    {
		Intent myIntent = new Intent(this, className);
		startActivity(myIntent);
    }
	
	private void configureList()
	{
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, schedule.upcomingAsArray());
	
		setListAdapter(adapter);
		

	}
	
	private void displayToast(String text) {

		// Creates and displays a toast
		Toast.makeText(this, getString(R.string.error_) + text, Toast.LENGTH_SHORT).show();
	}
	
	
	//Sub Class
	class getSchedule extends AsyncTask<Object, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(Object... notNeeded)
		{
			try
			{
				
				schedule.getUpcomingShows();
				
				return "";
			}
			catch (Exception e) 
			{ 
				e.printStackTrace();
				return e.getMessage();
			}

		}
		
		@Override
		protected void onPostExecute(String result)
		{
			if (result == "")
			{
				
				Log.d(TAG, "Successfully Loaded Schedule");
				configureList();
				loadingDialog.hideLoadingDialog();
			}
			else
			{
				loadingDialog.hideLoadingDialog();
				switchActivity( LoginActivity.class);
			}
		}
		
	}

}
