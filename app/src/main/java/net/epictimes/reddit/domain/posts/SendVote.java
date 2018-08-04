package net.epictimes.reddit.domain.posts;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.PostRepository;
import net.epictimes.reddit.data.model.vote.Vote;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Completable;
import polanski.option.Option;

public class SendVote implements Interactor.SendInteractor<SendVote.Params> {

    @NonNull
    private final PostRepository postRepository;

    @Inject
    public SendVote(@NonNull PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @NonNull
    @Override
    public Completable getCompletable(@Nonnull Option<Params> paramsOption) {
        final Params params = OptionUtils.someOrThrow(paramsOption);
        return postRepository.sendVote(params.id, params.vote);
    }

    public static class Params {

        @NonNull
        private final String id;

        @NonNull
        private final Vote vote;

        public Params(@NonNull String id, @NonNull Vote vote) {
            this.id = id;
            this.vote = vote;
        }
    }
}
