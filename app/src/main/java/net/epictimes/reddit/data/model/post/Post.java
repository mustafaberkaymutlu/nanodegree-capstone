package net.epictimes.reddit.data.model.post;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Post {

    @Nonnull
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

    @Nonnull
    public String getAuthor() {
        return author;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getSelfText() {
        return selfText;
    }

    @Nonnull
    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    @Nullable
    public String getHeaderImg() {
        return headerImg;
    }

    @Nonnull
    public String getThumbnail() {
        return thumbnail;
    }

    @Nullable
    public String getBannerImg() {
        return bannerImg;
    }

    @Nonnull
    public Long getCreatedUtc() {
        return createdUtc;
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
