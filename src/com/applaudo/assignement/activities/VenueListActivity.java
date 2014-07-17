package com.applaudo.assignement.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.applaudo.assignement.AppConstants;
import com.applaudo.assignement.R;
import com.applaudo.assignement.controllers.VenueController;
import com.applaudo.assignement.fragments.VenueDetailFragment;
import com.applaudo.assignement.fragments.VenueListFragment;
import com.applaudo.assignement.model.RequestResult;
import com.applaudo.assignement.model.Venue;
import com.applaudo.assignement.tools.Utils;

/**
 * An activity representing a list of Venues. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of venues, which when touched, lead to a
 * {@link VenueDetailActivity} representing venue details. On tablets, the
 * activity presents the list of venues and venue details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link VenueListFragment} and the item details (if present) is a
 * {@link VenueDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link VenueListFragment.Callbacks} interface to listen for item selections.
 */
public class VenueListActivity extends ActionBarActivity implements
		VenueListFragment.Callbacks, RequestResult {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	/**
	 * In two-pane mode, the fragment to display details
	 * {@link VenueDetailFragment}
	 */
	private VenueDetailFragment fragmentVenueDetail;

	/**
	 * The venue controller to access data
	 */
	public VenueController mVenueController;

	/**
	 * Progress dialog to show during data remote fetching
	 */
	public ProgressDialog progressDialog;

	/**
	 * Share action provider used for the action bar share action
	 */
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_venue_list);

		mVenueController = VenueController.getInstance();

		if (findViewById(R.id.venue_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((VenueListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.venue_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// In case there was an error with internet or server we need to be able
		// research venues again. Call only if the venues are empty
		if (mVenueController.getVenues().isEmpty()) {
			mVenueController.loadVenues(this,getApplicationContext());
			showProgress(R.string.loading);
		}
	}

	/**
	 * Callback method from {@link VenueListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(long id) {

		if (mTwoPane) {
			
			//Need to update the intent with proper venue in case of sharing
			mShareActionProvider.setShareIntent(getShareIntent(id));
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(VenueDetailFragment.ARG_ITEM_ID, id);
			fragmentVenueDetail = new VenueDetailFragment();
			fragmentVenueDetail.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.venue_detail_container, fragmentVenueDetail)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, VenueDetailActivity.class);
			detailIntent.putExtra(VenueDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	/**
	 * Callback method from {@link RequestResult} indicating the response is
	 * received
	 */
	@Override
	public void handleSuccessRequest(int type) {

		dismissProgress();
		
		switch (type) {
		case VenueController.GET_VENUES:
			//Refresh the list of items after receiving the data
			((VenueListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.venue_list)).refresh();
		default:
			break;
		}
	}

	/**
	 * Callback method from {@link RequestResult} indicating there was a problem
	 * with the remote request
	 */
	@Override
	public void handleFailedRequest(int type, String error) {

		dismissProgress();
		// Display the errors in case of internet connection pbs
		if (error.equals(AppConstants.ERROR_NO_INTERNET)) {
			Utils.showMsg(this, R.string.error_no_internet);
		} else {
			// Everything else will be considered as server pbs
			Utils.showMsg(this, R.string.error_server);
		}
	}

	@Override
	public void showProgress(int id_message) {

		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getString(id_message));
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();

	}

	@Override
	public void dismissProgress() {
		if(progressDialog != null) progressDialog.dismiss();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mTwoPane) {
			getMenuInflater().inflate(R.menu.share_menu, menu);
			MenuItem item = menu.findItem(R.id.menu_item_share);
			mShareActionProvider = (ShareActionProvider) MenuItemCompat
					.getActionProvider(item);
		}
		return true;
	}

	/**
	 * Prepare the intent to share data to other applications
	 * @return intent
	 */
	private Intent getShareIntent(long id) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		
		if (id != -1) {
			
			Venue venueToShare = mVenueController.getVenueWithId(id);
			
			intent.putExtra(Intent.EXTRA_SUBJECT, "Share venue");
			intent.putExtra(Intent.EXTRA_TEXT, venueToShare.getName() + " "
					+ venueToShare.getAddress());
		}

		return intent;
	}

}