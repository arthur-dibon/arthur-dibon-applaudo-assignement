package com.applaudo.assignement.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applaudo.assignement.R;
import com.applaudo.assignement.model.ScheduleItem;

/**
 * Adapter for product list, load products item information.
 * @author arthur
 *
 */
public class ScheduleAdapter extends BaseAdapter {

		// List of books to display in the list
		public List<ScheduleItem> items = new ArrayList<ScheduleItem>();
    	
		private Context mContext;
    	private LayoutInflater inf;
    	
    	/**
    	 * Constuctor of the adapter
    	 * @param context
    	 * @param results venues to display
    	 * @param newPageListener
    	 */
    	public ScheduleAdapter(final Context context, final List<ScheduleItem> results) {
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
 			   
 			    convertView = inf.inflate(R.layout.schedule_item, null);
 		
     			holder.title = (TextView) convertView.findViewById(R.id.text_title);
     			convertView.setTag(holder);
 	        }else{
 	    	   holder = (ViewHolder) convertView.getTag();
 	        }
 		   
 		    ScheduleItem item = items.get(position);
 		    holder.title.setText(item.getScheduleString());
     	    return convertView;
     	}
     	
     	private static class ViewHolder {
     		public TextView title;
     	}
}	