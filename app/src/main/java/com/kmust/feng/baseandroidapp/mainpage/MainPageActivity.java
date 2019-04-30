package com.kmust.feng.baseandroidapp.mainpage;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;

import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;
import com.kmust.feng.baseandroidapp.util.StatusBarUtils;

public class MainPageActivity extends BaseActivity<MainPageViewModel> {

    public static final String VIEW_MODEL_TAG = "MainPageViewModel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置沉浸式状态栏
        StatusBarUtils.setImmersiveStatusBar(this, Color.TRANSPARENT);
        // create viewModel
        mViewModel = ViewModelProviders.of(this).get(VIEW_MODEL_TAG, MainPageViewModel.class);
        setContentView(R.layout.activity_main);
    }
}
