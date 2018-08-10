package net.epictimes.reddit.data.remote;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchMapper;
import net.epictimes.reddit.data.model.subreddit_search.SubredditSearchResponse;

import java.util.List;

import io.reactivex.subjects.SingleSubject;

/**
 * Required AsyncTask by the project's rubric. Otherwise we could've just use the RxJava.
 */
class SearchTask extends AsyncTask<String, Void, List<SubredditSearch>> {

    @NonNull
    private final Services services;

    @NonNull
    private final SubredditSearchMapper subredditSearchMapper;

    @NonNull
    private final SingleSubject<List<SubredditSearch>> callBack;

    SearchTask(@NonNull Services services,
               @NonNull SubredditSearchMapper subredditSearchMapper,
               @NonNull SingleSubject<List<SubredditSearch>> callBack) {
        this.services = services;
        this.subredditSearchMapper = subredditSearchMapper;
        this.callBack = callBack;
    }

    @Override
    protected List<SubredditSearch> doInBackground(String... strings) {
        final String query = strings[0];

        return services
                .searchSubreddits(false, false, true, query)
                .map(SubredditSearchResponse::getSubredditsRaw)
                .flatMapIterable(it -> it)
                .map(subredditSearchMapper)
                .toList()
                .blockingGet();
    }

    @Override
    protected void onPostExecute(List<SubredditSearch> subredditSearches) {
        super.onPostExecute(subredditSearches);

        callBack.onSuccess(subredditSearches);
    }
}
