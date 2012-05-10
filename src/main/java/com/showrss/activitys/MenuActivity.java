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
	private Button addNewShows;
	private Button aboutButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        setContentView(R.layout.main);
        
        userName = (TextView)findViewById(R.id.loggedInAsName);
        try 
        {
			userName.setText(User.getUserName());
		} 
        catch (Exception e) 
        {
        	switchActivity(this, LoginActivity.class);
        	
		}
        
		this.setupViews();
		this.setupListeners();
		
    }

	private void setupViews(){
		yourShows = (Button)this.findViewById(R.id.yourShowsButton);
		addNewShows = (Button)this.findViewById(R.id.addNewShowsButton);
		aboutButton = (Button)this.findViewById(R.id.aboutButton);
	}
    
    private void setupListeners(){
		yourShows.setOnClickListener(this);
		addNewShows.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
	}

    public void switchActivity(Context packageContextName, @SuppressWarnings("rawtypes") Class className)
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
			case R.id.addNewShowsButton:
				switchActivity(this, AddNewShowsActivity.class);
				break;
			case R.id.aboutButton:
				switchActivity(this, AboutActivity.class);
				break;

		}
		
	}
}
