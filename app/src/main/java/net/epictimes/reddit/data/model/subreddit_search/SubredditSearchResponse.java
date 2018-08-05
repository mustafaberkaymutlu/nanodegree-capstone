package net.epictimes.reddit.data.model.subreddit_search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubredditSearchResponse {

    @SerializedName("subreddits")
    private List<SubredditSearchRaw> subredditsRaw;

    public List<SubredditSearchRaw> getSubredditsRaw() {
        return subredditsRaw;
    }
}
