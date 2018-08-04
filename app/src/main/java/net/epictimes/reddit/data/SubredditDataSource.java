package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.subreddit.Subreddit;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface SubredditDataSource {

    Flowable<Subreddit> getSubreddit(@Nonnull String subredditName);

    Completable saveSubreddit(Subreddit subreddit);

}
