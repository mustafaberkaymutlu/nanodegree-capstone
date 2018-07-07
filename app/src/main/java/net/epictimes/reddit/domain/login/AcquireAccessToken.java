package net.epictimes.reddit.domain.login;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.UserRepository;
import net.epictimes.reddit.domain.Interactor;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AcquireAccessToken implements Interactor.RequestInteractor<AccessTokenEntity, Void> {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public AcquireAccessToken(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public Single<Void> getSingle(@Nullable AccessTokenEntity accessTokenEntity) {
        return userRepository.accessToken(accessTokenEntity);
    }
}
