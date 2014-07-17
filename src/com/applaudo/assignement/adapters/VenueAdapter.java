package com.applaudo.assignement.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.assignement.AppConstants;
import com.applaudo.assignement.R;
import com.applaudo.assignement.model.Venue;
import com.squareup.picasso.Picasso;

/**
 * Adapter for product list, load products item information.
 * @author arthur
 *
 */
public class VenueAdapter extends BaseAdapter {

		// List of books to display in the list
		public ArrayList<Venue> items = new ArrayList<Venue>();
    	
		private Context mContext;
    	private LayoutInflater inf;
    	
    	/**
    	 * Constuctor of the adapter
    	 * @param context
    	 * @param results venues to display
    	 * @param newPageListener
    	 */
    	public VenueAdapter(final Context context, final ArrayList<Venue> results) {
    		this.mContext = context;
    		this.items = results;
    		inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}

    	public void clearEntries() {
    		// Clear all the data points
    		this.items.clear();
    		notifyDataSetChanged();
    	}
        
    	@Override
    	public int getCount() {
    		return items.size();
    	}

    	@Override
    	public Object getItem(int position) {
    		return items.get(position);
    	}

    	@Override
    	public long getItemId(int position) {
    		return position;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		
    		ViewHolder holder;
    		
 		    if (convertView == null){
 			   holder = new ViewHolder();
 			   
 			    convertView = inf.inflate(R.layout.venue_item, null);
 		
     			holder.title = (TextView) convertView.findViewById(R.id.text_title);
     			holder.address = (TextView) convertView.findViewById(R.id.text_address);
     			holder.thumb = (ImageView) convertView.findViewById(R.id.img_thumb);
     			holder.calendar = (ImageView) convertView.findViewById(R.id.img_schedule);
     			convertView.setTag(holder);
 	        }else{
 	    	   holder = (ViewHolder) convertView.getTag();
 	        }
 		   
 		    Venue venue = items.get(position);
 		    holder.title.setText(venue.getName());
 		    holder.address.setText(venue.getAddress() + ", " + venue.getCity() + ", " + venue.getState());
 		    //Use the library picasso in order to load the image in a different thread if needed
 		    if(!TextUtils.isEmpty(venue.getImageUrl())){
 		    	Picasso.with(mContext).load(AppConstants.getThumbImage(venue.getImageUrl()))
				  .error(R.drawable.no_photo_available_thumb)
				  .into(holder.thumb);

 		    }else{
 		    	holder.thumb.setImageResource(R.drawable.no_photo_available_thumb);
 		    }
 		    
 		    if(venue.getSchedule() != null && venue.getSchedule().size() > 0){
 		    	holder.calendar.setVisibility(View.VISIBLE);
 		    }else{
 		    	holder.calendar.setVisibility(View.GONE);
 		    }
 		   
     	    return convertView;
     	}
     	
     	private static class ViewHolder {
     		public TextView title, address;
     		private ImageView thumb, calendar;
     	}
}	