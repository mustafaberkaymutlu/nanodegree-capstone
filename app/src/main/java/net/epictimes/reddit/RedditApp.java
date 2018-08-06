package net.epictimes.reddit;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;

import com.facebook.stetho.Stetho;

import net.epictimes.reddit.di.DaggerSingletonComponent;
import net.epictimes.reddit.util.ReleaseLoggingTree;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

public class RedditApp extends Application implements
        HasActivityInjector,
        HasServiceInjector,
        HasBroadcastReceiverInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> dispatchingBroadcastReceiverInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initSingletonComponent();
        Stetho.initializeWithDefaults(this);
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
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return dispatchingBroadcastReceiverInjector;
    }
}
