package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@RemoteDataSource
@Singleton
public class RemotePostDataSource implements PostDataSource {

    private final Services services;

    @Inject
    public RemotePostDataSource(Services services) {
        this.services = services;
    }
}
