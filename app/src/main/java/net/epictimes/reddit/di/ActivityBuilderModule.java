package net.epictimes.reddit.di;

import net.epictimes.reddit.di.scope.ActivityScoped;
import net.epictimes.reddit.features.feed.FeedActivity;
import net.epictimes.reddit.features.image_detail.ImageDetailActivity;
import net.epictimes.reddit.features.image_detail.ImageDetailModule;
import net.epictimes.reddit.features.login.LoginActivity;
import net.epictimes.reddit.features.post_detail.PostDetailActivity;
import net.epictimes.reddit.features.post_detail.PostDetailModule;
import net.epictimes.reddit.features.video_detail.VideoDetailActivity;

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

    @ActivityScoped
    @ContributesAndroidInjector(modules = PostDetailModule.class)
    abstract PostDetailActivity contributePostDetailActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ImageDetailModule.class)
    abstract ImageDetailActivity contributeImageDetailActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract VideoDetailActivity contributeVideoDetailActivityInjector();

}
