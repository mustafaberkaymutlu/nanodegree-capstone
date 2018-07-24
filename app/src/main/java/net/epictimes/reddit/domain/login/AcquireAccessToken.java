package net.epictimes.reddit.domain.login;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.UserRepository;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Single;
import polanski.option.Option;

public class AcquireAccessToken implements Interactor.RequestInteractor<AccessTokenEntity, Object> {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public AcquireAccessToken(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public Single<Object> getSingle(@Nonnull Option<AccessTokenEntity> params) {
        return userRepository.accessToken(OptionUtils.someOrThrow(params));
    }
}
