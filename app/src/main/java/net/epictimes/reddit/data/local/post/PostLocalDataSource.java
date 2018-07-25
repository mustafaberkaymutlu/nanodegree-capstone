package net.epictimes.reddit.data.local.post;

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
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

@LocalDataSource
@Singleton
public class PostLocalDataSource implements PostDataSource {

    private final PostDao postDao;

    @Inject
    public PostLocalDataSource(PostDao postDao) {
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
    public Maybe<Post> getPost(@Nonnull String postId) {
        return postDao
                .getPost(postId)
                .subscribeOn(Schedulers.io());
    }
}
