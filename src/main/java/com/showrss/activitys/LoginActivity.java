package com.showrss.activitys;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.showrss.LoginTask;
import com.showrss.R;

public class LoginActivity extends Activity {

	// Declarations for threading
	private Handler guiThread;
	private ExecutorService transThread;
	@SuppressWarnings("rawtypes")
	private Future transPending;
	private Runnable loginTask;
	
	private String username;
	private String password;
	
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		
		this.initThreading();
	}

	private void setupViews(){
		loginButton = (Button)this.findViewById(R.id.loginButton);
	}
	

	/**
	 * Request the task to start after a short delay
	 * 
	 * @param task
	 * @param delayMillis
	 */
	private void queueUpdate(Runnable task, long delayMillis) {
		// Cancel previous update if it hasn't started yet
		this.guiThread.removeCallbacks(task);
		// Start an update if nothing happens after a few milliseconds
		this.guiThread.postDelayed(task, delayMillis);
	}
	
	

	/**
	 * Sets up the login task so it can be ran
	 * 
	 */
	private void initThreading() {
		this.guiThread = new Handler();
		this.transThread = Executors.newSingleThreadExecutor();

		this.loginTask = new Runnable() {
			@Override
			public void run() {

				// Cancel previous login task
				if (LoginActivity.this.transPending != null) {
					LoginActivity.this.transPending.cancel(true);
				}

				// TODO:Let user know we're doing something

				// Begin the login request but don't wait for it

				try {
					
					LoginTask loginTask = new LoginTask(LoginActivity.this.username, LoginActivity.this.password);
					LoginActivity.this.transPending = LoginActivity.this.transThread.submit(loginTask);
				} catch (RejectedExecutionException e) {
					// Unable to start new task
					//TODO: ADD Error
				}

			}
		};
	}

}