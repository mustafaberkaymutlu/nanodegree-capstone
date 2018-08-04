package net.epictimes.reddit.data.local.post;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.di.qualifier.LocalDataSource;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@LocalDataSource
@Singleton
public class PostLocalDataSource implements PostDataSource {

    @NonNull
    private final PostDao postDao;

    @Inject
    PostLocalDataSource(@NonNull PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public Flowable<Listing> getBestPosts(String after) {
        // TODO implement
        return null;
    }

    @Override
    public Completable savePosts(List<Post> posts) {
        return Completable
                .fromAction(() -> postDao.insert(posts))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<Post> getPost(@Nonnull String postId) {
        return postDao
                .getPost(postId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable savePost(@Nonnull Post post) {
        return Completable
                .fromAction(() -> postDao.insert(post))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable vote(@NonNull String id, String voteDirection) {
        throw new UnsupportedOperationException();
    }
}
