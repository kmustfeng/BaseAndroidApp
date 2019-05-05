package com.kmust.feng.baseandroidapp.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayActivity extends BaseActivity {

    public static final String KEY_VIDEO_URL = "key_video_url";
    public static final String KEY_VIDEO_FORMAT = "key_video_format";

    @BindView(R.id.base_video_play_view)
    BaseVideoPlayView baseVideoPlayView;

    private String videoUrl = "";
    private String videoFormat = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        // playVideo
        if (getIntent() != null) {
            videoUrl = getIntent().getStringExtra(KEY_VIDEO_URL);
            videoFormat = getIntent().getStringExtra(KEY_VIDEO_FORMAT);
            baseVideoPlayView.playVideo(videoUrl, videoFormat);
        }
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
        if (Util.SDK_INT <= 23) {
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
}
