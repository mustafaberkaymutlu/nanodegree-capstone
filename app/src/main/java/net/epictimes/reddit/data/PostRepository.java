package net.epictimes.reddit.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.data.model.vote.Vote;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import org.reactivestreams.Subscriber;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class PostRepository {

    @Nonnull
    private final PostDataSource remoteDataSource;

    @Nonnull
    private final PostDataSource localDataSource;

    @Inject
    PostRepository(@RemoteDataSource @Nonnull PostDataSource remoteDataSource,
                   @LocalDataSource @Nonnull PostDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Nonnull
    public Flowable<Listing> retrieveBestPosts(@Nullable String after) {
        return remoteDataSource
                .getBestPosts(after)
                .flatMap(listing ->
                        localDataSource
                                .savePosts(listing.getChildren())
                                .andThen(new Flowable<Listing>() {
                                    @Override
                                    protected void subscribeActual(Subscriber<? super Listing> s) {
                                        s.onNext(listing);
                                    }
                                }));
    }

    @Nonnull
    public Flowable<Post> retrievePost(@Nonnull String postId) {
        return localDataSource.getPost(postId);
    }

    @Nonnull
    public Completable refreshPost(@Nonnull String postId) {
        return remoteDataSource
                .getPost(postId)
                .flatMapCompletable(localDataSource::savePost);
    }

    @Nonnull
    public Completable sendVote(@NonNull String postId, Vote vote) {
        return remoteDataSource.vote("t3_" + postId, vote.getVoteAsString());
    }
}
