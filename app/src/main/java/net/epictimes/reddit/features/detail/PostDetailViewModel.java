package net.epictimes.reddit.features.detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.domain.posts.GetPost;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;
import timber.log.Timber;

public class PostDetailViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<PostDetailViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @Nonnull
    private final GetPost getPost;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Nonnull
    private final String postId;

    @Inject
    PostDetailViewModel(@Nonnull GetPost getPost,
                        @Nonnull AlertViewEntityMapper alertViewEntityMapper,
                        @Nonnull String postId) {
        this.getPost = getPost;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.postId = postId;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(getPostBehaviorStream());
    }

    @NonNull
    private Disposable getPostBehaviorStream() {
        return getPost
                .getBehaviorStream(Option.ofObj(postId))
                .subscribe(post -> viewEntityLiveData.postValue(new PostDetailViewEntity.Content(post)),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        viewEntityLiveData.postValue(new PostDetailViewEntity.Error(alertViewEntity));
    }
}
