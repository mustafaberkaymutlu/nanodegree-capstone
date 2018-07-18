package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.model.listing.ListingResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Services {

    String BASE_URL = "https://oauth.reddit.com";

    @GET("/subreddits/popular")
    Single<ListingResponse> getPopularSubreddits();

}
