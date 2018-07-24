package net.epictimes.reddit.features.feed;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.domain.login.IsUserLoggedIn;
import net.epictimes.reddit.domain.posts.GetBestPosts;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;
import polanski.option.Unit;
import timber.log.Timber;

public class FeedViewModel extends BaseViewModel {

    final MutableLiveData<Unit> userNotLoggedInLiveData = new MutableLiveData<>();
    final MutableLiveData<FeedViewEntity> viewEntityLiveData = new MutableLiveData<>();

    private final IsUserLoggedIn isUserLoggedIn;
    private final AlertViewEntityMapper alertViewEntityMapper;
    private final GetBestPosts getBestPosts;

    @Inject
    FeedViewModel(IsUserLoggedIn isUserLoggedIn,
                  AlertViewEntityMapper alertViewEntityMapper,
                  GetBestPosts getBestPosts) {
        this.isUserLoggedIn = isUserLoggedIn;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.getBestPosts = getBestPosts;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(getIsUserLoggedInBehaviorSingle());
        lifecycleDisposable.add(getPopularSubredditsBehaviourStream());
    }

    @NonNull
    private Disposable getIsUserLoggedInBehaviorSingle() {
        return isUserLoggedIn
                .getSingle(Option.none())
                .subscribe(isUserLoggedIn -> {
                            if (!isUserLoggedIn) {
                                userNotLoggedInLiveData.postValue(Unit.DEFAULT);
                            }
                        },
                        this::showError);
    }

    @NonNull
    private Disposable getPopularSubredditsBehaviourStream() {
        return getBestPosts
                .getBehaviorStream(Option.none())
                .doOnSubscribe(subscription -> postLoading(true))
                .doOnNext(o -> postLoading(false))
                .doOnError(o -> postLoading(false))
                .doOnTerminate(() -> postLoading(false))
                .subscribe(listing -> viewEntityLiveData.postValue(new FeedViewEntity.Content(listing)),
                        this::showError);
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
