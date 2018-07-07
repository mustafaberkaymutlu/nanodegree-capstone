package net.epictimes.reddit.domain.login;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.UserRepository;
import net.epictimes.reddit.domain.Interactor;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Single;

public class IsUserLoggedIn implements Interactor.RequestInteractor<Void, Boolean> {

    private final UserRepository userRepository;

    @Inject
    public IsUserLoggedIn(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public Single<Boolean> getSingle(@Nullable Void aVoid) {
        return userRepository.isUserLoggedIn();
    }
}
