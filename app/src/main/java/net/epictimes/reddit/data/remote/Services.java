package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.listing.ListingResponse;
import net.epictimes.reddit.data.model.subreddit.SubredditResponse;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    String BASE_URL = "https://oauth.reddit.com";

    @GET("/best")
    Flowable<ListingResponse> getBestPosts(
            @Nullable @Query("after") String after
    );

    @GET("/by_id/names")
    Flowable<ListingResponse> getPost(
            @NonNull @Query("names") String commaSeparatedFullNames
    );

    @FormUrlEncoded
    @POST("/api/vote")
    Completable vote(
            @Nonnull @Field("id") String id,
            @Nonnull @Field("rank") String rank,
            @Nonnull @Field("dir") String direction
    );

    @GET("/r/{subreddit-name}/about")
    Flowable<SubredditResponse> getSubreddit(
            @Nullable @Path("subreddit-name") String subredditName
    );

    @FormUrlEncoded
    @POST("/api/subscribe")
    Completable sendSubscription(
            @NonNull @Field("action") String action,
            @Field("skip_initial_defaults") boolean skipInitialDefaults,
            @NonNull @Field("sr_name") String subredditName
    );

    @FormUrlEncoded
    @POST("/api/search_subreddits")
    Flowable<SubredditSearchResponse> searchSubreddits(
            @Field("exact") boolean exact,
            @Field("include_over_18") boolean includeOver18,
            @Field("include_unadvertisable") boolean includeUnadvertisable,
            @NonNull @Field("query") String query
    );

}
