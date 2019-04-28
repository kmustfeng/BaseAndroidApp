package com.kmust.feng.baseandroidapp.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by LHF on 2019-04-28.
 * <p>
 * YY.Inc
 */
public class BaseViewModel extends AndroidViewModel implements IAppViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onViewModelDestroy() {

    }
}
