package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.listing.ListingMapper;
import net.epictimes.reddit.data.model.listing.ListingResponse;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RemoteDataSource
@Singleton
public class PostRemoteDataSource implements PostDataSource {

    @NonNull
    private final Services services;

    @NonNull
    private final ListingMapper listingMapper;

    @Inject
    PostRemoteDataSource(@NonNull Services services, @NonNull ListingMapper listingMapper) {
        this.services = services;
        this.listingMapper = listingMapper;
    }

    @Override
    public Flowable<Listing> getBestPosts(@Nullable String after) {
        return services
                .getBestPosts(after)
                .subscribeOn(Schedulers.io())
                .map(ListingResponse::getData)
                .map(listingMapper);
    }

    @Override
    public Completable savePosts(List<Post> posts) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<Post> getPost(@Nonnull String postId) {
        return services
                .getPost("t3_" + postId)
                .subscribeOn(Schedulers.io())
                .map(ListingResponse::getData)
                .map(listingMapper)
                .map(listing -> listing.getChildren().get(0));
    }

    @Override
    public Single<Post> getPostSingle(@Nonnull String postId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable savePost(@Nonnull Post post) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Completable vote(@NonNull String postId, String voteDirection) {
        return services
                .vote("t3_" + postId, "2", voteDirection)
                .subscribeOn(Schedulers.io());
    }
}
