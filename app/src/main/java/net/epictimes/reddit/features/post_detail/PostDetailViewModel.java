package net.epictimes.reddit.features.post_detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.data.model.vote.Vote;
import net.epictimes.reddit.domain.posts.RefreshAndRetrievePost;
import net.epictimes.reddit.domain.posts.RetrievePost;
import net.epictimes.reddit.domain.posts.SendVote;
import net.epictimes.reddit.features.BaseViewModel;
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

public class PostDetailViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<PostDetailViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @Nonnull
    final MutableLiveData<AlertViewEntity> alertViewEntitySingleLiveEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToImageDetailEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToVideoDetailEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<Vote> voteEvent = new SingleLiveEvent<>();

    @Nonnull
    private final RetrievePost retrievePost;

    @Nonnull
    private final RefreshAndRetrievePost refreshAndRetrievePost;

    @NonNull
    private final SendVote sendVote;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Nonnull
    private final String postId;

    @Inject
    PostDetailViewModel(@Nonnull RetrievePost retrievePost,
                        @Nonnull RefreshAndRetrievePost refreshAndRetrievePost,
                        @NonNull SendVote sendVote,
                        @Nonnull AlertViewEntityMapper alertViewEntityMapper,
                        @PostId @Nonnull String postId) {
        this.retrievePost = retrievePost;
        this.refreshAndRetrievePost = refreshAndRetrievePost;
        this.sendVote = sendVote;
        this.alertViewEntityMapper = alertViewEntityMapper;
        this.postId = postId;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(subscribeGetPostBehaviorStream());
    }

    @NonNull
    private Disposable subscribeGetPostBehaviorStream() {
        return retrievePost
                .getBehaviorStream(Option.ofObj(postId))
                .subscribe(post -> viewEntityLiveData.postValue(new PostDetailViewEntity(post)),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        alertViewEntitySingleLiveEvent.postValue(alertViewEntity);
    }

    public void onImageClicked() {
        final PostDetailViewEntity viewEntity = viewEntityLiveData.getValue();

        if (viewEntity != null) {
            final Post post = viewEntity.getPost();

            if (post.isVideo()) {
                navigateToVideoDetailEvent.setValue(post.getVideoUrl());
            } else {
                navigateToImageDetailEvent.setValue(post.getPreviewImage());
            }
        }
    }

    public void onUpVoteClicked() {
        lifecycleDisposable.add(getSendVoteBehaviorCompletable(isUnVotedOr(Vote.UP)));
    }

    public void onDownVoteClicked() {
        lifecycleDisposable.add(getSendVoteBehaviorCompletable(isUnVotedOr(Vote.DOWN)));
    }

    @NonNull
    private Disposable getSendVoteBehaviorCompletable(Vote vote) {
        final SendVote.Params params = new SendVote.Params(postId, vote);
        return sendVote
                .getCompletable(Option.ofObj(params))
                .toSingleDefault(Unit.DEFAULT)
                .toFlowable()
                .flatMap(o -> refreshAndRetrievePost.getBehaviorStream(Option.ofObj(postId)))
                .subscribe(post -> {
                    viewEntityLiveData.postValue(new PostDetailViewEntity(post));
                    voteEvent.postValue(vote);
                }, this::showError);
    }

    private Vote isUnVotedOr(Vote newVote) {
        final Post post = viewEntityLiveData.getValue().getPost();
        final Vote previousVote = post.getVote();

        if (previousVote == newVote) return Vote.NONE;
        return newVote;
    }

}
