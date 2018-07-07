package net.epictimes.reddit.di;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.features.feed.FeedActivity;
import net.epictimes.reddit.features.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract FeedActivity contributeFeedActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LoginActivity contributeLoginActivityInjector();

}
