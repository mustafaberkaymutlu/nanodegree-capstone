package net.epictimes.reddit.data.model.reddit_video;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class RedditVideoMapper implements ObservableTransformer<RedditVideoRaw, RedditVideo> {

    @Inject
    public RedditVideoMapper() {
    }

    @Override
    public ObservableSource<RedditVideo> apply(Observable<RedditVideoRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .map(redditVideoRaw -> new RedditVideo(redditVideoRaw.getHlsUrl()));
    }

    private void validate(RedditVideoRaw raw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(raw.getHlsUrl())) {
            stringBuilder.append("hls_url cannot be null, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
