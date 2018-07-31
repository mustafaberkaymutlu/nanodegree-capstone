package net.epictimes.reddit.features.image_detail;

import android.os.Bundle;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.util.Preconditions;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageDetailModule {

    @Url
    @ActivityScoped
    @Provides
    String provideUrl(ImageDetailActivity activity) {
        final Bundle extras = Preconditions.checkNotNull(activity.getIntent().getExtras());
        return extras.getString(ImageDetailActivity.KEY_URL);
    }

}
