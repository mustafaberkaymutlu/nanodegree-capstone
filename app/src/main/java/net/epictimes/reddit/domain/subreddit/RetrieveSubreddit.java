package net.epictimes.reddit.domain.subreddit;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditRepository;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import polanski.option.Option;

public class RetrieveSubreddit implements Interactor.RetrieveInteractor<String, Subreddit> {

    @NonNull
    private final SubredditRepository subredditRepository;

    @Inject
    public RetrieveSubreddit(@NonNull SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @NonNull
    @Override
    public Flowable<Subreddit> getBehaviorStream(@Nonnull Option<String> params) {
        final String subreddit = OptionUtils.someOrThrow(params);
        return subredditRepository.retrieveSubreddit(subreddit);
    }
}
