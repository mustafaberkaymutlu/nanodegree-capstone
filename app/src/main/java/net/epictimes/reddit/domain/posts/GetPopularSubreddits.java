package net.epictimes.reddit.domain.posts;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostRepository;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.domain.Interactor;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetPopularSubreddits implements Interactor.RetrieveInteractor<Object, Listing> {

    private final PostRepository postRepository;

    @Inject
    public GetPopularSubreddits(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @NonNull
    @Override
    public Flowable<Listing> getBehaviorStream(@Nullable Object o) {
        return postRepository.getPopularSubreddits();
    }
}
