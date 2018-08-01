package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.model.listing.ListingResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Services {

    String BASE_URL = "https://oauth.reddit.com";

    @GET("/best")
    Single<ListingResponse> getBestPosts(
            @Nullable @Query("after") String after
    );

    @GET("/by_id/names")
    Observable<ListingResponse> getPost(
            @Nullable @Query("names") String commaSeparatedFullNames
    );

    @FormUrlEncoded
    @POST("/api/vote")
    Completable vote(
            @Nonnull @Field("id") String id,
            @Nonnull @Field("rank") String rank,
            @Nonnull @Field("dir") String direction
    );

}
