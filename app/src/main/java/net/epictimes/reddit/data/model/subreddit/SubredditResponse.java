package net.epictimes.reddit.data.model.subreddit;

import com.google.gson.annotations.SerializedName;

public class SubredditResponse {

    @SerializedName("data")
    private SubredditRaw subredditRaw;

    public SubredditRaw getSubredditRaw() {
        return subredditRaw;
    }
}
