package net.epictimes.reddit.data.local.post;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.epictimes.reddit.data.model.post.Post;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Post> posts);

    @Query("SELECT * from Post where id = :postId")
    Maybe<Post> getPost(String postId);

}
