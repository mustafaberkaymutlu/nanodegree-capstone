package net.epictimes.reddit.data.model.subreddit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.annotation.Nonnull;

@Entity
public class Subreddit {

    @NonNull
    @Nonnull
    @PrimaryKey
    private String displayName;

    private String displayNamePrefixed;

    private int subscribers;

    private String description;

    private boolean userIsSubscriber;

    @Nullable
    private String headerImg;

    public Subreddit() {
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NonNull String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUserIsSubscriber() {
        return userIsSubscriber;
    }

    public void setUserIsSubscriber(boolean userIsSubscriber) {
        this.userIsSubscriber = userIsSubscriber;
    }

    @Nullable
    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(@Nullable String headerImg) {
        this.headerImg = headerImg;
    }

    private Subreddit(Builder builder) {
        displayName = builder.displayName;
        displayNamePrefixed = builder.displayNamePrefixed;
        subscribers = builder.subscribers;
        description = builder.description;
        userIsSubscriber = builder.userIsSubscriber;
        headerImg = builder.headerImg;
    }

    public static final class Builder {
        private String displayName;
        private String displayNamePrefixed;
        private int subscribers;
        private String description;
        private boolean userIsSubscriber;
        private String headerImg;

        public Builder() {
        }

        @Nonnull
        public Builder withDisplayName(@Nonnull String displayName) {
            this.displayName = displayName;
            return this;
        }

        @Nonnull
        public Builder withDisplayNamePrefixed(@Nonnull String displayNamePrefixed) {
            this.displayNamePrefixed = displayNamePrefixed;
            return this;
        }

        @Nonnull
        public Builder withSubscribers(int subscribers) {
            this.subscribers = subscribers;
            return this;
        }

        @Nonnull
        public Builder withDescription(@Nonnull String description) {
            this.description = description;
            return this;
        }

        @Nonnull
        public Builder withUserIsSubscriber(boolean userIsSubscriber) {
            this.userIsSubscriber = userIsSubscriber;
            return this;
        }

        @Nonnull
        public Builder withHeaderImg(@Nullable String headerImg) {
            this.headerImg = headerImg;
            return this;
        }

        @Nonnull
        public Subreddit build() {
            return new Subreddit(this);
        }
    }
}
