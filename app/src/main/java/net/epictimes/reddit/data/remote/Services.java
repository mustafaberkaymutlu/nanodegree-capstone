package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.model.login.AccessTokenResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Services {
    String CLIENT_ID = "put-your-client-id-here";

    String BASE_URL = "https://www.reddit.com";
    String REDIRECT_URI = "reddit://epictimes.net";

    String SCOPES = "identity,mysubreddits,read,subscribe,vote";

    String AUTHORIZATION_URL = "https://www.reddit.com/api/v1/authorize";

    @FormUrlEncoded
    @POST("api/v1/access_token")
    Single<AccessTokenResponse> accessToken(@Header("Authorization") String authorization,
                                            @Field("grant_type") String grantType,
                                            @Field("code") String code,
                                            @Field("redirect_uri") String redirectUri);
}
