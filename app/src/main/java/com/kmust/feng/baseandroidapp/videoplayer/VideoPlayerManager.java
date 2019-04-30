package com.kmust.feng.baseandroidapp.videoplayer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.base.Frameworks;
import com.kmust.feng.baseandroidapp.util.ResourceUtil;

import java.io.File;

/**
 * Created by LHF on 2019-04-30.
 * <p>
 * YY.Inc
 */
public class VideoPlayerManager {

    private static VideoPlayerManager playerManager;
    private static long maxCacheSize = 1024 * 1024 * 1024;
    private static long maxFileSize = 5 * 1024 * 1024;

    public static VideoPlayerManager getInstance() {
        if (playerManager == null) {
            synchronized (VideoPlayerManager.class) {
                if (playerManager == null) {
                    playerManager = new VideoPlayerManager();
                }
            }
        }
        return playerManager;
    }

    private static final String DOWNLOAD_ACTION_FILE = "actions";
    private static final String DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private static final int MAX_SIMULTANEOUS_DOWNLOADS = 2;

    protected String userAgent;

    private VideoPlayerManager() {
        userAgent = getUserAgent(Frameworks.getApplication(), ResourceUtil.getString(R.string.app_name));
    }

    public DataSource.Factory buildDataSourceFactory() {
        DefaultDataSourceFactory upstreamFactory =
                new DefaultDataSourceFactory(Frameworks.getApplication(), buildHttpDataSourceFactory());
        return buildReadOnlyCacheDataSource(upstreamFactory);
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(userAgent);
    }

    private static CacheDataSourceFactory buildReadOnlyCacheDataSource(DefaultDataSourceFactory upstreamFactory) {
        LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(maxCacheSize);
        SimpleCache simpleCache = new SimpleCache(new File(
                Frameworks.getApplication().getCacheDir(), "media"), evictor);
        return new CacheDataSourceFactory(
                simpleCache,
                upstreamFactory,
                new FileDataSourceFactory(),
                new CacheDataSinkFactory(simpleCache, maxFileSize),
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                /* eventListener= */ null);
    }

    /**
     * Returns a user agent string based on the given application name and the library version.
     *
     * @param context         A valid context of the calling application.
     * @param applicationName String that will be prefix'ed to the generated user agent.
     * @return A user agent string generated using the applicationName and the library version.
     */
    private String getUserAgent(Context context, String applicationName) {
        String versionName;
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "?";
        }
        return applicationName + "/" + versionName + " (Linux;Android " + Build.VERSION.RELEASE
                + ") " + ExoPlayerLibraryInfo.VERSION_SLASHY;
    }
}
