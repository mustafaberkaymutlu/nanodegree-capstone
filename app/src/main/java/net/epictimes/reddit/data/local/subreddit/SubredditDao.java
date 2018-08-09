package net.epictimes.reddit.data.local.subreddit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.epictimes.reddit.data.model.subreddit.Subreddit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface SubredditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Subreddit> subreddits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Subreddit subreddit);

    @Query("SELECT * from Subreddit where displayName = :subredditName")
    Flowable<Subreddit> getSubreddit(String subredditName);

    @Query("SELECT * from Subreddit where displayName = :subredditName")
    Maybe<Subreddit> getSubredditMaybe(String subredditName);

}
