package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

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

    public Flowable<Listing> getBestPosts() {
        return remoteDataSource.getBestPosts();
    }
}
