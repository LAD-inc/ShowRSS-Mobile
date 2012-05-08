package com.showrss.activitys;

import com.showrss.R;
import com.showrss.YourShows;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class ShowConfigActivity extends Activity implements OnItemSelectedListener 
{
	String showName = "";
	
	Button button;
	CheckBox box;
	Spinner spinner;
	
	boolean inHD = false;
	boolean inSD = false;
	
	
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
		    }
		  });
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(this);
		
		box = (CheckBox) findViewById(R.id.checkBox1);
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
}
