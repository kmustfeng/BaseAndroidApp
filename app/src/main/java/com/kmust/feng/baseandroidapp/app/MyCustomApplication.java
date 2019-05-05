package com.kmust.feng.baseandroidapp.app;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.kmust.feng.baseandroidapp.base.Frameworks;
import com.kmust.feng.baseandroidapp.util.NetworkUtils;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class MyCustomApplication extends Application {

    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());
        super.onCreate();
        Frameworks.init(getApplicationContext());
        NetworkUtils.registerDefaultNetworkCallback();
    }
}
