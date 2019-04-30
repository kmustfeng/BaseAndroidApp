package com.kmust.feng.baseandroidapp.mainpage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.exoplayer2.util.Util;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;
import com.kmust.feng.baseandroidapp.util.StatusBarUtils;
import com.kmust.feng.baseandroidapp.videoplayer.BaseVideoPlayView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends BaseActivity<MainPageViewModel> {

    public static final String VIEW_MODEL_TAG = "MainPageViewModel";
    @BindView(R.id.base_video_play_view)
    BaseVideoPlayView baseVideoPlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置沉浸式状态栏
        StatusBarUtils.setImmersiveStatusBar(this, Color.WHITE);
        // create viewModel
        mViewModel = ViewModelProviders.of(this).get(VIEW_MODEL_TAG, MainPageViewModel.class);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // test
        baseVideoPlayView.playVideo("https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (baseVideoPlayView != null) {
                baseVideoPlayView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || baseVideoPlayView == null) {
            if (baseVideoPlayView != null) {
                baseVideoPlayView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (baseVideoPlayView != null) {
                baseVideoPlayView.onPause();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (baseVideoPlayView != null) {
                baseVideoPlayView.onPause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (baseVideoPlayView != null) {
            baseVideoPlayView.releasePlayer();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // 按返回键回到桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
