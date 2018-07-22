package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.listing.Listing;

import io.reactivex.Flowable;

public interface PostDataSource {

    Flowable<Listing> getBestPosts();

}
