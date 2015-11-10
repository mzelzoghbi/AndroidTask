package com.fekrasoftware.androidtask.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by mohamedzakaria on 11/9/15.
 */
public class Utility {
    private static final String TAG_NETWORK = "NETWORK";
    /**
     * Check for network connection
     *
     * @return true if there is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            Log.i(TAG_NETWORK, "NETWORK AVAILABLE");
        } else {
            Log.i(TAG_NETWORK, "NO NETWORK AVAILABLE");
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
