package net.epictimes.reddit.features.feed;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.domain.login.IsUserLoggedIn;
import net.epictimes.reddit.domain.posts.RetrieveBestPosts;
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
    final SingleLiveEvent<String> navigateToPostDetailEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToImageDetailEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToVideoDetailEvent = new SingleLiveEvent<>();

    @Nonnull
    private final IsUserLoggedIn isUserLoggedIn;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Nonnull
    private final RetrieveBestPosts retrieveBestPosts;

    @Inject
    FeedViewModel(@NonNull IsUserLoggedIn isUserLoggedIn,
                  @NonNull AlertViewEntityMapper alertViewEntityMapper,
                  @NonNull RetrieveBestPosts retrieveBestPosts) {
        this.isUserLoggedIn = isUserLoggedIn;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.retrieveBestPosts = retrieveBestPosts;
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
        return retrieveBestPosts
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
        return retrieveBestPosts
                .getBehaviorStream(Option.ofObj(new RetrieveBestPosts.Params(after)))
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
        navigateToPostDetailEvent.setValue(post.getId());
    }

    public void onImageClicked(@Nonnull Post post) {
        if (post.isVideo()) {
            navigateToVideoDetailEvent.setValue(post.getVideoUrl());
        } else {
            navigateToImageDetailEvent.setValue(post.getPreviewImage());
        }
    }
}
