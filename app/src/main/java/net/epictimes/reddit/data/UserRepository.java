package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;
import net.epictimes.reddit.data.remote.Services;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;
import net.epictimes.reddit.domain.login.AccessTokenEntity;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class UserRepository {

    private final UserDataSource remoteDataSource;

    private final UserDataSource localDataSource;

    @Inject
    public UserRepository(@RemoteDataSource UserDataSource userDataSource,
                          @LocalDataSource UserDataSource localDataSource) {
        this.remoteDataSource = userDataSource;
        this.localDataSource = localDataSource;
    }

    public Single<Boolean> isUserLoggedIn() {
        return localDataSource
                .getAccessToken()
                .flatMapSingle(accessToken -> Single.just(true))
                .doOnError(Timber::e)
                .onErrorResumeNext(Single.just(false));
    }

    public Single<Object> accessToken(AccessTokenEntity accessTokenEntity) {
        final AccessTokenRequest request = new AccessTokenRequest("authorization_code", accessTokenEntity.getCode(), Services.REDIRECT_URI);

        return remoteDataSource
                .accessToken(request)
                .flatMap(this::saveAccessToken);
    }

    private Single<Object> saveAccessToken(AccessToken accessToken) {
        return localDataSource.saveAccessToken(accessToken);
    }


}
