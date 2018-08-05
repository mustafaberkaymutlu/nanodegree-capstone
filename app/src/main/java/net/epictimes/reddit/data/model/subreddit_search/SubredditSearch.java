package net.epictimes.reddit.data.model.subreddit_search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.annotation.Nonnull;

public class SubredditSearch {

    @NonNull
    private String name;

    private int subscriberCount;

    @Nullable
    private String iconUrl;

    private SubredditSearch(Builder builder) {
        name = builder.name;
        subscriberCount = builder.subscriberCount;
        iconUrl = builder.iconUrl;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    @Nullable
    public String getIconUrl() {
        return iconUrl;
    }

    public static final class Builder {
        private String name;
        private int subscriberCount;
        private String iconUrl;

        public Builder() {
        }

        @Nonnull
        public Builder withName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public Builder withSubscriberCount(int subscriberCount) {
            this.subscriberCount = subscriberCount;
            return this;
        }

        @Nonnull
        public Builder withIconUrl(@Nullable String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        @Nonnull
        public SubredditSearch build() {
            return new SubredditSearch(this);
        }
    }
}
