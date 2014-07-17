package com.applaudo.assignement.tools;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {
	
   public static void showMsg(Context context, String string){
		
    	Toast msg = Toast.makeText(context,
				string, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.TOP, msg.getXOffset() / 1,
				msg.getYOffset() / 1);
		msg.show();
	}
    
	public static void showMsg(Context context, int string){
		
    	Toast msg = Toast.makeText(context,
				string, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.TOP, msg.getXOffset() / 1,
				msg.getYOffset() / 1);
		msg.show();
	}
	
	public static boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
	public static String formatCurrency(String totalPrice) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		return format.format(Float.parseFloat(totalPrice));
	}
	
	public static boolean isConnectedToInternet(Context context){
		
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
}
