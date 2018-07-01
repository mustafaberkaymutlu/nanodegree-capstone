package net.epictimes.reddit;

import android.app.Activity;
import android.app.Application;

import net.epictimes.reddit.di.DaggerSingletonComponent;
import net.epictimes.reddit.di.SingletonComponent;
import net.epictimes.reddit.util.ReleaseLoggingTree;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class RedditApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initSingletonComponent();
    }

    private void initSingletonComponent() {
        DaggerSingletonComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    private void initTimber() {
        final Timber.Tree tree = BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new ReleaseLoggingTree();

        Timber.plant(tree);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
