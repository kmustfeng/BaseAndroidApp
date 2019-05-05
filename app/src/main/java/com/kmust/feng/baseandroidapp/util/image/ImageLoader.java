package com.kmust.feng.baseandroidapp.util.image;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        if (checkActivityIsDestroyed(context)) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        GlideApp.with(context).
                load(imgUrl).
                apply(options).into(imageView);
    }

    private static boolean checkActivityIsDestroyed(Context context) {
        return context != null
                && context instanceof Activity &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) context).isDestroyed();
    }
}
