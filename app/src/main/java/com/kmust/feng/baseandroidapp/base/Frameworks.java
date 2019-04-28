package com.kmust.feng.baseandroidapp.base;

import android.content.Context;

import com.kmust.feng.baseandroidapp.http.HttpService;

import java.lang.ref.WeakReference;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class Frameworks {

    private static WeakReference<Context> contextWeakReference;

    public static void init(Context context) {
        HttpService.init(context.getCacheDir().getAbsolutePath());
        contextWeakReference = new WeakReference<Context>(context);
    }

    public static Context getApplication() {
        return contextWeakReference != null ? contextWeakReference.get() : null;
    }
}
