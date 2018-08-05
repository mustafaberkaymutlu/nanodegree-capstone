package net.epictimes.reddit.features.search;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.domain.subreddit.RetrieveSearchSubreddit;
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

public class SearchViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<SearchViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @Nonnull
    final SingleLiveEvent<AlertViewEntity> alertViewEntitySingleLiveEvent = new SingleLiveEvent<>();

    @Nonnull
    final SingleLiveEvent<String> navigateToSubredditDetailEvent = new SingleLiveEvent<>();

    @NonNull
    private final String searchQuery;

    @NonNull
    private final RetrieveSearchSubreddit retrieveSearchSubreddit;

    @Nonnull
    private final AlertViewEntityMapper alertViewEntityMapper;

    @Inject
    SearchViewModel(@SearchQuery @NonNull String searchQuery,
                    @NonNull RetrieveSearchSubreddit retrieveSearchSubreddit,
                    @Nonnull AlertViewEntityMapper alertViewEntityMapper) {
        this.searchQuery = searchQuery;
        this.retrieveSearchSubreddit = retrieveSearchSubreddit;
        this.alertViewEntityMapper = alertViewEntityMapper;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        lifecycleDisposable.add(subscribeSearch());
    }

    private Disposable subscribeSearch() {
        return retrieveSearchSubreddit
                .getBehaviorStream(Option.ofObj(searchQuery))
                .subscribe(subreddits ->
                                viewEntityLiveData.postValue(new SearchViewEntity(subreddits)),
                        this::showError);
    }

    private void showError(Throwable throwable) {
        Timber.e(throwable);
        final AlertViewEntity alertViewEntity = alertViewEntityMapper.create(throwable);
        alertViewEntitySingleLiveEvent.postValue(alertViewEntity);
    }

    public void onSubredditClicked(@NonNull SubredditSearch subreddit) {
        navigateToSubredditDetailEvent.postValue(subreddit.getName());
    }
}
