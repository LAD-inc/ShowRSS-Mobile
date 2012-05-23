package com.showrss.activitys;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.showrss.HttpClientHelper;
import com.showrss.LoadingDialog;
import com.showrss.LoginTask;
import com.showrss.R;
import com.showrss.Register;
import com.showrss.User;
import com.showrss.Utilities;
import com.showrss.activitys.LoginActivity.LoginToRss;
import com.showrss.activitys.YourShowsActivity.getShows;

public class RegisterActivity extends Activity implements OnClickListener{
	
	// Declarations for threading
	private String username;
	private String password;
	private String repeatPassword;
	private String captchaText;
	
	private Register register;
	
	EditText uNameEdit;
	EditText passEdit;
	EditText repeatPassEdit;
	EditText captchaEdit;
	
	ImageView captchaImage;
	
	LoadingDialog loadingDialog;
	
	ArrayList<String> errors;

	private Button registerButton;
	private Button newCaptchaButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try
		{
			super.onCreate(savedInstanceState);
	
			setContentView(R.layout.register);
	
			this.setupViews();
			this.setupListeners();
			
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
	
	@Override
	protected void onResume() {
		super.onResume();
		getCaptcha();
		
	}
	
	private void getCaptcha()
	{
		//Reset Errors so old errors aren't shown
		errors = null;
		if (Utilities.isOnline(this)) {
	        //Download a new captcha and display it
			new LoadCaptchaDetails().execute();
		} 
		else 
		{
			displayToast(getString(R.string.no_internet_connection));
		}
	}

	private void setupViews() {
		registerButton = (Button) this.findViewById(R.id.registerButton);
		newCaptchaButton = (Button) this.findViewById(R.id.newCaptchaButton);
		
		uNameEdit = (EditText) findViewById(R.id.username);
		passEdit = (EditText) findViewById(R.id.password);
		repeatPassEdit = (EditText) findViewById(R.id.repeatPassword);
		captchaEdit = (EditText) findViewById(R.id.captchaText);
		
		captchaImage = (ImageView) findViewById(R.id.captchaImage);
		loadingDialog = new LoadingDialog(this, getString(R.string.loading_captcha));
		
	}

	private void setupListeners() {
		registerButton.setOnClickListener(this);
		newCaptchaButton.setOnClickListener(this);

	}

	private void displayToast(String text) {

		// Creates and displays a toast
		Toast.makeText(this, getString(R.string.error_) + text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * This method
	 * 
	 */
	public void changeToMenu() {
		Log.d("LoginActivity", "Changing to Menu");

		CookieSyncManager.getInstance().sync();

		Intent myIntent = new Intent(this, MenuActivity.class);
		try {
			startActivity(myIntent);
		} finally {
			finish();
		}
	}
	
	public void switchActivity(Context packageContextName, @SuppressWarnings("rawtypes") Class className) {
		Intent myIntent = new Intent(packageContextName, className);
		startActivity(myIntent);
	}
	
	private void changeToShowConfigActivity()
    {
    	Intent intent = new Intent(this, DisplayErrorActivity.class);
    	intent.putStringArrayListExtra("errorList", errors);
    	intent.putExtra("title", "Registration Errors");
    	
    	startActivityForResult(intent, 2);
    }

	@Override
	public void onClick(View v) {

		if (Utilities.isOnline(this)) 
		{
			switch (v.getId()) 
			{
				case R.id.registerButton:
					//Get values from inputs and register
					this.username = uNameEdit.getText().toString();
					this.password = passEdit.getText().toString();
					this.repeatPassword = repeatPassEdit.getText().toString();
					this.captchaText = captchaEdit.getText().toString();
					new RegisterAttempt().execute();
					break;
				case R.id.newCaptchaButton:
					//Load a new captcha
					getCaptcha();
					break;
			}

		} 
		else 
		{
			displayToast(getString(R.string.no_internet_connection));
		}

	}
	
	public boolean getNewCaptcha() throws Exception
	{
		this.register = new Register();

		this.register.getRegisterDetails();
		
		try {
			 
			  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(this.register.captchaImageUrl).getContent());
			  captchaImage.setImageBitmap(bitmap); 
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			} catch (IOException e) {
			  e.printStackTrace();
			}
		
		return true;	

	}
	
	public boolean attemptRegister() throws Exception
	{
		

		try {
			
			String htmlCode = this.register.sendRegisterRequest(this.username, this.password, this.repeatPassword, this.captchaText);			
			
			if("" != User.extractUserName(htmlCode))
			{
				return true;
			}
			else
			{
				errors = this.register.extractErrors(htmlCode);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	
	class LoadCaptchaDetails extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			loadingDialog.setMessage(getString(R.string.loading_captcha));
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... nothing) {


			try 
			{
				getNewCaptcha();
				return "";
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return "Error";

		}

		@Override
		protected void onPostExecute(String result) {
			// Check if there was an error
			if (result == null) {
				loadingDialog.hideLoadingDialog();
				

			} else {
				loadingDialog.hideLoadingDialog();
				
			}

		}

	}
	
	class RegisterAttempt extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			loadingDialog.setMessage("Attempting to Register...");
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... nothing) {


			try 
			{
				if (attemptRegister())
					return "";
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return "Error";

		}

		@Override
		protected void onPostExecute(String result) {
			// Check if there was an error
			if (result == "") {
				loadingDialog.hideLoadingDialog();
				changeToMenu();

			} 
			else 
			{
				loadingDialog.hideLoadingDialog();
				if (errors == null)
					errors.add("Unkown Error");
				
				
				changeToShowConfigActivity();
					
				
			}

		}

	}


}
