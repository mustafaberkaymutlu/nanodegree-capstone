package net.epictimes.reddit.data.model.subreddit;

import com.google.gson.annotations.SerializedName;

public class SubredditRaw {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("display_name_prefixed")
    private String displayNamePrefixed;

    @SerializedName("subscribers")
    private Integer subscribers;

    @SerializedName("description")
    private String description;

    @SerializedName("user_is_subscriber")
    private Boolean userIsSubscriber;

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getUserIsSubscriber() {
        return userIsSubscriber;
    }
}
