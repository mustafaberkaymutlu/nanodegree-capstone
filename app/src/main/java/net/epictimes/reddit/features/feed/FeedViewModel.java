package net.epictimes.reddit.features.feed;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.domain.login.IsUserLoggedIn;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FeedViewModel extends BaseViewModel {

    MutableLiveData<FeedViewEntity> viewEntityLiveData = new MutableLiveData<>();

    private final IsUserLoggedIn isUserLoggedIn;
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Inject
    FeedViewModel(IsUserLoggedIn isUserLoggedIn,
                  AlertViewEntityMapper alertViewEntityMapper) {
        this.isUserLoggedIn = isUserLoggedIn;
        this.alertViewEntityMapper = alertViewEntityMapper;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(getIsUserLoggedInBehaviorSingle());
    }

    @NonNull
    private Disposable getIsUserLoggedInBehaviorSingle() {
        return isUserLoggedIn
                .getSingle(null)
                .subscribe(aBoolean -> {
                            viewEntityLiveData.postValue(new FeedViewEntity.UserNotLoggedIn());
                        },
                        throwable -> {
                            final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
                            viewEntityLiveData.postValue(new FeedViewEntity.Error(alertViewEntity));
                        });
    }


}
