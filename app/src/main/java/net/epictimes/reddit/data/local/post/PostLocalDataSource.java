package net.epictimes.reddit.data.local.post;

import net.epictimes.reddit.data.PostDataSource;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.di.qualifier.LocalDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@LocalDataSource
@Singleton
public class PostLocalDataSource implements PostDataSource {

    private final PostDao postDao;

    @Inject
    public PostLocalDataSource(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public Flowable<Listing> getBestPosts() {
        // TODO implement
        return null;
    }
}
