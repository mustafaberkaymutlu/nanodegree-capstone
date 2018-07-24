package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.model.listing.ListingResponse;

import javax.annotation.Nullable;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    String BASE_URL = "https://oauth.reddit.com";

    @GET("/best")
    Single<ListingResponse> getBestPosts(
            @Nullable @Query("after") String after
    );

}
