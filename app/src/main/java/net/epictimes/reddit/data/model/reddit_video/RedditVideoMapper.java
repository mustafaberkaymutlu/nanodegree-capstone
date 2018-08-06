package net.epictimes.reddit.data.model.reddit_video;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class RedditVideoMapper implements Function<RedditVideoRaw, RedditVideo> {

    @Inject
    RedditVideoMapper() {
    }

    @Override
    public RedditVideo apply(RedditVideoRaw redditVideoRaw) {
        validate(redditVideoRaw);

        return new RedditVideo(redditVideoRaw.getHlsUrl());
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
