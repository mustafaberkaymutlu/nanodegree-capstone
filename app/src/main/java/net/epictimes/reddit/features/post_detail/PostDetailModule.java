package net.epictimes.reddit.features.post_detail;

import android.os.Bundle;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.util.Preconditions;

import dagger.Module;
import dagger.Provides;

@Module
public class PostDetailModule {

    @PostId
    @ActivityScoped
    @Provides
    String providePostId(PostDetailActivity activity) {
        final Bundle extras = Preconditions.checkNotNull(activity.getIntent().getExtras());
        return extras.getString(PostDetailActivity.KEY_POST_ID);
    }

}
