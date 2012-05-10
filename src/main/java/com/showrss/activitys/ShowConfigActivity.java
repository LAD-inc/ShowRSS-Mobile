package com.showrss.activitys;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.showrss.LoadingDialog;
import com.showrss.R;
import com.showrss.YourShows;

public class ShowConfigActivity extends Activity implements OnItemSelectedListener, OnClickListener 
{
	private static final String TAG = "ShowConfigActivity";
	
	String showName = "";
	
	Button saveSettingsButton;
	Button deleteShowButton;
	TextView showNameTextView;
	CheckBox box;
	Spinner spinner;
	
	boolean inHD = false;
	boolean inSD = false;
	
	LoadingDialog loadingDialog;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showconfig);
		
		Bundle bundle = getIntent().getExtras();
		showName = bundle.get("showName").toString();
		
		
		this.setupViews();
		this.setupListeners();
	}

	private void setupViews() {
		showNameTextView = (TextView)findViewById(R.id.showNameTop);
		saveSettingsButton = (Button)findViewById(R.id.saveSettingsButton);
		deleteShowButton = (Button)findViewById(R.id.deleteShowButton);
		spinner = (Spinner) findViewById(R.id.spinner1);
		box = (CheckBox) findViewById(R.id.checkBox1);
		
	
		showNameTextView.setText(showName);
		loadingDialog = new LoadingDialog(this, "Deleting Show");
		
	}

	private void setupListeners() {
		
		saveSettingsButton.setOnClickListener(this);	    	
		deleteShowButton.setOnClickListener(this);
		spinner.setOnItemSelectedListener(this);
		
	}
	
	private void saveSettings()
	{
		if(inHD || inSD )
		{
			YourShows.showSettings(showName, inSD, inHD, box.isChecked());			
		}
		else
		{
			Toast.makeText(this, "You need to select a quality value.", Toast.LENGTH_SHORT).show();
		}
		
		finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
	{
		String selected = parent.getItemAtPosition(pos).toString();
		if(selected.equals("HD"))
		{
			inSD = false;
			inHD = true;
		}
		else if(selected.equals("SD"))
		{
			inSD = true;
			inHD = false;
		}
		else if(selected.equals("Both"))
		{
			inSD = true;
			inHD = true;
		}
	}

	/**
	 * If nothing is selected, we have to reset everything back
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		inHD = false;
		inSD = false;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.saveSettingsButton:
		    	saveSettings();
				break;
			case R.id.deleteShowButton:
				new deleteShow().execute(showName);
				break;
		}
		
	}
	
	private void displayToast(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	//Subclass for deleting shows
	class deleteShow extends AsyncTask<String, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			loadingDialog.setMessage("Deleting");
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... selectedShow)
		{	
			YourShows.deleteShow(selectedShow[0]);
			
			return selectedShow[0];
		}
		
		@Override
		protected void onPostExecute(String selectedShow)
		{
			Log.d(TAG, "Successfully deleted " + selectedShow );
		
			loadingDialog.hideLoadingDialog();
			
			displayToast("Deleted " + selectedShow);
			
			onBackPressed();
				
		}
		
	}
}
