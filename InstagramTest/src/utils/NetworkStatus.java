package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		try {
			return activeNetworkInfo.isConnected();
		} catch (NullPointerException e) {
			return false;
		}

	}
}
