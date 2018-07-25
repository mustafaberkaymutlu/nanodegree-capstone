package net.epictimes.reddit.data;

import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface PostDataSource {

    Flowable<Listing> getBestPosts(@Nullable String after);

    Completable savePosts(List<Post> posts);

    Maybe<Post> getPost(@Nonnull String postId);

}
