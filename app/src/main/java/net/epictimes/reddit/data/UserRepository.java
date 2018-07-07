package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;
import net.epictimes.reddit.data.remote.Services;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;
import net.epictimes.reddit.domain.login.AccessTokenEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class UserRepository {

    private final UserDataSource remoteDataSource;

    @Inject
    public UserRepository(@RemoteDataSource UserDataSource userDataSource) {
        this.remoteDataSource = userDataSource;
    }

    public Single<Boolean> isUserLoggedIn() {
        return Single.just(false);
    }

    public Single<Void> accessToken(AccessTokenEntity accessTokenEntity) {
        final AccessTokenRequest request = new AccessTokenRequest("authorization_code", accessTokenEntity.getCode(), Services.REDIRECT_URI);

        return remoteDataSource
                .accessToken(request)
                .flatMap((Function<AccessToken, SingleSource<Void>>) accessToken -> {
                    // TODO save for offline
                    return Single.never();
                });
    }


}
