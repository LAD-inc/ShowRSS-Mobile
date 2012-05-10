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

import com.showrss.LoadingDialog;
import com.showrss.LoginTask;
import com.showrss.R;
import com.showrss.User;

public class LoginActivity extends Activity implements OnClickListener {

	// Declarations for threading
	private String username;
	private String password;
	EditText uNameEdit;
	EditText passEdit;
	LoadingDialog loadingDialog;

	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: cookie manager is not working yet
		CookieSyncManager.createInstance(this);

		setContentView(R.layout.login);

		this.setupViews();
		this.setupListeners();
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO: cookie manager is not working yet
		CookieSyncManager.getInstance().startSync();
		
		// Calls http request and if user name is present, it is already logged in
		// and the and the login screen is not loaded
		try 
		{
			if (User.getUserName() != "") 
			{
				// user is already logged in
				changeToMenu();
			}
		}
		catch (Exception e) {
			//Already at login Acitivty
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// TODO: cookie manager is not working yet
		CookieSyncManager.getInstance().stopSync();
	}

	private void setupViews() {
		loginButton = (Button) this.findViewById(R.id.loginButton);

		loadingDialog = new LoadingDialog(this, getString(R.string.logging_in_));
	}

	private void setupListeners() {
		loginButton.setOnClickListener(this);
		uNameEdit = (EditText) findViewById(R.id.username);
		passEdit = (EditText) findViewById(R.id.password);
	}

	private void displayToast(String text) {
		// Creates and displays a toast
		Toast.makeText(this, R.string.error_ + text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * This method
	 * 
	 */
	public void changeToMenu() {
		Log.d("LoginActivity", "Changing to Menu");

		CookieSyncManager.getInstance().sync();

		Intent myIntent = new Intent(this, MenuActivity.class);
		startActivity(myIntent);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.loginButton) {

			// extract user name
			this.username = uNameEdit.getText().toString();
			this.password = passEdit.getText().toString();

			LoginTask login = new LoginTask(this.username, this.password);
			new LoginToRss().execute(login);
		}

	}

	class LoginToRss extends AsyncTask<LoginTask, Integer, String> {
		@Override
		protected void onPreExecute() {
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(LoginTask... login) {

			LoginTask newLogin = login[0];

			try 
			{
				return newLogin.attemptLogin();
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
				changeToMenu();
			} 
			else {

				displayToast(result);
			}

			loadingDialog.hideLoadingDialog();
		}

	}

}