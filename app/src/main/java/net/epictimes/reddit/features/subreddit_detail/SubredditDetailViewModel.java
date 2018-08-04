package net.epictimes.reddit.features.subreddit_detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.domain.subreddit.RetrieveSubreddit;
import net.epictimes.reddit.features.BaseViewModel;
import net.epictimes.reddit.features.SingleLiveEvent;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    private final String subredditName;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Inject
    public SubredditDetailViewModel(@NonNull RetrieveSubreddit retrieveSubreddit,
                                    @SubredditName @NonNull String subredditName,
                                    @Nonnull AlertViewEntityMapper alertViewEntityMapper) {
        this.retrieveSubreddit = retrieveSubreddit;
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
                .subscribe(subreddit ->
                                viewEntityLiveData.postValue(new SubredditDetailViewEntity(subreddit)),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        alertViewEntitySingleLiveEvent.postValue(alertViewEntity);
    }
}
