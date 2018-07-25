package net.epictimes.reddit.domain.posts;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostRepository;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import polanski.option.Option;

public class GetPost implements Interactor.RetrieveInteractor<String, Post> {

    @Nonnull
    private final PostRepository postRepository;

    @Inject
    public GetPost(@Nonnull PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @NonNull
    @Override
    public Flowable<Post> getBehaviorStream(@Nonnull Option<String> params) {
        final String postId = OptionUtils.someOrThrow(params);
        return postRepository.getPost(postId);
    }
}
