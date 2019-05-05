package com.kmust.feng.baseandroidapp.mainpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kmust.feng.baseandroidapp.R;
import com.kmust.feng.baseandroidapp.util.NavigationUtil;
import com.kmust.feng.baseandroidapp.util.image.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainVideoListAdapter extends RecyclerView.Adapter<MainVideoListAdapter.VideoListViewHolder> {

    private List<VideoEntry> videoEntryList;

    public void setVideoEntryList(List<VideoEntry> data) {
        videoEntryList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_main_video, viewGroup, false);
        return new VideoListViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewHolder videoListViewHolder, int i) {
        VideoEntry videoEntry = videoEntryList.get(i);
        videoListViewHolder.videoEntry = videoEntry;
        videoListViewHolder.title.setText(videoEntry.title);
        ImageLoader.loadImage(videoListViewHolder.context, videoEntry.coverImg, videoListViewHolder.cover);
    }

    @Override
    public int getItemCount() {
        return videoEntryList == null ? 0 : videoEntryList.size();
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        public TextView title;
        @BindView(R.id.iv_cover)
        public ImageView cover;

        public Context context;
        public VideoEntry videoEntry;

        public VideoListViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (videoEntry != null) {
                    NavigationUtil.gotoVideoPlayActivity(context, videoEntry.videoUri, videoEntry.videoFormat);
                }
            });
        }
    }
}
