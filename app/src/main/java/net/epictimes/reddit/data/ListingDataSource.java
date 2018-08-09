package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.listing.ListingOffline;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ListingDataSource {

    Completable saveListing(Listing listing);

    Completable clearListing();

    Single<List<ListingOffline>> getListing();

}
