package net.epictimes.reddit.data;

import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.listing.Listing;

import io.reactivex.Flowable;

public interface PostDataSource {

    Flowable<Listing> getBestPosts(@Nullable String after);

}
