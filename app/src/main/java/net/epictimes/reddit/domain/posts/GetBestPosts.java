package net.epictimes.reddit.domain.posts;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostRepository;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.domain.Interactor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Flowable;
import polanski.option.Option;

public class GetBestPosts implements Interactor.RetrieveInteractor<GetBestPosts.Params, Listing> {

    @Nonnull
    private final PostRepository postRepository;

    @Inject
    public GetBestPosts(@Nonnull PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @NonNull
    @Override
    public Flowable<Listing> getBehaviorStream(@Nonnull Option<Params> params) {
        final String after = params.match(params1 -> "t3_" + params1.lastPostId, () -> null);
        return postRepository.getBestPosts(after);
    }

    public static class Params {
        @Nullable
        private final String lastPostId;

        public Params(@Nullable String lastPostId) {
            this.lastPostId = lastPostId;
        }

    }
}
