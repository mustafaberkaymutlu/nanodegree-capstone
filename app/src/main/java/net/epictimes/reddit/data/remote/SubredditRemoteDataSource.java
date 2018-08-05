package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditDataSource;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.data.model.subreddit.SubredditMapper;
import net.epictimes.reddit.data.model.subreddit.SubredditResponse;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class SubredditRemoteDataSource implements SubredditDataSource {

    @NonNull
    private final Services services;

    @NonNull
    private final SubredditMapper subredditMapper;

    @Inject
    SubredditRemoteDataSource(@NonNull Services services, @NonNull SubredditMapper subredditMapper) {
        this.services = services;
        this.subredditMapper = subredditMapper;
    }

    @Override
    public Flowable<Subreddit> getSubreddit(@Nonnull String subredditName) {
        return services
                .getSubreddit(subredditName)
                .subscribeOn(Schedulers.io())
                .map(SubredditResponse::getSubredditRaw)
                .compose(subredditMapper)
                .toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Completable saveSubreddit(Subreddit subreddit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable sendSubscription(SubscribeRequest subscribeRequest) {
        return services
                .sendSubscription(subscribeRequest.getAction().getApiString(),
                        subscribeRequest.isSkipInitialDefaults(),
                        subscribeRequest.getSubredditName())
                .subscribeOn(Schedulers.io());
    }
}
