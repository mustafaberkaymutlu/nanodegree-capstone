package net.epictimes.reddit.data.model.post;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Post {

    @Nonnull
    private String id;

    @Nonnull
    private String description;

    @Nonnull
    private String displayNamePrefixed;

    @Nullable
    private String headerImg;

    @Nullable
    private String iconImg;

    @Nullable
    private String bannerImg;

    @Nonnull
    private Long createdUtc;

    private Post(Builder builder) {
        id = builder.id;
        description = builder.description;
        displayNamePrefixed = builder.displayNamePrefixed;
        headerImg = builder.headerImg;
        iconImg = builder.iconImg;
        bannerImg = builder.bannerImg;
        createdUtc = builder.createdUtc;
    }


    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nonnull
    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    @Nullable
    public String getHeaderImg() {
        return headerImg;
    }

    @Nullable
    public String getIconImg() {
        return iconImg;
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
        private String description;
        private String displayNamePrefixed;
        private String headerImg;
        private String iconImg;
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
        public Builder withDescription(@Nonnull String description) {
            this.description = description;
            return this;
        }

        @Nonnull
        public Builder withDisplayNamePrefixed(@Nonnull String displayNamePrefixed) {
            this.displayNamePrefixed = displayNamePrefixed;
            return this;
        }

        @Nonnull
        public Builder withHeaderImg(@Nullable String headerImg) {
            this.headerImg = headerImg;
            return this;
        }

        @Nonnull
        public Builder withIconImg(@Nullable String iconImg) {
            this.iconImg = iconImg;
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
