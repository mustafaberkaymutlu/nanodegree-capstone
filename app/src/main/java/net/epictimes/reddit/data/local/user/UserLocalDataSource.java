package net.epictimes.reddit.data.local.user;

import net.epictimes.reddit.data.UserDataSource;
import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.login.AccessTokenRequest;
import net.epictimes.reddit.di.qualifier.LocalDataSource;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@LocalDataSource
public class UserLocalDataSource implements UserDataSource {

    private final UserDao userDao;

    @Inject
    public UserLocalDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Single<AccessToken> accessToken(AccessTokenRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<Object> saveAccessToken(AccessToken accessToken) {
        return Single.fromCallable(() -> {
            userDao.insert(accessToken);
            return new Object();
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<AccessToken> getAccessToken() {
        return userDao
                .getAccessToken()
                .subscribeOn(Schedulers.io());
    }


}
