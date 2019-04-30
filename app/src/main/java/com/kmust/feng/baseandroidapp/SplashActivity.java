package com.kmust.feng.baseandroidapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.kmust.feng.baseandroidapp.base.BaseActivity;
import com.kmust.feng.baseandroidapp.util.NavigationUtil;
import com.kmust.feng.baseandroidapp.util.StatusBarUtils;

/**
 * Created by LHF on 2019-04-30.
 * <p>
 * YY.Inc
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setImmersiveStatusBar(this, Color.WHITE);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            NavigationUtil.gotoMainPage(SplashActivity.this);
            finish();
        }, 500);
    }
}
