package com.kmust.feng.baseandroidapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kmust.feng.baseandroidapp.util.StatusBarUtils;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    protected T mViewModel;
    protected int statusBarHeight; // 系统状态栏高度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initStatusBar();
        // 设置沉浸式状态栏
        StatusBarUtils.setImmersiveStatusBar(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 添加状态栏
     */
    protected void initStatusBar() {
        //获取status bar的高度
        int id = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = getResources().getDimensionPixelSize(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.onViewModelDestroy();
        }
    }
}
