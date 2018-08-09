package net.epictimes.reddit.data.local.listing_offline;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.epictimes.reddit.data.model.listing.ListingOffline;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ListingOffline> listingOfflines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListingOffline listingOffline);

    @Query("SELECT * from ListingOffline ORDER BY id")
    Single<List<ListingOffline>> getListing();

    @Query("DELETE FROM ListingOffline")
    void clear();
}
