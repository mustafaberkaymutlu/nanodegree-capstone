package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.listing.ListingMapper;
import net.epictimes.reddit.data.model.listing.ListingResponse;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@RemoteDataSource
@Singleton
public class PostRemoteDataSource implements PostDataSource {

    @NonNull
    private final Services services;

    @NonNull
    private final ListingMapper listingMapper;

    @Inject
    public PostRemoteDataSource(@NonNull Services services, @NonNull ListingMapper listingMapper) {
        this.services = services;
        this.listingMapper = listingMapper;
    }

    @Override
    public Flowable<Listing> getPopularSubreddits() {
        return services
                .getPopularSubreddits()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map(ListingResponse::getData)
                .compose(listingMapper)
                .toFlowable(BackpressureStrategy.LATEST);
    }
}
