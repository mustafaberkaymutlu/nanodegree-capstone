package net.epictimes.reddit.data;

import net.epictimes.reddit.data.local.listing_offline.ListingLocalDataSource;
import net.epictimes.reddit.di.qualifier.LocalDataSource;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ListingRepositoryModule {

    @LocalDataSource
    @Binds
    abstract ListingDataSource bindListingLocaleDataSource(ListingLocalDataSource listingLocalDataSource);

}
