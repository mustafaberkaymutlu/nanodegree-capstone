package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.model.login.AccessTokenResponse;

import javax.annotation.Nonnull;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthorizationServices {
    String CLIENT_ID = "put-your-api-key-here";

    String BASE_URL = "https://www.reddit.com";
    String REDIRECT_URI = "reddit://epictimes.net";

    String SCOPES = "identity,mysubreddits,read,subscribe,vote";

    String AUTHORIZATION_URL = "https://www.reddit.com/api/v1/authorize";

    @FormUrlEncoded
    @POST("api/v1/access_token")
    Single<AccessTokenResponse> accessToken(@Nonnull @Header("Authorization") String authorization,
                                            @Nonnull @Field("grant_type") String grantType,
                                            @Nonnull @Field("code") String code,
                                            @Nonnull @Field("redirect_uri") String redirectUri);
}
