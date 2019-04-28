package com.kmust.feng.baseandroidapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.kmust.feng.baseandroidapp.base.Frameworks;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    private static Context context() {
        return Frameworks.getApplication();
    }

    /**
     * 注册网络状态改变监听
     */
    public static void registerDefaultNetworkCallback() {
        if (context() != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (cm != null) {
                    cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                            Log.i(TAG, "onAvailable");
                        }

                        @Override
                        public void onLosing(Network network, int maxMsToLive) {
                            Log.i(TAG, "onLosing");
                        }

                        @Override
                        public void onLost(Network network) {
                            Log.i(TAG, "onLost");
                        }

                        @Override
                        public void onUnavailable() {
                            Log.i(TAG, "onUnavailable");
                        }

                        @Override
                        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                            Log.i(TAG, "onCapabilitiesChanged");
                        }

                        @Override
                        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                            Log.i(TAG, "onLinkPropertiesChanged");
                        }
                    });
                }
            }
        }
    }

    /**
     * 判断网络是否可用
     *
     * @return true or false
     */
    public static boolean isNetworkAvailable() {
        if (context() != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = null;
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
            }
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
        return true;
    }
}
