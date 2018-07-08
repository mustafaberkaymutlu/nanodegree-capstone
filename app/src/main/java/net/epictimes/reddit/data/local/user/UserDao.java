package net.epictimes.reddit.data.local.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.epictimes.reddit.data.model.login.AccessToken;

import io.reactivex.Maybe;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AccessToken accessToken);

    @Query("SELECT * from AccessToken where id = 0")
    Maybe<AccessToken> getAccessToken();

}
