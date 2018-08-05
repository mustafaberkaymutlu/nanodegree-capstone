package net.epictimes.reddit.data.model.subreddit_search;

import com.google.gson.annotations.SerializedName;

public class SubredditSearchRaw {

    @SerializedName("name")
    private String name;

    @SerializedName("subscriber_count")
    private Integer subscriberCount;

    @SerializedName("icon_img")
    private String iconUrl;

    public String getName() {
        return name;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
