package net.epictimes.reddit.di;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.features.widget.RedditWidgetProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BroadcastReceiverBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract RedditWidgetProvider contributeRedditWidgetProviderInjector();

}
