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
    private String subredditNamePrefixed;

    @Nullable
    private String headerImg;

    @Nonnull
    private String thumbnail;

    @Nullable
    private String bannerImg;

    @Nonnull
    private Long createdUtc;

    public Post() {
    }

    private Post(Builder builder) {
        id = builder.id;
        author = builder.author;
        title = builder.title;
        selfText = builder.selfText;
        subredditNamePrefixed = builder.subredditNamePrefixed;
        headerImg = builder.headerImg;
        thumbnail = builder.thumbnail;
        bannerImg = builder.bannerImg;
        createdUtc = builder.createdUtc;
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

    @Nonnull
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nonnull String thumbnail) {
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

    public static final class Builder {
        private String id;
        private String author;
        private String title;
        private String selfText;
        private String subredditNamePrefixed;
        private String headerImg;
        private String thumbnail;
        private String bannerImg;
        private long createdUtc;

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
        public Builder withThumbnail(@Nonnull String thumbnail) {
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
        public Post build() {
            return new Post(this);
        }
    }
}
