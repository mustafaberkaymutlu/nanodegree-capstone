package net.epictimes.reddit.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface PostDataSource {

    Flowable<Listing> getBestPosts(@Nullable String after);

    Completable savePosts(List<Post> posts);

    Flowable<Post> getPost(@Nonnull String postId);

    Completable savePost(@Nonnull Post post);

    Completable vote(@NonNull String id, String voteDirection);

}
