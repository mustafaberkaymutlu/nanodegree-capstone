package net.epictimes.reddit.data;

import net.epictimes.reddit.data.local.subreddit.SubredditLocalDataSource;
import net.epictimes.reddit.data.remote.SubredditRemoteDataSource;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SubredditRepositoryModule {

    @RemoteDataSource
    @Binds
    abstract SubredditDataSource bindSubredditRemoteDataSource(SubredditRemoteDataSource remoteDataSource);

    @LocalDataSource
    @Binds
    abstract SubredditDataSource bindSubredditLocalDataSource(SubredditLocalDataSource localDataSource);

}
