package net.epictimes.reddit.data.model.subreddit;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class SubredditMapper implements Function<SubredditRaw, Subreddit> {

    @Inject
    SubredditMapper() {
    }

    @Override
    public Subreddit apply(SubredditRaw subredditRaw) {
        validate(subredditRaw);

        return buildSubreddit(subredditRaw);
    }

    @NonNull
    private Subreddit buildSubreddit(SubredditRaw subredditRaw) {
        return new Subreddit.Builder()
                .withDisplayName(subredditRaw.getDisplayName())
                .withDisplayNamePrefixed(subredditRaw.getDisplayNamePrefixed())
                .withSubscribers(subredditRaw.getSubscribers())
                .withDescription(subredditRaw.getDescription())
                .withUserIsSubscriber(subredditRaw.getUserIsSubscriber())
                .build();
    }

    private void validate(SubredditRaw raw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(raw.getDisplayName())) {
            stringBuilder.append("display_name cannot be empty, ");
        }

        if (StringUtils.isBlank(raw.getDisplayNamePrefixed())) {
            stringBuilder.append("display_name_prefixed cannot be empty, ");
        }

        if (raw.getSubscribers() == null) {
            stringBuilder.append("subscribers cannot be null, ");
        }

        if (StringUtils.isBlank(raw.getDescription())) {
            stringBuilder.append("description_html cannot be empty, ");
        }

        if (raw.getUserIsSubscriber() == null) {
            stringBuilder.append("user_is_subscriber cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
