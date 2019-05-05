package com.kmust.feng.baseandroidapp.mainpage;

import com.google.gson.annotations.SerializedName;

public class VideoEntry {

    @SerializedName("title")
    public String title;

    @SerializedName("coverImg")
    public String coverImg;

    @SerializedName("videoUri")
    public String videoUri;

    @SerializedName("videoFormat")
    public String videoFormat;

    @Override
    public String toString() {
        return "VideoEntry{" +
                "title='" + title + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", videoUri='" + videoUri + '\'' +
                ", videoFormat='" + videoFormat + '\'' +
                '}';
    }
}
