package net.epictimes.reddit.data;

import net.epictimes.reddit.data.remote.UserRemoteDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UserRepositoryModule {

    @RemoteDataSource
    @Binds
    abstract UserDataSource bindUserRepository(UserRemoteDataSource userRemoteDataSource);

}
