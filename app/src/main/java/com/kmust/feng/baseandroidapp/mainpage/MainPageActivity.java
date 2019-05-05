package com.kmust.feng.baseandroidapp.mainpage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.BaseActivity;
import com.kmust.feng.baseandroidapp.util.GsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends BaseActivity<MainPageViewModel> {

    public static final String VIEW_MODEL_TAG = "MainPageViewModel";

    @BindView(R.id.main_video_list)
    RecyclerView mainVideoList;

    private MainVideoListAdapter mainVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create viewModel
        mViewModel = ViewModelProviders.of(this).get(VIEW_MODEL_TAG, MainPageViewModel.class);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // initVideoListView
        mainVideoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainVideoAdapter = new MainVideoListAdapter();
        mainVideoList.setAdapter(mainVideoAdapter);
        parseData();
    }

    private void parseData() {
        String json = "";
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("mainPageList.json");
            byte[] bytes;
            bytes = new byte[inputStream.available()];
            int result = inputStream.read(bytes);
            json = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<VideoEntry> videoEntryList = GsonUtil.fromJson(json, new TypeToken<List<VideoEntry>>() {
        });
        if (videoEntryList != null && videoEntryList.size() > 0) {
            Log.i("parseData", videoEntryList.toString());
            mainVideoAdapter.setVideoEntryList(videoEntryList);
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
