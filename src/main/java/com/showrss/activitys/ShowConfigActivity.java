package com.showrss.activitys;

import com.showrss.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowConfigActivity extends Activity
{
	String showName = "";
	Button button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showconfig);
		
		Bundle bundle = getIntent().getExtras();
		showName = bundle.get("showName").toString();
		
		button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) 
		    {
		    	saveSettings();
		    	finish();
		    }
		  });
	}
	
	private void saveSettings()
	{
		//String showId = AllShows.showNameAsKey.get(showName);
	}
}
