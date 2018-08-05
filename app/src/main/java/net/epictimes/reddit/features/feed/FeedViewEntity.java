package net.epictimes.reddit.features.feed;

import net.epictimes.reddit.data.model.listing.Listing;

class FeedViewEntity {

    private final Listing listing;
    private final boolean paginated;

    FeedViewEntity(Listing listing, boolean paginated) {
        this.listing = listing;
        this.paginated = paginated;
    }

    public Listing getListing() {
        return listing;
    }

    public boolean isPaginated() {
        return paginated;
    }
}
