package com.applaudo.assignement.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.applaudo.assignement.R;
import com.applaudo.assignement.activities.VenueDetailActivity;
import com.applaudo.assignement.activities.VenueListActivity;
import com.applaudo.assignement.adapters.ScheduleAdapter;
import com.applaudo.assignement.controllers.VenueController;
import com.applaudo.assignement.model.Venue;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Venue detail screen. This fragment is either
 * contained in a {@link VenueListActivity} in two-pane mode (on tablets) or a
 * {@link VenueDetailActivity} on handsets.
 */
public class VenueDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "venue_id";

	/**
	 * Current venue
	 */
	private Venue mVenue;

	/**
	 * The list of schedule items
	 */
	private ListView mListSchedule;

	/**
	 * The schedule item adapters to add in the list
	 */
	private ScheduleAdapter mScheduleAdapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VenueDetailFragment() {
	}

	/**
	 * Textand image views
	 */
	private TextView mTitle, mAddress, mCityCP;
	private ImageView mImageBook;
	private Button mButtonCall, mButtonTickets, mButtonDirection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Get book details
			mVenue = VenueController.getInstance().getVenueWithId(
					getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_venue_detail,
				container, false);
		mTitle = (TextView) rootView.findViewById(R.id.text_title);
		mAddress = (TextView) rootView.findViewById(R.id.text_address);
		mCityCP = (TextView) rootView.findViewById(R.id.text_city_cp);
		mImageBook = (ImageView) rootView.findViewById(R.id.img_venue);
		mListSchedule = (ListView) rootView.findViewById(R.id.list_schedule);
		mButtonCall  = (Button) rootView.findViewById(R.id.button_call);
		mButtonTickets  = (Button) rootView.findViewById(R.id.button_tickets);
		mButtonDirection  = (Button) rootView.findViewById(R.id.button_direction);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Poplulate the view 
		if (mVenue != null) {

			mTitle.setText(mVenue.getName());
			mAddress.setText(mVenue.getAddress());
			mCityCP.setText(mVenue.getCity() + ", " + mVenue.getState() + " " + mVenue.getPcode());

			//We load the image if it exists of show the no photo available
			if (!TextUtils.isEmpty(mVenue.getImageUrl()) && mImageBook != null) {
				Picasso.with(getActivity()).load(mVenue.getImageUrl()).into(mImageBook);
			} else {
				mImageBook.setImageResource(R.drawable.no_photo_available);
			}

			//if the schedule is available we fill the list
			if (mVenue.getSchedule() != null && mVenue.getSchedule().size() > 0) {
				mScheduleAdapter = new ScheduleAdapter(getActivity(), mVenue.getSchedule());
				mListSchedule.setAdapter(mScheduleAdapter);
			}
			
			//Show the call button if the venue has a phone
			if(!TextUtils.isEmpty(mVenue.getPhone())){
				setCallListener(mVenue.getPhone());
			}else{
				mButtonCall.setVisibility(View.INVISIBLE);
			}
			
			//Show the call button if the venue has ticket link
			if(!TextUtils.isEmpty(mVenue.getTicketLink())){
				setTicketsListener(mVenue.getTicketLink());
			}else{
				mButtonTickets.setVisibility(View.INVISIBLE);
			}
			
			//All venues has coordinates and name so we can redirect to google mpas
			setDirectionListener(mVenue.getAddress(), mVenue.getName(), mVenue.getLatitude(), mVenue.getLongitude());
		}
	}

	private void setCallListener(final String phone) {
		
		mButtonCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
				startActivity(intent);
				
			}
		});
	}
	
	private void setTicketsListener(final String link) {

		mButtonTickets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
				startActivity(i);

			}
		});
	}

	private void setDirectionListener(final String address, final String name, final double latitude, final double longitude) {

		mButtonDirection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String strAddress = "";
				if(address != null){
					strAddress = address;
				}
				
				String uri = "geo:"+latitude+","+longitude+"?q="+ strAddress + "(" +name+")";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
	
		}
	});
}
}