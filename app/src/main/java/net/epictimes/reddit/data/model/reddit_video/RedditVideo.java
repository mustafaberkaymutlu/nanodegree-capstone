package net.epictimes.reddit.data.model.reddit_video;

public class RedditVideo {

    private final String hlsUrl;

    public RedditVideo(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }
}
