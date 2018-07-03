package net.epictimes.reddit.data;

import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PostRepository {

    private final PostDataSource remoteDataSource;
    private final PostDataSource localDataSource;

    @Inject
    public PostRepository(@RemoteDataSource PostDataSource remoteDataSource,
                          @LocalDataSource PostDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }
}
