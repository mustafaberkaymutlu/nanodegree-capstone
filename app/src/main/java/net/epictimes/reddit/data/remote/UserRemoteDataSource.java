package net.epictimes.reddit.data.remote;

import net.epictimes.reddit.data.UserDataSource;
import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

@RemoteDataSource
public class UserRemoteDataSource implements UserDataSource {

    private final Services services;

    @Inject
    public UserRemoteDataSource(Services services) {
        this.services = services;
    }

    @Override
    public Single<AccessToken> accessToken(AccessTokenRequest request) {
        final String auth = Credentials.basic(Services.CLIENT_ID, "");
        return services
                .accessToken(auth, request.getGrantType(), request.getCode(), request.getRedirectUri())
                .map(response -> new AccessToken(response.getAccessToken(), response.getTokenType(), response.getExpiresIn()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Object> saveAccessToken(AccessToken accessToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<AccessToken> getAccessToken() {
        throw new UnsupportedOperationException();
    }
}
