package com.kmust.feng.baseandroidapp.mainpage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;
import com.kmust.feng.baseandroidapp.videoplayer.VideoPlayActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainPageActivity extends BaseActivity<MainPageViewModel> {

    public static final String VIEW_MODEL_TAG = "MainPageViewModel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create viewModel
        mViewModel = ViewModelProviders.of(this).get(VIEW_MODEL_TAG, MainPageViewModel.class);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.goto_video_player})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.goto_video_player:
                Intent intent = new Intent(this, VideoPlayActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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
