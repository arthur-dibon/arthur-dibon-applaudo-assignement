package com.applaudo.assignement.controllers;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.applaudo.assignement.AppConstants;
import com.applaudo.assignement.model.RequestResult;
import com.applaudo.assignement.model.Venue;
import com.applaudo.assignement.tools.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *  Class that use the singleton design pattern in order to define and keep application globales 
 *  variables and datas.
 * 
 * 'Please read the interesting conversation with Reto Meier and Dianne Hackborn in which use of 
 *  Application subclasses is discouraged in favor of Singleton patterns. '
 *  https://plus.google.com/u/0/+AnderWebbs/posts/DsfpW51Vvow
 * 
 *  The current list of venues is just kept in a array and could be saved in a database for instance.
 *  The controller is using the volley library that will handle the remote connection, sending and 
 *  receiving requests in JSon. 
 *  
 * @author Arthur
 *
 */
public class VenueController{

	public static final String TAG = "VenueController";
	
	public static final int GET_VENUES = 10;
	public static final int GET_VENUE_DETAIL = 11;
	
	/**
	 * The list of venues
	 */
	private ArrayList<Venue> mVenues = new ArrayList<Venue>();
	
	/**
	 * The queue used to add requests
	 */
	private RequestQueue queue;
	
	//---------------Singleton--------------------
	public static VenueController getInstance() {
        if (null == instance) {
            instance = new VenueController();
        }
        return instance;
    }
	
	private VenueController() {
	
	}

	private static VenueController instance;
	//---------------End Singleton--------------------
	
	
    public  ArrayList<Venue> getVenues(){
        return this.mVenues;
    }
    
    public void setVenues(ArrayList<Venue> venues){
        this.mVenues = venues;
    }

    /**
     * Load the venues and rely on the volley library to handle the threads and remote tasks.
     * 
     * @param activityResult
     * @param context
     */
	public void loadVenues(final RequestResult activityResult, Context context){
		
		//Check if there is an internet connection
		if(!Utils.isConnectedToInternet(context)){
			activityResult.handleFailedRequest(GET_VENUES, AppConstants.ERROR_NO_INTERNET);
			return;
		}
		
		//create the request queue if it doesn't exist already
		if(queue == null) queue = Volley.newRequestQueue(context);
		
		JsonArrayRequest jsObjRequest= new JsonArrayRequest(AppConstants.PROD_URL, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				
				if(AppConstants.DEBUG) {
					Log.d(TAG, "Response => "+response.toString());
				}
				
				GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss Z");
                Gson gson = gsonBuilder.create();
				
                //Parse the response with GSON library
				ArrayList<Venue> temp = gson.fromJson(response.toString(), new TypeToken<ArrayList<Venue>>() {}.getType());
				
				if(temp.size() > 0){
					//Add result to bottom of the list
					mVenues.addAll(temp); 
					// Notify the listening activity that the response is good
					activityResult.handleSuccessRequest(GET_VENUES);
				}else{
					// There is no more venues the load, notif a failure
					activityResult.handleFailedRequest(GET_VENUES, AppConstants.ERROR_EMPTY_LIST);
				}
				
			
			}
		}, new ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				if(AppConstants.DEBUG) {
					Log.d(TAG, "Response Error => "+error.toString());
				}
				// Notify the listening activity that the request failed
				activityResult.handleFailedRequest(GET_VENUES, error.toString());

			}
		});
		
		queue.add(jsObjRequest);
	}
	
	/**
	 * Retrieve the venue with the venue id
	 * @param id
	 * @return venue found
	 */
	public Venue getVenueWithId(long id) {
		
		Venue tmp = new Venue();

		for (Venue venue : mVenues) {
			
			if(venue.getId() == id){
				tmp = venue;
				break;
			}
		}
		
		return tmp;
	}
	
	public void clean() {
		mVenues.clear();
		queue.cancelAll(new RequestFilter() {
			
			@Override
			public boolean apply(Request<?> arg0) {
				return true;
			}
		});
	}
	
}
