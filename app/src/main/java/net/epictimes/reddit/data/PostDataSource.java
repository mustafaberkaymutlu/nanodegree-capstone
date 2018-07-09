package net.epictimes.reddit.data;

import io.reactivex.Flowable;

public interface PostDataSource {

    Flowable<Object> getPopularSubreddits();

}
