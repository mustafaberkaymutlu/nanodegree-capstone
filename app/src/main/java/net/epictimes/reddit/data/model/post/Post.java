package net.epictimes.reddit.data.model.post;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Entity
public class Post {

    @NonNull
    @Nonnull
    @PrimaryKey
    private String id;

    @Nonnull
    private String author;

    @Nonnull
    private String title;

    @Nullable
    private String selfText;

    @Nonnull
    private String subreddit;

    @Nonnull
    private String subredditNamePrefixed;

    @Nullable
    private String headerImg;

    @Nullable
    private String thumbnail;

    @Nullable
    private String bannerImg;

    @Nonnull
    private Long createdUtc;

    @Nullable
    private String url;

    @NonNull
    private String domain;

    @Nullable
    private String previewImage;

    private int commentCount;

    private int upVoteCount;

    public Post() {
    }

    private Post(Builder builder) {
        id = builder.id;
        author = builder.author;
        title = builder.title;
        selfText = builder.selfText;
        subreddit = builder.subreddit;
        subredditNamePrefixed = builder.subredditNamePrefixed;
        headerImg = builder.headerImg;
        thumbnail = builder.thumbnail;
        bannerImg = builder.bannerImg;
        createdUtc = builder.createdUtc;
        url = builder.url;
        domain = builder.domain;
        previewImage = builder.previewImage;
        commentCount = builder.commentCount;
        upVoteCount = builder.upVoteCount;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nonnull String author) {
        this.author = author;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    @Nullable
    public String getSelfText() {
        return selfText;
    }

    public void setSelfText(@Nullable String selfText) {
        this.selfText = selfText;
    }

    @Nonnull
    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(@Nonnull String subreddit) {
        this.subreddit = subreddit;
    }

    @Nonnull
    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public void setSubredditNamePrefixed(@Nonnull String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    @Nullable
    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(@Nullable String headerImg) {
        this.headerImg = headerImg;
    }

    @Nullable
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Nullable
    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(@Nullable String bannerImg) {
        this.bannerImg = bannerImg;
    }

    @Nonnull
    public Long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(@Nonnull Long createdUtc) {
        this.createdUtc = createdUtc;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @NonNull
    public String getDomain() {
        return domain;
    }

    public void setDomain(@NonNull String domain) {
        this.domain = domain;
    }

    @Nullable
    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(@Nullable String previewImage) {
        this.previewImage = previewImage;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(int upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public static final class Builder {
        private String id;
        private String author;
        private String title;
        private String selfText;
        private String subreddit;
        private String subredditNamePrefixed;
        private String headerImg;
        private String thumbnail;
        private String bannerImg;
        private long createdUtc;
        private String url;
        private String domain;
        private String previewImage;
        private int commentCount;
        private int upVoteCount;

        public Builder() {
        }

        @Nonnull
        public Builder withId(@Nonnull String id) {
            this.id = id;
            return this;
        }

        @Nonnull
        public Builder withAuthor(@Nonnull String author) {
            this.author = author;
            return this;
        }

        @Nonnull
        public Builder withTitle(@Nonnull String title) {
            this.title = title;
            return this;
        }

        @Nonnull
        public Builder withSelfText(@Nonnull String selfText) {
            this.selfText = selfText;
            return this;
        }

        @Nonnull
        public Builder withSubreddit(@Nonnull String subreddit) {
            this.subreddit = subreddit;
            return this;
        }

        @Nonnull
        public Builder withSubredditNamePrefixed(@Nonnull String subredditNamePrefixed) {
            this.subredditNamePrefixed = subredditNamePrefixed;
            return this;
        }

        @Nonnull
        public Builder withHeaderImg(@Nullable String headerImg) {
            this.headerImg = headerImg;
            return this;
        }

        @Nonnull
        public Builder withThumbnail(@Nullable String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        @Nonnull
        public Builder withBannerImg(@Nullable String bannerImg) {
            this.bannerImg = bannerImg;
            return this;
        }

        @Nonnull
        public Builder withCreatedUtc(Long createdUtc) {
            this.createdUtc = createdUtc;
            return this;
        }

        @Nonnull
        public Builder withUrl(@Nullable String url) {
            this.url = url;
            return this;
        }

        @Nonnull
        public Builder withDomain(@NonNull String domain) {
            this.domain = domain;
            return this;
        }

        @Nonnull
        public Builder withPreviewImage(@Nullable String previewImage) {
            this.previewImage = previewImage;
            return this;
        }

        @Nonnull
        public Builder withCommentCount(int commentCount) {
            this.commentCount = commentCount;
            return this;
        }

        @Nonnull
        public Builder withUpVoteCount(int upVoteCount) {
            this.upVoteCount = upVoteCount;
            return this;
        }

        @Nonnull
        public Post build() {
            return new Post(this);
        }
    }
}
