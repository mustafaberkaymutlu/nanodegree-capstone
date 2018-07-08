package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface UserDataSource {

    Single<AccessToken> accessToken(AccessTokenRequest request);

    Single<Object> saveAccessToken(AccessToken accessToken);

    Maybe<AccessToken> getAccessToken();

}
