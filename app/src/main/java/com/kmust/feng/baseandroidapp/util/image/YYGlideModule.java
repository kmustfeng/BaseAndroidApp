package com.kmust.feng.baseandroidapp.util.image;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by lhf on 2017/11/28.
 * YYGlideModule
 */
@GlideModule
public class YYGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // set memory cache
        long max = Runtime.getRuntime().maxMemory();
        builder.setMemoryCache(new LruResourceCache((int) (max / 8)));

        // set disk cache
        int MAX_CACHE_SIZE = 100 * 1024 * 1024; // 100 m
        String CACHE_FILE_NAME = "imgCache";
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
