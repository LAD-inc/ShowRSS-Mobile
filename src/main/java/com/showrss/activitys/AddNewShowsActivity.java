package com.showrss.activitys;

import com.showrss.AllShows;
import com.showrss.R;
import com.showrss.YourShows;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddNewShowsActivity extends Activity implements OnClickListener{

	private static final String TAG = "AddNewShowActivity";
	
	private Button addShow;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewshows);
        
        //This does not changes, it should maybe loaded once on app startup
        AllShows.populateAllShows();

        	
		configureSpinner();
		this.setupViews();
		this.setupListeners();
		
		
        Log.d(TAG, YourShows.shows.toString());
    }
    
	private void setupViews(){
		addShow = (Button)this.findViewById(R.id.addShowButton);

	}
    
    private void setupListeners(){
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
		
		//TODO: Needs to be threaded.
		YourShows.addShow(selectedShow);
		
		configureSpinner();
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
		s.setAdapter(adapter);
	}

}
