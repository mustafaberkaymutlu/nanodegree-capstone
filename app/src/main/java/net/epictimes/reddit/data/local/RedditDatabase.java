package net.epictimes.reddit.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.epictimes.reddit.data.local.post.PostDao;
import net.epictimes.reddit.data.local.user.UserDao;
import net.epictimes.reddit.data.model.login.AccessToken;

@Database(entities = {AccessToken.class}, version = 1)
abstract class RedditDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "reddit-db";

    abstract PostDao postDao();

    abstract UserDao userDao();

}
