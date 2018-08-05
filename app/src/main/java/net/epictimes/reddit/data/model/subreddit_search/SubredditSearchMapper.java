package net.epictimes.reddit.data.model.subreddit_search;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class SubredditSearchMapper implements ObservableTransformer<SubredditSearchRaw, SubredditSearch> {

    @Inject
    public SubredditSearchMapper() {
    }

    @Override
    public ObservableSource<SubredditSearch> apply(Observable<SubredditSearchRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .map(this::buildSubredditSearch);
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
