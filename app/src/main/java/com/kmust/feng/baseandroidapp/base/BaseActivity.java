package com.kmust.feng.baseandroidapp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.util.ResourceUtil;
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

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // init view
        View statusBarBg = findViewById(R.id.activity_status_bar_bg);
        if (statusBarBg != null) {
            ConstraintLayout.LayoutParams statusBarBgLp = (ConstraintLayout.LayoutParams) statusBarBg.getLayoutParams();
            statusBarBgLp.height = statusBarHeight;
            statusBarBg.setLayoutParams(statusBarBgLp);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                // 不支持沉浸式状态栏
                statusBarBg.setVisibility(View.GONE);
            }
        }
        View contentView = findViewById(R.id.activity_content_view_with_top_bar);
        if (contentView != null) {
            int contentViewMarginTop = ResourceUtil.getDimension(R.dimen.activity_top_bar_height);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 支持沉浸式状态栏
                contentViewMarginTop += statusBarHeight;
            }
            ConstraintLayout.LayoutParams contentViewLp = (ConstraintLayout.LayoutParams) contentView.getLayoutParams();
            contentViewLp.setMargins(0, contentViewMarginTop, 0, 0);
        }
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
