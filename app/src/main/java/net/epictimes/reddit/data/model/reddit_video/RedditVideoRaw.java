package net.epictimes.reddit.data.model.reddit_video;

import com.google.gson.annotations.SerializedName;

public class RedditVideoRaw {

    @SerializedName("hls_url")
    private String hlsUrl;

    public String getHlsUrl() {
        return hlsUrl;
    }
}
