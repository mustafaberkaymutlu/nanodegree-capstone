package net.epictimes.reddit.features.feed;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.domain.login.IsUserLoggedIn;
import net.epictimes.reddit.domain.posts.GetPopularSubreddits;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class FeedViewModel extends BaseViewModel {

    final MutableLiveData<FeedViewEntity> viewEntityLiveData = new MutableLiveData<>();

    private final IsUserLoggedIn isUserLoggedIn;
    private final AlertViewEntityMapper alertViewEntityMapper;
    private final GetPopularSubreddits getPopularSubreddits;

    @Inject
    FeedViewModel(IsUserLoggedIn isUserLoggedIn,
                  AlertViewEntityMapper alertViewEntityMapper,
                  GetPopularSubreddits getPopularSubreddits) {
        this.isUserLoggedIn = isUserLoggedIn;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.getPopularSubreddits = getPopularSubreddits;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(getIsUserLoggedInBehaviorSingle());
        lifecycleDisposable.add(getPopularSubredditsBehaviourStream());
    }

    @NonNull
    private Disposable getIsUserLoggedInBehaviorSingle() {
        return isUserLoggedIn
                .getSingle(null)
                .subscribe(isUserLoggedIn -> {
                            if (!isUserLoggedIn) {
                                viewEntityLiveData.postValue(new FeedViewEntity.UserNotLoggedIn());
                            }
                        },
                        this::showError);
    }

    @NonNull
    private Disposable getPopularSubredditsBehaviourStream() {
        return getPopularSubreddits
                .getBehaviorStream(null)
                .doOnSubscribe(subscription -> postLoading(true))
                .doOnNext(o -> postLoading(false))
                .doOnTerminate(() -> postLoading(false))
                .subscribe(o -> {
                    Timber.d(o.toString());
                }, this::showError);
    }

    private void postLoading(boolean b) {
        viewEntityLiveData.postValue(new FeedViewEntity.Loading(b));
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        viewEntityLiveData.postValue(new FeedViewEntity.Error(alertViewEntity));
    }

    public void onUserRefreshed() {
        lifecycleDisposable.add(getPopularSubredditsBehaviourStream());
    }
}
