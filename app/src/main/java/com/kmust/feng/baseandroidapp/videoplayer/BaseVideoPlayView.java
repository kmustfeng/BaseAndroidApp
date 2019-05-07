package com.kmust.feng.baseandroidapp.videoplayer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.util.ResourceUtil;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LHF on 2019-04-30.
 * <p>
 * YY.Inc
 */
public class BaseVideoPlayView extends ConstraintLayout implements
        PlayerControlView.VisibilityListener,
        PlaybackPreparer,
        Player.EventListener {

    private static final String TAG = "BaseVideoPlayView";

    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    @BindView(R.id.exo_player_view)
    PlayerView exoPlayerView;

    // 媒体源工厂
    private DataSource.Factory dataSourceFactory;
    // 播放器
    private SimpleExoPlayer player;
    // 媒体源
    private MediaSource mediaSource;
    // 是否自动播放
    private boolean startAutoPlay = true;
    // 开播播放的窗口
    private int startWindow = -1;
    // 开始播放的时间点
    private long startPosition = 0;

    public BaseVideoPlayView(Context context) {
        this(context, null);
    }

    public BaseVideoPlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.color.black);
        // init view
        LayoutInflater.from(context).inflate(R.layout.layout_base_video_play_view, this, true);
        ButterKnife.bind(this, this);
        initPlayerView();
    }

    /**
     * 播放视频
     *
     * @param url 视频地址
     */
    public void playVideo(String url, String overrideExtension) {
        Uri videoUri = Uri.parse(url);
        mediaSource = buildMediaSource(videoUri, overrideExtension);
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        player.prepare(mediaSource, !haveStartPosition, false);
    }

    /**
     * 重置
     */
    public void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    /**
     * 释放播放器
     */
    public void releasePlayer() {
        if (player != null) {
            updateStartPosition();
            player.release();
            player = null;
            mediaSource = null;
        }
    }

    public void onResume() {
        if (exoPlayerView != null) {
            exoPlayerView.onResume();
        }
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    public void onPause() {
        if (exoPlayerView != null) {
            exoPlayerView.onPause();
        }
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onVisibilityChange(int visibility) {
        // 视频区控制ui显示隐藏回调

    }

    @Override
    public void preparePlayback() {
        // 准备播放
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // 缓冲状态改变
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // 播放错误
        Log.e(TAG, "onPlayerError", error);
    }

    /**
     * 初始化播放器
     */
    private void initPlayerView() {
        exoPlayerView.setControllerVisibilityListener(this);
        exoPlayerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        exoPlayerView.getVideoSurfaceView().setKeepScreenOn(true);
        exoPlayerView.requestFocus();
        dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        player = ExoPlayerFactory.newSimpleInstance(getContext());
        player.addListener(this);
        player.setPlayWhenReady(startAutoPlay);
        exoPlayerView.setPlayer(player);
        exoPlayerView.setPlaybackPreparer(this);
    }

    private DataSource.Factory buildDataSourceFactory() {
        return VideoPlayerManager.getInstance().buildDataSourceFactory();
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser<>(new DashManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser<>(new SsManifestParser(), getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory)
                        .setPlaylistParserFactory(
                                new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri)))
                        .createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return Collections.emptyList();
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString = ResourceUtil.getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = ResourceUtil.getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString =
                                    ResourceUtil.getString(
                                            R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
                            errorString =
                                    ResourceUtil.getString(
                                            R.string.error_no_decoder,
                                            decoderInitializationException.mimeType
                                    );
                        }
                    } else {
                        errorString =
                                ResourceUtil.getString(
                                        R.string.error_instantiating_decoder,
                                        decoderInitializationException.decoderName);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }
}
