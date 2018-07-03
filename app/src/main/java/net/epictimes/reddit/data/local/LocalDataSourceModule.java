package net.epictimes.reddit.data.local;

import android.arch.persistence.room.Room;

import net.epictimes.reddit.RedditApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {

    @Singleton
    @Provides
    RedditDatabase provideChameleonDatabase(RedditApp app) {
        return Room.databaseBuilder(app, RedditDatabase.class, RedditDatabase.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    PostDao providePhotoDao(RedditDatabase chameleonDatabase) {
        return chameleonDatabase.postDao();
    }

}
