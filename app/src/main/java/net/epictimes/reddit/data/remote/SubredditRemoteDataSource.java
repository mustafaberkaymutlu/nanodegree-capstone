package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditDataSource;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.data.model.subreddit.SubredditMapper;
import net.epictimes.reddit.data.model.subreddit.SubredditResponse;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchMapper;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchRaw;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchResponse;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SubredditRemoteDataSource implements SubredditDataSource {

    @NonNull
    private final Services services;

    @NonNull
    private final SubredditMapper subredditMapper;

    @NonNull
    private final SubredditSearchMapper subredditSearchMapper;

    @Inject
    SubredditRemoteDataSource(@NonNull Services services,
                              @NonNull SubredditMapper subredditMapper,
                              @NonNull SubredditSearchMapper subredditSearchMapper) {
        this.services = services;
        this.subredditMapper = subredditMapper;
        this.subredditSearchMapper = subredditSearchMapper;
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

    @Override
    public Flowable<List<SubredditSearch>> search(@Nonnull String query) {
        return services
                .searchSubreddits(false, false, true, query)
                .subscribeOn(Schedulers.io())
                .map(SubredditSearchResponse::getSubredditsRaw)
                .flatMapIterable((Function<List<SubredditSearchRaw>, Iterable<SubredditSearchRaw>>) subredditSearchRaws -> subredditSearchRaws)
                .compose(subredditSearchMapper)
                .toList()
                .toFlowable();
    }
}
