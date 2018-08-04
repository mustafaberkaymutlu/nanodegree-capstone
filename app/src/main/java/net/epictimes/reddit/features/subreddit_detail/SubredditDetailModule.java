package net.epictimes.reddit.features.subreddit_detail;

import android.os.Bundle;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.util.Preconditions;

import dagger.Module;
import dagger.Provides;

@Module
public class SubredditDetailModule {

    @SubredditName
    @ActivityScoped
    @Provides
    String provideSubredditName(SubredditDetailActivity activity) {
        final Bundle extras = activity.getIntent().getExtras();
        Preconditions.checkNotNull(extras);
        return extras.getString(SubredditDetailActivity.KEY_SUBREDDIT_NAME);
    }

}
