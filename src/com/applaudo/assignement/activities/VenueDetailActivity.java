package com.applaudo.assignement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.applaudo.assignement.R;
import com.applaudo.assignement.controllers.VenueController;
import com.applaudo.assignement.fragments.VenueDetailFragment;
import com.applaudo.assignement.model.Venue;

/**
 * An activity representing a single Venue detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link VenueListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link VenueDetailFragment}.
 */
public class VenueDetailActivity extends ActionBarActivity {

	private ShareActionProvider mShareActionProvider;

	private long mCurrentIdVenue = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_venue_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			
			mCurrentIdVenue = getIntent().getLongExtra(VenueDetailFragment.ARG_ITEM_ID, 0);
			
			arguments.putLong(VenueDetailFragment.ARG_ITEM_ID, mCurrentIdVenue);
			VenueDetailFragment fragment = new VenueDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.venue_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,VenueListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_menu, menu);
		MenuItem item = menu.findItem(R.id.menu_item_share);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
		
		if(mShareActionProvider == null) System.out.println("NULLLL");
		else mShareActionProvider.setShareIntent(getShareIntent());
		
		
		
		return true;
	}
	
	private Intent getShareIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		if (mCurrentIdVenue != -1) {
			
			Venue venueToShare =  VenueController.getInstance().getVenueWithId(mCurrentIdVenue);
			System.out.println("MAME:"+venueToShare.getName());
			intent.putExtra(Intent.EXTRA_SUBJECT, "Awesome Bar!!");
			intent.putExtra(Intent.EXTRA_TEXT, venueToShare.getName() + " "
					+ venueToShare.getAddress());
		}

		return intent;
	}
}
