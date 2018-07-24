package net.epictimes.reddit.domain.login;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.UserRepository;
import net.epictimes.reddit.domain.Interactor;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Single;
import polanski.option.Option;
import polanski.option.Unit;

public class IsUserLoggedIn implements Interactor.RequestInteractor<Unit, Boolean> {

    private final UserRepository userRepository;

    @Inject
    public IsUserLoggedIn(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public Single<Boolean> getSingle(@Nonnull Option<Unit> params) {
        return userRepository.isUserLoggedIn();
    }
}
