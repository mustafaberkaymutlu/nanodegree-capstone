package net.epictimes.reddit.features.search;

import android.app.SearchManager;
import android.os.Bundle;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.util.Preconditions;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @SearchQuery
    @ActivityScoped
    @Provides
    String provideSearchQuery(SearchActivity activity) {
        final Bundle extras = Preconditions.checkNotNull(activity.getIntent().getExtras());
        return extras.getString(SearchManager.QUERY);
    }

}
