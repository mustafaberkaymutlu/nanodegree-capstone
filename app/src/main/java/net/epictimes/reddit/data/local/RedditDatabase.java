package net.epictimes.reddit.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

//@Database(entities = {}, version = 1)
abstract class RedditDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "reddit-db";

    abstract PostDao postDao();

}
