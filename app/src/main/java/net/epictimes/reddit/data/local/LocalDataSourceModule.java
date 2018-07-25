package net.epictimes.reddit.data.local;

import android.arch.persistence.room.Room;

import net.epictimes.reddit.RedditApp;
import net.epictimes.reddit.data.local.post.PostDao;
import net.epictimes.reddit.data.local.user.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {

    @Singleton
    @Provides
    RedditDatabase provideChameleonDatabase(RedditApp app) {
        return Room.databaseBuilder(app, RedditDatabase.class, RedditDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    PostDao providePostDao(RedditDatabase db) {
        return db.postDao();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(RedditDatabase db) {
        return db.userDao();
    }

}
