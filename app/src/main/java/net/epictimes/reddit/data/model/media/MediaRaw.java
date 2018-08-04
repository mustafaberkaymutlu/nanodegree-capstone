package net.epictimes.reddit.data.model.media;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.reddit_video.RedditVideoRaw;

public class MediaRaw {

    @SerializedName("reddit_video")
    private RedditVideoRaw redditVideoRaw;

    public RedditVideoRaw getRedditVideoRaw() {
        return redditVideoRaw;
    }
}
