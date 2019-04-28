package com.kmust.feng.baseandroidapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    protected T mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.onViewModelDestroy();
        }
    }
}
