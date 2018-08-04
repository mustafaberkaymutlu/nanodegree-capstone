package net.epictimes.reddit.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.epictimes.reddit.data.local.post.PostDao;
import net.epictimes.reddit.data.local.subreddit.SubredditDao;
import net.epictimes.reddit.data.local.user.UserDao;
import net.epictimes.reddit.data.model.login.AccessToken;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.data.model.subreddit.Subreddit;

@Database(entities = {AccessToken.class, Post.class, Subreddit.class}, version = 1)
abstract class RedditDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "reddit-db";

    abstract PostDao postDao();

    abstract UserDao userDao();

    abstract SubredditDao subredditDao();

}
