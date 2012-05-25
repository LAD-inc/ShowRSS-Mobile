package com.ladinc.showrss.activitys;

import java.util.List;

import com.showrss.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;


public class DisplayErrorActivity extends Activity {
	
	private TextView errorsText;
	List<String> errorsList;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try
		{
			super.onCreate(savedInstanceState);
			
			Bundle bundle = getIntent().getExtras();
			errorsList = (List<String>) bundle.get("errorList");
			
			String title = bundle.get("title").toString();
			setTitle(title);
			
			setContentView(R.layout.error_popup);
			
			this.setupViews();
			
			populateErrors();
			
			
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
	
	private void setupViews() {
		errorsText = (TextView) this.findViewById(R.id.errorText);
		
	}
	
	private void populateErrors()
	{
		String errorsAsHtml = "";
		String bullet = "&#8226; ";
		String newLine = " <br/> ";
		
		
		for (String error : errorsList)
		{
			errorsAsHtml = errorsAsHtml + bullet + error + newLine + newLine;
		}
		
		errorsText.setText(Html.fromHtml(errorsAsHtml));
	}

	
	

}
