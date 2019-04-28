package com.kmust.feng.baseandroidapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kmust.feng.baseandroidapp.base.BaseActivity;

public class MainPageActivity extends BaseActivity<MainPageViewModel> {

    public static final String VIEW_MODEL_TAG = "MainPageViewModel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create viewModel
        mViewModel = ViewModelProviders.of(this).get(VIEW_MODEL_TAG, MainPageViewModel.class);
        setContentView(R.layout.activity_main);
    }
}
