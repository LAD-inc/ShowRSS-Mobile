package com.showrss.activitys;

import com.showrss.R;
import com.showrss.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{
	
	TextView userName;
	private Button yourShows;
	private Button airedEpisodes;
	private Button upcomingEpisodes;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        userName = (TextView)findViewById(R.id.loggedInAsName);
        userName.setText(User.userName);
        
		this.setupViews();
		this.setupListeners();
		
    }

	private void setupViews(){
		yourShows = (Button)this.findViewById(R.id.yourShowsButton);
		airedEpisodes = (Button)this.findViewById(R.id.airedShowsButton);
		upcomingEpisodes = (Button)this.findViewById(R.id.upcomingShowsButton);
	}
    
    private void setupListeners(){
		yourShows.setOnClickListener(this);
		airedEpisodes.setOnClickListener(this);
		upcomingEpisodes.setOnClickListener(this);
	}

    public void switchActivity(Context packageContextName, Class<YourShowsActivity> className)
    {
		Intent myIntent = new Intent(packageContextName, className);
		startActivity(myIntent);
    }
    
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.yourShowsButton:
				switchActivity(this, YourShowsActivity.class);
				break;
			case R.id.airedShowsButton:
				//do something
				break;
			case R.id.upcomingShowsButton:
				//do something
				break;
		}
		
	}
}
