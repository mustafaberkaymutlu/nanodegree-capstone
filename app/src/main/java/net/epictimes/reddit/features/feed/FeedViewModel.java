package net.epictimes.reddit.features.feed;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.domain.login.IsUserLoggedIn;
import net.epictimes.reddit.domain.posts.GetBestPosts;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.LoadingViewEntity;
import net.epictimes.reddit.features.SingleLiveEvent;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;
import polanski.option.Unit;
import timber.log.Timber;

public class FeedViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<Unit> userNotLoggedInLiveData = new MutableLiveData<>();

    @Nonnull
    final MutableLiveData<FeedViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @Nonnull
    final MutableLiveData<LoadingViewEntity> loadingLiveData = new MutableLiveData<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToPostDetail = new SingleLiveEvent<>();

    @Nonnull
    private final IsUserLoggedIn isUserLoggedIn;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Nonnull
    private final GetBestPosts getBestPosts;

    @Inject
    FeedViewModel(@NonNull IsUserLoggedIn isUserLoggedIn,
                  @NonNull AlertViewEntityMapper alertViewEntityMapper,
                  @NonNull GetBestPosts getBestPosts) {
        this.isUserLoggedIn = isUserLoggedIn;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.getBestPosts = getBestPosts;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(getIsUserLoggedInBehaviorSingle());
    }

    @NonNull
    private Disposable getIsUserLoggedInBehaviorSingle() {
        return isUserLoggedIn
                .getSingle(Option.none())
                .subscribe(isUserLoggedIn -> {
                            if (isUserLoggedIn) {
                                onUserLoggedIn();
                            } else {
                                userNotLoggedInLiveData.postValue(Unit.DEFAULT);
                            }
                        },
                        this::showError);
    }

    private void onUserLoggedIn() {
        lifecycleDisposable.add(getBestPostsBehaviourStream());
    }

    @NonNull
    private Disposable getBestPostsBehaviourStream() {
        return getBestPosts
                .getBehaviorStream(Option.none())
                .doOnSubscribe(subscription -> postLoading(true))
                .doOnNext(o -> postLoading(false))
                .doOnError(o -> postLoading(false))
                .doOnTerminate(() -> postLoading(false))
                .subscribe(listing -> viewEntityLiveData.postValue(new FeedViewEntity.Content(listing, false)),
                        this::showError);
    }

    @NonNull
    private Disposable getBestPostsLoadMoreBehaviourStream(String after) {
        return getBestPosts
                .getBehaviorStream(Option.ofObj(new GetBestPosts.Params(after)))
                .subscribe(listing -> viewEntityLiveData.postValue(new FeedViewEntity.Content(listing, true)),
                        this::showError);
    }

    private void postLoading(boolean isLoading) {
        loadingLiveData.postValue(new LoadingViewEntity(isLoading));
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        viewEntityLiveData.postValue(new FeedViewEntity.Error(alertViewEntity));
    }

    public void onUserRefreshed() {
        lifecycleDisposable.add(getBestPostsBehaviourStream());
    }

    public void loadMore(String lastPostId) {
        lifecycleDisposable.add(getBestPostsLoadMoreBehaviourStream(lastPostId));
    }

    public void onPostClicked(@Nonnull Post post) {
        navigateToPostDetail.setValue(post.getId());
    }
}
