package net.epictimes.reddit.features.subreddit_detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.domain.subreddit.RetrieveSubreddit;
import net.epictimes.reddit.domain.subreddit.SendSubscription;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.SingleLiveEvent;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import polanski.option.Option;
import timber.log.Timber;

public class SubredditDetailViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<SubredditDetailViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @Nonnull
    final SingleLiveEvent<AlertViewEntity> alertViewEntitySingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final RetrieveSubreddit retrieveSubreddit;

    @NonNull
    private final SendSubscription sendSubscription;

    @NonNull
    private final String subredditName;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Inject
    public SubredditDetailViewModel(@NonNull RetrieveSubreddit retrieveSubreddit,
                                    @NonNull SendSubscription sendSubscription,
                                    @SubredditName @NonNull String subredditName,
                                    @Nonnull AlertViewEntityMapper alertViewEntityMapper) {
        this.retrieveSubreddit = retrieveSubreddit;
        this.sendSubscription = sendSubscription;
        this.subredditName = subredditName;
        this.alertViewEntityMapper = alertViewEntityMapper;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(subscribeRetrievingSubreddit());
    }

    private Disposable subscribeRetrievingSubreddit() {
        return retrieveSubreddit
                .getBehaviorStream(Option.ofObj(subredditName))
                .subscribe(displaySubreddit(),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        alertViewEntitySingleLiveEvent.postValue(alertViewEntity);
    }

    public void onSubscribeButtonClicked() {
        final SubredditDetailViewEntity viewEntity = viewEntityLiveData.getValue();
        if (viewEntity == null) return;

        lifecycleDisposable.add(subscribeSendingSubscription(viewEntity.getSubreddit()));
    }

    private Disposable subscribeSendingSubscription(Subreddit subreddit) {
        final SendSubscription.Action action = subreddit.isUserIsSubscriber()
                ? SendSubscription.Action.UNSUBSCRIBE
                : SendSubscription.Action.SUBSCRIBE;

        return sendSubscription
                .getCompletable(Option.ofObj(new SendSubscription.Params(subreddit.getDisplayName(), action)))
                .andThen(retrieveSubreddit.getBehaviorStream(Option.ofObj(subredditName)))
                .subscribe(displaySubreddit(),
                        this::showError);
    }

    @NonNull
    private Consumer<Subreddit> displaySubreddit() {
        return subreddit -> viewEntityLiveData.postValue(new SubredditDetailViewEntity(subreddit));
    }
}
