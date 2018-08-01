package net.epictimes.reddit.data.local.post;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.epictimes.reddit.data.model.post.Post;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Query("SELECT * from Post where id = :postId")
    Flowable<Post> getPost(String postId);

}
