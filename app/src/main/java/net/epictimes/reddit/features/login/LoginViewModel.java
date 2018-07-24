package net.epictimes.reddit.features.login;

import android.arch.lifecycle.MutableLiveData;

import net.epictimes.reddit.domain.login.AccessTokenEntity;
import net.epictimes.reddit.domain.login.AcquireAccessToken;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;

public class LoginViewModel extends BaseViewModel {

    final MutableLiveData<LoginViewEntity> viewEntityLiveData = new MutableLiveData<>();

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
                .doOnSubscribe(disposable -> viewEntityLiveData.postValue(new LoginViewEntity.Loading()))
                .subscribe(__ -> viewEntityLiveData.postValue(new LoginViewEntity.LoginComplete()),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        final LoginViewEntity.Error loginViewEntity = new LoginViewEntity.Error(alertViewEntity);
        viewEntityLiveData.postValue(loginViewEntity);
    }
}
