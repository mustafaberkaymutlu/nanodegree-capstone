package net.epictimes.reddit.features.search;

import net.epictimes.reddit.data.model.subreddit_search.SubredditSearch;

import java.util.List;

public class SearchViewEntity {

    private final List<SubredditSearch> results;

    SearchViewEntity(List<SubredditSearch> results) {
        this.results = results;
    }

    public List<SubredditSearch> getResults() {
        return results;
    }
}
