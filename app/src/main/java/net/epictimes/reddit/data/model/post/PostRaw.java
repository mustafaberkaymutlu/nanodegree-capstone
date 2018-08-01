package net.epictimes.reddit.data.model.post;

import com.google.gson.annotations.SerializedName;

import net.epictimes.reddit.data.model.preview.PreviewRaw;

public class PostRaw {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("selftext")
    private String selfText;

    @SerializedName("subreddit")
    private String subreddit;

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

    @SerializedName("url")
    private String url;

    @SerializedName("domain")
    private String domain;

    @SerializedName("preview")
    private PreviewRaw previewRaw;

    @SerializedName("num_comments")
    private Integer commentCount;

    @SerializedName("ups")
    private Integer upVoteCount;

    @SerializedName("downs")
    private Integer downVoteCount;

    @SerializedName("likes")
    private Boolean likes;

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

    public String getSubreddit() {
        return subreddit;
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

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public PreviewRaw getPreviewRaw() {
        return previewRaw;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Integer getUpVoteCount() {
        return upVoteCount;
    }

    public Integer getDownVoteCount() {
        return downVoteCount;
    }

    public Boolean getLikes() {
        return likes;
    }
}
