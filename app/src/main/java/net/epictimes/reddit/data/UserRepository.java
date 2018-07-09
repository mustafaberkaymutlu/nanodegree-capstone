package net.epictimes.reddit.data;

import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;
import net.epictimes.reddit.data.remote.AuthInterceptor;
import net.epictimes.reddit.data.remote.AuthorizationServices;
import net.epictimes.reddit.di.qualifier.LocalDataSource;
import net.epictimes.reddit.di.qualifier.RemoteDataSource;
import net.epictimes.reddit.domain.login.AccessTokenEntity;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import timber.log.Timber;

public class UserRepository {

    private final UserDataSource remoteDataSource;
    private final UserDataSource localDataSource;

    private final AuthInterceptor authInterceptor;

    @Inject
    public UserRepository(@RemoteDataSource UserDataSource userDataSource,
                          @LocalDataSource UserDataSource localDataSource,
                          AuthInterceptor authInterceptor) {
        this.remoteDataSource = userDataSource;
        this.localDataSource = localDataSource;
        this.authInterceptor = authInterceptor;
    }

    public Maybe<AccessToken> getAccessToken() {
        return localDataSource
                .getAccessToken()
                .doOnSuccess(authInterceptor::setAccessToken);
    }

    public Single<Boolean> isUserLoggedIn() {
        return getAccessToken()
                .doOnError(Timber::e)
                .flatMapSingle(accessToken -> Single.just(true))
                .onErrorResumeNext(Single.just(false));
    }

    public Single<Object> accessToken(AccessTokenEntity accessTokenEntity) {
        final AccessTokenRequest request = new AccessTokenRequest("authorization_code", accessTokenEntity.getCode(), AuthorizationServices.REDIRECT_URI);

        return remoteDataSource
                .accessToken(request)
                .flatMap(this::saveAccessToken);
    }

    private Single<Object> saveAccessToken(AccessToken accessToken) {
        return localDataSource.saveAccessToken(accessToken);
    }


}
