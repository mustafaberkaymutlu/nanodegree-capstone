package net.epictimes.reddit.di;

import net.epictimes.reddit.RedditApp;
import net.epictimes.reddit.data.ListingRepositoryModule;
import net.epictimes.reddit.data.PostRepositoryModule;
import net.epictimes.reddit.data.SubredditRepositoryModule;
import net.epictimes.reddit.data.UserRepositoryModule;
import net.epictimes.reddit.data.local.LocalDataSourceModule;
import net.epictimes.reddit.data.remote.RemoteDataSourceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {SingletonModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        BroadcastReceiverBuilderModule.class,
        ServiceBuilderModule.class,
        LocalDataSourceModule.class,
        RemoteDataSourceModule.class,
        UserRepositoryModule.class,
        PostRepositoryModule.class,
        SubredditRepositoryModule.class,
        ListingRepositoryModule.class})
public interface SingletonComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(RedditApp application);

        SingletonComponent build();
    }

    void inject(RedditApp application);


}
