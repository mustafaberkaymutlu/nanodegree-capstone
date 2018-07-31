package net.epictimes.reddit.data;

import net.epictimes.reddit.data.local.post.PostLocalDataSource;
import net.epictimes.reddit.data.remote.PostRemoteDataSource;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PostRepositoryModule {

    @RemoteDataSource
    @Binds
    abstract PostDataSource bindPostRemoteDataSource(PostRemoteDataSource postRemoteDataSource);

    @LocalDataSource
    @Binds
    abstract PostDataSource bindPostLocaleDataSource(PostLocalDataSource postLocalDataSource);

}
