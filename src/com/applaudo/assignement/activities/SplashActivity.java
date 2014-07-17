package com.applaudo.assignement.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.applaudo.assignement.R;

/**
 * Activity to show for a couple of seconds the logo of the company
 * and the assigment
 * @author Arthur
 *
 */
public class SplashActivity extends Activity {

	/**
	 * Constant to know what is the current state in case of device rotation
	 */
	private static final String STATE_ISWORING = "com.gg.assigment.activitiess.STATE_ISWORING";
	
	/**
	 * Time constant to show the activity in milliseconds
	 */
	protected static final long TIME_SPLASH = 2 * 1000;
	
	private boolean isWorking;
	
	/**
	 * Asynctask to run for a couple of seconds
	 */
	private SplashWorker splashWorker;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		isWorking = false;
		
		if (savedInstanceState != null) {
			isWorking = savedInstanceState.getBoolean(STATE_ISWORING);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_ISWORING, isWorking);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if (!isWorking) {
			splashWorker = new SplashWorker();
			splashWorker.execute(TIME_SPLASH);
		}
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		// Cancel the task if the user quits the application
		if (isFinishing() && splashWorker != null && splashWorker.getStatus() == AsyncTask.Status.RUNNING) {
			splashWorker.cancel(true);
		}
	}

	/**
	 * The function will redirect to the main activity
	 */
	public void finishSplash() {
		
		Intent intent = new Intent(SplashActivity.this, VenueListActivity.class);
		startActivity(intent);

		isWorking = false;
		
		finish();
		
	}

	/**
	 * Asynctask that runs a thread for a couple of seconds before laucnhing
	 * the main activity
	 */
	private class SplashWorker extends AsyncTask<Long, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		} 

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

				
			finishSplash();
			
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Void doInBackground(Long... delay) {
			
	
			// Wait a bit
			try {
				Thread.sleep(delay[0]); 
			} catch (InterruptedException e) {
				// Ignore
			}

			return null;
		}
	}

}
