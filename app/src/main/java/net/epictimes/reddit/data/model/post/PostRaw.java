package net.epictimes.reddit.data.model.post;

import com.google.gson.annotations.SerializedName;

public class PostRaw {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("selftext")
    private String selfText;

    @SerializedName("subreddit_name_prefixed")
    private String subredditNamePrefixed;

    @SerializedName("header_img")
    private String headerImg;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("banner_img")
    private String bannerImg;

    @SerializedName("created_utc")
    private Long createdUtc;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSelfText() {
        return selfText;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }
}
