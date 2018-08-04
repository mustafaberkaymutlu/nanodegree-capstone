package net.epictimes.reddit.data.model.media;

import net.epictimes.reddit.data.model.reddit_video.RedditVideoMapper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class MediaMapper implements ObservableTransformer<MediaRaw, Media> {

    private final RedditVideoMapper redditVideoMapper;

    @Inject
    MediaMapper(RedditVideoMapper redditVideoMapper) {
        this.redditVideoMapper = redditVideoMapper;
    }

    @Override
    public ObservableSource<Media> apply(Observable<MediaRaw> upstream) {
        return upstream
                .flatMap(mediaRaw -> {
                    if (mediaRaw.getRedditVideoRaw() == null) {
                        return Observable.just(new Media(null));
                    }

                    return Observable.just(mediaRaw.getRedditVideoRaw())
                            .compose(redditVideoMapper)
                            .map(Media::new);
                });
    }
}
