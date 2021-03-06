package net.epictimes.reddit.di;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.epictimes.reddit.RedditApp;
import net.epictimes.reddit.features.alert.AlertViewEntityMapper;
import net.epictimes.reddit.util.StringProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SingletonModule {

    @Singleton
    @Provides
    StringProvider provideStringProvider(RedditApp redditApp) {
        return new StringProvider(redditApp.getApplicationContext());
    }

    @Singleton
    @Provides
    AlertViewEntityMapper provideAlertViewEntityMapper(StringProvider stringProvider) {
        return new AlertViewEntityMapper(stringProvider);
    }

    @Singleton
    @Provides
    FirebaseAnalytics provideFirebaseAnalytics(RedditApp redditApp) {
        return FirebaseAnalytics.getInstance(redditApp);
    }

}
