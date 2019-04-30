package com.kmust.feng.baseandroidapp.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayActivity extends BaseActivity {

    private static final String URL_1 = "http://pl-ali.youku.com/playlist/m3u8?vid=XNDEzNzE2NjM1Ng&type=mp4hd2v3&ups_client_netip=b72475f3&utid=m0lPFaZ4cxICAbckdfN0oRdC&ccode=0590&psid=af3ce679284a3854e0bd532a7121523b&duration=217&expire=18000&drm_type=1&drm_device=10&ups_ts=1556634527&onOff=0&encr=0&ups_key=f9d483f2f2d3e56ca2368498eb3adc35";
    private static final String URL_2 = "http://pl-ali.youku.com/playlist/m3u8?vid=XNDE2MTAwOTExMg&type=mp4hd2v3&ups_client_netip=b72475f3&utid=f0tPFWVaezMCAbckdfNjQPXJ&ccode=0590&psid=8e887ea45f722ec5deed827418d963c3&duration=233&expire=18000&drm_type=1&drm_device=10&ups_ts=1556635010&onOff=0&encr=0&ups_key=01aff16cf9131173f17f8db45d15eb0e";
    private static final String URL_3 = "http://pl-ali.youku.com/playlist/m3u8?vid=XNjIyMjUwNTAw&type=mp4hd2v3&ups_client_netip=b72475f3&utid=u09PFZ3x5ToCAbckdfODarTe&ccode=0590&psid=5a80c36b01a9beaa88a516019612f106&duration=8575&expire=18000&drm_type=1&drm_device=10&ups_ts=1556636094&onOff=0&encr=0&ups_key=7f84f2ae59646274d53cb8779dac3597";

    @BindView(R.id.base_video_play_view)
    BaseVideoPlayView baseVideoPlayView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        // test
        baseVideoPlayView.playVideo(URL_3, "m3u8");
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
}
