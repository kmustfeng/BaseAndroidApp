package com.kmust.feng.baseandroidapp.util;

import android.content.Context;
import android.content.Intent;

import com.kmust.feng.baseandroidapp.mainpage.MainPageActivity;
import com.kmust.feng.baseandroidapp.videoplayer.VideoPlayActivity;

/**
 * Created by LHF on 2019-04-30.
 * <p>
 * YY.Inc
 */
public class NavigationUtil {

    public static void gotoMainPage(Context context) {
        Intent intent = new Intent(context, MainPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void gotoVideoPlayActivity(Context context,
                                             String videoUrl,
                                             String videoFormat) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VideoPlayActivity.KEY_VIDEO_URL, videoUrl);
        intent.putExtra(VideoPlayActivity.KEY_VIDEO_FORMAT, videoFormat);
        context.startActivity(intent);
    }
}
