package net.epictimes.reddit.domain.subreddit;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.SubredditRepository;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.domain.Interactor;
import net.epictimes.reddit.util.OptionUtils;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;
import polanski.option.Option;

public class RetrieveSearchSubreddit implements Interactor.RetrieveInteractor<String, List<SubredditSearch>> {

    @NonNull
    private final SubredditRepository subredditRepository;

    @Inject
    public RetrieveSearchSubreddit(@NonNull SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    @NonNull
    @Override
    public Flowable<List<SubredditSearch>> getBehaviorStream(@Nonnull Option<String> params) {
        final String query = OptionUtils.someOrThrow(params);
        return subredditRepository.search(query);
    }
}
