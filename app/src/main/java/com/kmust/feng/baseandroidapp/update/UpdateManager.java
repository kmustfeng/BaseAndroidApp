package com.kmust.feng.baseandroidapp.update;

import android.content.Context;

import com.tencent.bugly.Bugly;

/**
 * Created by LHF on 2019-04-29.
 * <p>
 * YY.Inc
 */
public class UpdateManager {

    private static UpdateManager mInstance;

    public static UpdateManager getInstance() {
        if (mInstance == null) {
            synchronized (UpdateManager.class) {
                if (mInstance == null) {
                    mInstance = new UpdateManager();
                }
            }
        }
        return mInstance;
    }

    public static final String BUGLY_APPID = "123456789";

    private UpdateManager() {
        // init
    }

    /**
     * 初始化升级sdk
     */
    public void init(Context context) {
        Bugly.init(context, BUGLY_APPID, false);
    }
}
