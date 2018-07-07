package net.epictimes.reddit.di;

import net.epictimes.reddit.RedditApp;
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
        LocalDataSourceModule.class,
        RemoteDataSourceModule.class,
        UserRepositoryModule.class})
public interface SingletonComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(RedditApp application);

        SingletonComponent build();
    }

    void inject(RedditApp application);


}
