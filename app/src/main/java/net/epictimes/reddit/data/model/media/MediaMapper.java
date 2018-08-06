package net.epictimes.reddit.data.model.media;

import net.epictimes.reddit.data.model.reddit_video.RedditVideo;
import net.epictimes.reddit.data.model.reddit_video.RedditVideoMapper;
import net.epictimes.reddit.data.model.reddit_video.RedditVideoRaw;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class MediaMapper implements Function<MediaRaw, Media> {

    private final RedditVideoMapper redditVideoMapper;

    @Inject
    MediaMapper(RedditVideoMapper redditVideoMapper) {
        this.redditVideoMapper = redditVideoMapper;
    }

    @Override
    public Media apply(MediaRaw mediaRaw) {
        if (mediaRaw.getRedditVideoRaw() == null) {
            return new Media(null);
        }

        final RedditVideoRaw redditVideoRaw = mediaRaw.getRedditVideoRaw();
        final RedditVideo redditVideo = redditVideoMapper.apply(redditVideoRaw);
        return new Media(redditVideo);
    }
}
