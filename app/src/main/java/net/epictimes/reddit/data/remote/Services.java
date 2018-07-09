package net.epictimes.reddit.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Services {

    String BASE_URL = "https://oauth.reddit.com";

    @GET("/subreddits/popular")
    Single<Object> getPopularSubreddits();

}
