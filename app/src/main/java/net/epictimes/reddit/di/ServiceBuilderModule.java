package net.epictimes.reddit.di;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.features.widget.RedditRemoteViewsService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ServiceBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract RedditRemoteViewsService contributeRedditRemoteViewsServiceInjector();

}