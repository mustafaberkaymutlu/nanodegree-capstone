package net.epictimes.reddit.features.subreddit_detail;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.subreddit.Subreddit;

public class SubredditDetailViewEntity {

    @NonNull
    private final Subreddit subreddit;

    public SubredditDetailViewEntity(@NonNull Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    @NonNull
    public Subreddit getSubreddit() {
        return subreddit;
    }
}
