package com.kmust.feng.baseandroidapp.util;

import android.app.Activity;
import android.content.Intent;

import com.kmust.feng.baseandroidapp.mainpage.MainPageActivity;

/**
 * Created by LHF on 2019-04-30.
 * <p>
 * YY.Inc
 */
public class NavigationUtil {

    public static void gotoMainPage(Activity activity) {
        Intent intent = new Intent(activity, MainPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
