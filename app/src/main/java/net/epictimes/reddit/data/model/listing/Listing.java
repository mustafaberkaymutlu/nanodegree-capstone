package net.epictimes.reddit.data.model.listing;

import net.epictimes.reddit.data.model.post.Post;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Listing {

    @Nullable
    private String before;

    @Nullable
    private String after;

    @Nonnull
    private List<Post> children;

    private Listing(Builder builder) {
        before = builder.before;
        after = builder.after;
        children = builder.children;
    }

    @Nullable
    public String getBefore() {
        return before;
    }

    @Nullable
    public String getAfter() {
        return after;
    }

    @Nonnull
    public List<Post> getChildren() {
        return children;
    }

    public static final class Builder {
        private String before;
        private String after;
        private List<Post> children;

        public Builder() {
        }

        @Nonnull
        public Builder withBefore(@Nullable String before) {
            this.before = before;
            return this;
        }

        @Nonnull
        public Builder withAfter(@Nonnull String after) {
            this.after = after;
            return this;
        }

        @Nonnull
        public Builder withChildren(@Nonnull List<Post> children) {
            this.children = children;
            return this;
        }

        @Nonnull
        public Listing build() {
            return new Listing(this);
        }
    }
}
