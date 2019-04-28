package com.kmust.feng.baseandroidapp.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by xxd on 2018/7/15
 */
@SuppressWarnings("javadoc")
public class StatusBarUtils {

    public static void setImmersiveStatusBar(Activity activity, @ColorInt int statusBarColor) {
        Window window = null;
        try {
            window = activity.getWindow();
        } catch (Exception ex) {
            // do nothing
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(statusBarColor);
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(statusBarColor)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

}
