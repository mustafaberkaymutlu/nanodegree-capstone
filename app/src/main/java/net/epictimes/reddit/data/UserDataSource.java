package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;

import io.reactivex.Single;

public interface UserDataSource {

    Single<AccessToken> accessToken(AccessTokenRequest request);

}
