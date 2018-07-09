package net.epictimes.reddit.data;

import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class PostRepository {

    private final PostDataSource remoteDataSource;
    private final PostDataSource localDataSource;

    @Inject
    PostRepository(@RemoteDataSource PostDataSource remoteDataSource,
                   @LocalDataSource PostDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public Flowable<Object> getPopularSubreddits() {
        return remoteDataSource.getPopularSubreddits();
    }
}
