package com.applaudo.assignement;

import android.content.Context;
import android.graphics.Typeface;

/**
 * This class is used to keep all global constants.
 * Some constants could be set with the ant script, in the build.properties.
 * @author Arthur
 *
 */
public class AppConstants {

	/**
	 * Enable or disable the Logs. 
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * Here we define the urls for production or development for the webservice. 
	 */
	public static final String PROD_URL = "https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
	
	/**
	 * Dev and prod url so use
	 */
	public static final String URL_TO_USE = PROD_URL ;
	
	/**
	 * Constant errors
	 */
	public static final String ERROR_EMPTY_LIST = "ERROR_EMPTY_LIST";
	public static final String ERROR_NO_INTERNET = "ERROR_NO_INTERNET";

	
	/**
	 * Just change the size of the venue image to get a smaller image 
	 * for the list of venues
	 * @param imageUrl
	 * @return thumb url
	 */
	public static String getThumbImage(String imageUrl){
		return imageUrl.replace("900/500", "100/100");
		
	}

}
