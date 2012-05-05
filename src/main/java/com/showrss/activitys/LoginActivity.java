package com.showrss.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class LoginActivity extends Activity implements OnClickListener {

	// Declarations for threading
	private String username;
	private String password;
	EditText uNameEdit;
	EditText passEdit;
	ProgressDialog dialog;

	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: cookie manager is not working yet
		CookieSyncManager.createInstance(this);

		// Calls http request and if user name is present, it is already logged in
		// and the and the login screen is not loaded
		if (User.getUserName() != "") {
			// user is already logged in
			changeToMenu();
		}

		setContentView(R.layout.login);

		this.setupViews();
		this.setupListeners();
	}

	@Override
	public void onResume() {
		super.onResume();
		// TODO: cookie manager is not working yet
		CookieSyncManager.getInstance().startSync();
	}

	@Override
	public void onPause() {
		super.onPause();
		// TODO: cookie manager is not working yet
		CookieSyncManager.getInstance().stopSync();
	}

	private void setupViews() {
		loginButton = (Button) this.findViewById(R.id.loginButton);

		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(R.string.logging_in_));
		dialog.setCancelable(false);
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

	/**
	 * Show dialog loading
	 */
	private void showLoadingDialog() {
		if (dialog != null) {
			dialog.show();
		}
	}

	private void hideLoadingDialog() {
		if (dialog.isShowing()) {
			dialog.hide();
		}
	}

	class LoginToRss extends AsyncTask<LoginTask, Integer, String> {
		@Override
		protected void onPreExecute() {
			showLoadingDialog();
		}

		@Override
		protected String doInBackground(LoginTask... login) {

			LoginTask newLogin = login[0];

			return newLogin.attemptLogin();

		}

		@Override
		protected void onPostExecute(String result) {
			// Check if there was an error
			if (result == null) {
				changeToMenu();
			} else {
				displayToast(result);
			}

			hideLoadingDialog();
		}

	}

}