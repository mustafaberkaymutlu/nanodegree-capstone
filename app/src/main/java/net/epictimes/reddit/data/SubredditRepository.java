package net.epictimes.reddit.data;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.data.model.subreddit.SubscribeRequest;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import org.reactivestreams.Subscriber;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class SubredditRepository {

    @NonNull
    private final SubredditDataSource remoteDataSource;

    @NonNull
    private final SubredditDataSource localDataSource;

    @Inject
    SubredditRepository(@RemoteDataSource @NonNull SubredditDataSource remoteDataSource,
                        @LocalDataSource @NonNull SubredditDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Nonnull
    public Flowable<Subreddit> retrieveSubreddit(@NonNull String subreddit) {
        return Flowable.concat(getSubredditFromLocal(subreddit),
                getSubredditFromRemote(subreddit)
        );
    }

    private Flowable<Subreddit> getSubredditFromLocal(@NonNull String subreddit) {
        return localDataSource.getSubredditMaybe(subreddit).toFlowable();
    }

    private Flowable<Subreddit> getSubredditFromRemote(@NonNull String subreddit) {
        return remoteDataSource
                .getSubreddit(subreddit)
                .flatMap(subreddit1 ->
                        localDataSource
                                .saveSubreddit(subreddit1)
                                .andThen(new Flowable<Subreddit>() {
                                    @Override
                                    protected void subscribeActual(Subscriber<? super Subreddit> s) {
                                        s.onNext(subreddit1);
                                    }
                                }));
    }

    @NonNull
    public Completable sendSubscription(SubscribeRequest subscribeRequest) {
        return remoteDataSource.sendSubscription(subscribeRequest);
    }

    @NonNull
    public Flowable<List<SubredditSearch>> search(@NonNull String query) {
        return remoteDataSource.search(query);
    }

}
