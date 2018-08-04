package net.epictimes.reddit.data.model.media;

import net.epictimes.reddit.data.model.reddit_video.RedditVideo;

import javax.annotation.Nullable;

public class Media {

    @Nullable
    private final RedditVideo redditVideo;

    public Media(@Nullable RedditVideo redditVideo) {
        this.redditVideo = redditVideo;
    }

    @Nullable
    public RedditVideo getRedditVideo() {
        return redditVideo;
    }
}
