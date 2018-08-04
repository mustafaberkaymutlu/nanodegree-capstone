package net.epictimes.reddit.data.local.subreddit;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditDataSource;
import net.epictimes.reddit.data.model.subreddit.Subreddit;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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
    public Completable saveSubreddit(Subreddit subreddit) {
        return Completable
                .fromAction(() -> subredditDao.insert(subreddit))
                .subscribeOn(Schedulers.io());
    }

}
