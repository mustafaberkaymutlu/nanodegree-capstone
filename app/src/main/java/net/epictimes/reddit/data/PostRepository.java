package net.epictimes.reddit.data;

import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import org.reactivestreams.Subscriber;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Flowable<Listing> getBestPosts(@Nullable String after) {
        return remoteDataSource
                .getBestPosts(after)
                .flatMap(listing -> localDataSource
                        .savePosts(listing.getChildren())
                        .andThen(new Flowable<Listing>() {
                            @Override
                            protected void subscribeActual(Subscriber<? super Listing> s) {
                                s.onNext(listing);
                            }
                        }));
    }

    @Nonnull
    public Flowable<Post> getPost(@Nonnull String postId) {
        return localDataSource
                .getPost(postId)
                .toFlowable();
    }
}
