package com.ladinc.showrss.activitys;

import com.ladinc.showrss.R;
import com.ladinc.showrss.activitys.ShowConfigActivity.getSettings;
import com.ladinc.showrss.utilities.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.Spinner;

public class buildFeedActivity extends Activity implements OnClickListener{
	
	Spinner feedQualitySpinner;
	Spinner feedRepackSpinner;
	Button generateButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_builder);

		this.setupViews();
		this.setupListeners();

	}
	
	
	private void setupListeners() {
		generateButton = (Button) this.findViewById(R.id.feed_generateButton);
		
		feedQualitySpinner = (Spinner) this.findViewById(R.id.feed_qualitySpinner);
		feedRepackSpinner = (Spinner) this.findViewById(R.id.feed_repackSpinner);
		
	}


	private void setupViews() {
		generateButton.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) 
		{
			case R.id.feed_generateButton:
				changeActivity(this, MenuActivity.class);
		}
		
	}


	private void changeActivity(	buildFeedActivity buildFeedActivity,
									Class<MenuActivity> class1) {
		
		Log.d("LoginActivity", "Changing to "+ class1.getCanonicalName());


		Intent myIntent = new Intent(buildFeedActivity, class1);
		try 
		{
			startActivity(myIntent);
		} 
		finally 
		{
			finish();
		}
		
	}

}
