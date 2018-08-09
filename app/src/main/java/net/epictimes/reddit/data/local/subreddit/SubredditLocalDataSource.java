package net.epictimes.reddit.data.local.subreddit;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditDataSource;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubredditLocalDataSource implements SubredditDataSource {

    @NonNull
    private final SubredditDao subredditDao;

    @Inject
    SubredditLocalDataSource(@NonNull SubredditDao subredditDao) {
        this.subredditDao = subredditDao;
    }

    @Override
    public Flowable<Subreddit> getSubreddit(@Nonnull String subredditName) {
        return subredditDao
                .getSubreddit(subredditName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Subreddit> getSubredditMaybe(@Nonnull String subredditName) {
        return subredditDao
                .getSubredditMaybe(subredditName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable saveSubreddit(Subreddit subreddit) {
        return Completable
                .fromAction(() -> subredditDao.insert(subreddit))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable sendSubscription(SubscribeRequest subscribeRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<List<SubredditSearch>> search(@Nonnull String query) {
        throw new UnsupportedOperationException();
    }

}
