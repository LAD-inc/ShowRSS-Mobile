package com.ladinc.showrss.activitys;

import com.ladinc.showrss.utilities.Utilities;
import com.ladinc.showrss.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener {

	TextView userName;
	private Button yourShows;
	private Button addNewShows;
	private Button aboutButton;
	private Button scheduleButtton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		Bundle bundle = getIntent().getExtras();
		
		

		userName = (TextView) findViewById(R.id.loggedInAsName);
		
		userName.setText(bundle.get("user").toString());
		
//		try {
//			userName.setText(Utilities.getUserName());
//		} catch (Exception e) {
//			e.printStackTrace();
//			switchActivity(this, LoginActivity.class);
//
//		}

		this.setupViews();
		this.setupListeners();

	}

	private void setupViews() {
		yourShows = (Button) this.findViewById(R.id.yourShowsButton);
		addNewShows = (Button) this.findViewById(R.id.addNewShowsButton);
		scheduleButtton = (Button) this.findViewById(R.id.scheduleButton);
		aboutButton = (Button) this.findViewById(R.id.aboutButton);
	}

	private void setupListeners() {
		yourShows.setOnClickListener(this);
		addNewShows.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		scheduleButtton.setOnClickListener(this);
	}

	public void switchActivity(Context packageContextName, @SuppressWarnings("rawtypes") Class className) {
		Intent myIntent = new Intent(packageContextName, className);
		startActivity(myIntent);
	}

	@Override
	public void onClick(View v) {

		if (Utilities.isOnline(this)) {
			switch (v.getId()) {
			case R.id.yourShowsButton:
				switchActivity(this, YourShowsActivity.class);
				break;
			case R.id.addNewShowsButton:
				switchActivity(this, AddNewShowsActivity.class);
				break;
			case R.id.scheduleButton:	
				switchActivity(this, ScheduleActivity.class);
				break;
			case R.id.aboutButton:
				switchActivity(this, AboutActivity.class);
				break;

			}
		} else {
			displayToast(getString(R.string.no_internet_connection));
		}

	}

	private void displayToast(String text) {

		// Creates and displays a toast
		Toast.makeText(this, getString(R.string.error_) + text, Toast.LENGTH_SHORT).show();
	}

}
