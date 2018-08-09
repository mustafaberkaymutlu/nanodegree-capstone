package net.epictimes.reddit.data.local.listing_offline;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.ListingDataSource;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.listing.ListingOffline;
import net.epictimes.reddit.data.model.listing.ListingOfflineMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ListingLocalDataSource implements ListingDataSource {

    @NonNull
    private final ListingDao listingDao;

    @NonNull
    private final ListingOfflineMapper listingOfflineMapper;

    @Inject
    ListingLocalDataSource(@NonNull ListingDao listingDao,
                           @NonNull ListingOfflineMapper listingOfflineMapper) {
        this.listingDao = listingDao;
        this.listingOfflineMapper = listingOfflineMapper;
    }

    @Override
    public Completable saveListing(Listing listing) {
        return Observable
                .just(listing)
                .map(listingOfflineMapper)
                .flatMapCompletable(listingOfflines ->
                        Completable.fromAction(() -> listingDao.insert(listingOfflines)))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable clearListing() {
        return Completable
                .fromAction(listingDao::clear)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ListingOffline>> getListing() {
        return listingDao
                .getListing()
                .subscribeOn(Schedulers.io());
    }
}
