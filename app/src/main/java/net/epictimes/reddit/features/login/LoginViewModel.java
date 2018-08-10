package net.epictimes.reddit.features.login;

import net.epictimes.reddit.domain.login.AccessTokenEntity;
import net.epictimes.reddit.domain.login.AcquireAccessToken;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.SingleLiveEvent;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;
import polanski.option.Unit;

public class LoginViewModel extends BaseViewModel {

    final SingleLiveEvent<AlertViewEntity> alertEvent = new SingleLiveEvent<>();
    final SingleLiveEvent<Unit> loginCompleteEvent = new SingleLiveEvent<>();

    private final AcquireAccessToken acquireAccessToken;
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Inject
    LoginViewModel(AcquireAccessToken acquireAccessToken, AlertViewEntityMapper alertViewEntityMapper) {
        this.acquireAccessToken = acquireAccessToken;
        this.alertViewEntityMapper = alertViewEntityMapper;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        // no-op
    }

    public void onUserLoggedIn(String code) {
        lifecycleDisposable.add(getAccessTokenBehaviorSingle(code));
    }

    private Disposable getAccessTokenBehaviorSingle(String code) {
        return acquireAccessToken
                .getSingle(Option.ofObj(new AccessTokenEntity(code)))
                .subscribe(__ -> loginCompleteEvent.postValue(Unit.DEFAULT), this::showError);
    }

    private void showError(Throwable throwable) {
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        alertEvent.postValue(alertViewEntity);
    }
}
