package com.applaudo.assignement.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	
	private ListView mListSchedule;
	
	private ScheduleAdapter mScheduleAdapter;
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VenueDetailFragment() {
	}
	
	private TextView mTitle, mAddress, mCityCP;
	private ImageView mImageBook;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			//Get book details
			mVenue = VenueController.getInstance().getVenueWithId(getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_venue_detail, container, false);
		mTitle = ((TextView) rootView.findViewById(R.id.text_title));
		mAddress =  ((TextView) rootView.findViewById(R.id.text_address));
		mCityCP =  ((TextView) rootView.findViewById(R.id.text_city_cp));
		mImageBook	=  ((ImageView) rootView.findViewById(R.id.img_venue));
		mListSchedule  =  ((ListView) rootView.findViewById(R.id.list_schedule));
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Poplulate the view in case we already got the details
		if (mVenue != null) {
			
			mTitle.setText(mVenue.getName());
			mAddress.setText(mVenue.getAddress());
			mCityCP.setText(mVenue.getCity()+", "+mVenue.getState() + " " + mVenue.getPcode());
			
			if(!TextUtils.isEmpty(mVenue.getImageUrl()) && mImageBook != null){
				Picasso.with(getActivity()).load(mVenue.getImageUrl()).into(mImageBook);
			}else{
				mImageBook.setImageResource(R.drawable.no_photo_available);
			}
			
			if(mVenue.getSchedule() != null && mVenue.getSchedule().size() > 0){
				mScheduleAdapter = new ScheduleAdapter(getActivity(), mVenue.getSchedule());
				mListSchedule.setAdapter(mScheduleAdapter);
			}
		}
	}
}