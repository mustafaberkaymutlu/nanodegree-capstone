package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface SubredditDataSource {

    Flowable<Subreddit> getSubreddit(@Nonnull String subredditName);

    Maybe<Subreddit> getSubredditMaybe(@Nonnull String subredditName);

    Completable saveSubreddit(Subreddit subreddit);

    Completable sendSubscription(SubscribeRequest subscribeRequest);

    Flowable<List<SubredditSearch>> search(@Nonnull String query);

}
