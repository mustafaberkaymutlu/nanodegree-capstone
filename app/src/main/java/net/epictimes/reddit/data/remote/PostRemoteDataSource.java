package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@RemoteDataSource
@Singleton
public class PostRemoteDataSource implements PostDataSource {

    private final Services services;

    @Inject
    PostRemoteDataSource(Services services) {
        this.services = services;
    }

    @Override
    public Flowable<Object> getPopularSubreddits() {
        return services
                .getPopularSubreddits()
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }
}
