package com.showrss.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.showrss.LoginTask;
import com.showrss.R;
import com.showrss.User;

public class LoginActivity extends Activity implements OnClickListener{

	// Declarations for threading
	private String username;
	private String password;
	EditText uNameEdit;
	EditText passEdit;
	
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CookieSyncManager.createInstance(this);
		
		
		if ("" != User.getUserName())
		{
			//user is already looged in
			changeToMenu();
		}
		
		setContentView(R.layout.login);
	
//		this.initThreading();
		this.setupViews();
		this.setupListeners();
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		CookieSyncManager.getInstance().startSync();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	private void setupViews(){
		loginButton = (Button)this.findViewById(R.id.loginButton);
	}
	
	
	private void setupListeners(){
		loginButton.setOnClickListener(this);
		uNameEdit = (EditText)findViewById(R.id.username);
		passEdit = (EditText)findViewById(R.id.password);
	}

	private void displayToast(String text)
	{
		Toast.makeText(this, "Error: " + text, Toast.LENGTH_SHORT).show();
	}
	
	public void changeToMenu()
	{
		Log.d("LoginActivity", "Changing to Menu");
		
		CookieSyncManager.getInstance().sync();
		
		Intent myIntent = new Intent(this, MenuActivity.class);
		startActivity(myIntent);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.loginButton){
			
			this.username = uNameEdit.getText().toString();
			this.password = passEdit.getText().toString();
			
			LoginTask login = new LoginTask(this.username, this.password);
			new LoginToRss().execute(login);
		}
		
	}
	
	class LoginToRss extends AsyncTask<LoginTask, Integer, String>
	{

		@Override
		protected String doInBackground(LoginTask... login)
		{
		
			LoginTask newLogin = login[0];
			
			return newLogin.attemptLogin();

		}
		
		@Override
		protected void onPostExecute(String result)
		{
			//Check if there was an error
			if (result == null )
			{
				changeToMenu();
			}
			else
			{
				displayToast(result);
			}
				
		}
		
	}

}