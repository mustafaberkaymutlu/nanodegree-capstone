package net.epictimes.reddit.features.search;

import android.support.annotation.NonNull;

import net.epictimes.reddit.features.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchViewModel extends BaseViewModel {

    @NonNull
    private final String searchQuery;

    @Inject
    SearchViewModel(@SearchQuery @NonNull String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {

    }
}
