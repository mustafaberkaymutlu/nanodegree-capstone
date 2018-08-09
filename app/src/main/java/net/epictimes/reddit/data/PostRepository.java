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
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;

@Singleton
public class PostRepository {

    @Nonnull
    private final PostDataSource remoteDataSource;

    @Nonnull
    private final PostDataSource localDataSource;

    @NonNull
    private final ListingDataSource listingLocalDataSource;

    @Inject
    PostRepository(@RemoteDataSource @Nonnull PostDataSource remoteDataSource,
                   @LocalDataSource @Nonnull PostDataSource localDataSource,
                   @LocalDataSource @NonNull ListingDataSource listingLocalDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.listingLocalDataSource = listingLocalDataSource;
    }

    @Nonnull
    public Flowable<Listing> retrieveBestPosts(@Nullable String after) {
        if (after == null) {
            return Flowable.concat(retrieveBestPostsFromLocal(),
                    retrieveBestPostsFromRemote(null));
        } else {
            return retrieveBestPostsFromRemote(after);
        }
    }

    private Flowable<Listing> retrieveBestPostsFromRemote(@Nullable String after) {
        return remoteDataSource
                .getBestPosts(after)
                .flatMap(listing ->
                        localDataSource
                                .savePosts(listing.getChildren())
                                .andThen((CompletableSource) cs -> {
                                    if (after == null) {
                                        listingLocalDataSource
                                                .clearListing()
                                                .subscribe(cs);
                                    }
                                })
                                .andThen(listingLocalDataSource.saveListing(listing))
                                .andThen(new Flowable<Listing>() {
                                    @Override
                                    protected void subscribeActual(Subscriber<? super Listing> s) {
                                        s.onNext(listing);
                                    }
                                }));
    }

    private Flowable<Listing> retrieveBestPostsFromLocal() {
        return listingLocalDataSource
                .getListing()
                .flatMap(listingOfflines ->
                        Flowable
                                .fromIterable(listingOfflines)
                                .flatMapSingle(listingOffline -> localDataSource.getPostSingle(listingOffline.getPostId()))
                                .toList()
                                .map(posts ->
                                        new Listing.Builder()
                                                .withChildren(posts)
                                                .build()))
                .toFlowable();
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
        return remoteDataSource.vote(postId, vote.getVoteAsString());
    }
}
