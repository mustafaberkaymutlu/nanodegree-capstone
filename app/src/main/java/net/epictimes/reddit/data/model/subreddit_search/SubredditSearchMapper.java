package net.epictimes.reddit.data.model.subreddit_search;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class SubredditSearchMapper implements Function<SubredditSearchRaw, SubredditSearch> {

    @Inject
    SubredditSearchMapper() {
    }

    @Override
    public SubredditSearch apply(SubredditSearchRaw raw) {
        validate(raw);

        return buildSubredditSearch(raw);
    }

    @NonNull
    private SubredditSearch buildSubredditSearch(SubredditSearchRaw raw) {
        return new SubredditSearch.Builder()
                .withName(raw.getName())
                .withSubscriberCount(raw.getSubscriberCount())
                .withIconUrl(raw.getIconUrl())
                .build();
    }

    private void validate(SubredditSearchRaw raw) {

        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(raw.getName())) {
            stringBuilder.append("name cannot be empty, ");
        }

        if (raw.getSubscriberCount() == null) {
            stringBuilder.append("subscriber_count cannot be null, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
