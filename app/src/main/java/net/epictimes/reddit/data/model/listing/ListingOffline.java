package net.epictimes.reddit.data.model.listing;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.post.Post;

@Entity(foreignKeys = @ForeignKey(entity = Post.class,
        parentColumns = "id",
        childColumns = "postId"),
        indices = {@Index("id")})
public class ListingOffline {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String postId;

    public ListingOffline(@NonNull String postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPostId() {
        return postId;
    }

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
    }
}
