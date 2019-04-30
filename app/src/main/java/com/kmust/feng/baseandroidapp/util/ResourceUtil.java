package com.kmust.feng.baseandroidapp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kmust.feng.baseandroidapp.base.Frameworks;

import java.lang.ref.WeakReference;


/**
 * Created by lhf on 2017/3/6.
 */

public class ResourceUtil {

    private static volatile WeakReference<Context> contextRef;

    private static Context getContext() {
        if (contextRef == null) {
            synchronized (ResourceUtil.class) {
                if (contextRef == null) {
                    Context context = Frameworks.getApplication();
                    contextRef = new WeakReference<Context>(context);
                }
            }
        }
        return contextRef.get();
    }

    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    public static String getString(int resId, Object... args) {
        return getContext().getResources().getString(resId, args);
    }

    public static int getColor(int resId) {
        return getContext().getResources().getColor(resId);
    }

    public static Drawable getDrawable(int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    public static int getDimension(int resId) {
        return getContext().getResources().getDimensionPixelOffset(resId);
    }

}
