package com.example.sofrtk.NetworkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Looper;

import java.util.logging.Handler;

public class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public static void registerNetworkCallback(Context context, NetworkConnection networkConnection) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();

            connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {

                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    new android.os.Handler(Looper.getMainLooper()).post(() -> {
                        networkConnection.onNetworkConnected();
                    });
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    // Internet is lost
                    new android.os.Handler(Looper.getMainLooper()).post(() -> {
                        networkConnection.onNetworkDisconnected();
                    });
                }
            });
        }
    }
}