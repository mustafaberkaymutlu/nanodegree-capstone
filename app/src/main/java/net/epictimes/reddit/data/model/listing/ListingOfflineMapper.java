package net.epictimes.reddit.data.model.listing;

import net.epictimes.reddit.data.model.post.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class ListingOfflineMapper implements Function<Listing, List<ListingOffline>> {

    @Inject
    public ListingOfflineMapper() {
    }

    @Override
    public List<ListingOffline> apply(Listing listing) {
        final ArrayList<ListingOffline> listingOfflines = new ArrayList<>();

        for (Post post : listing.getChildren()) {
            final ListingOffline listingOffline = new ListingOffline(post.getId());
            listingOfflines.add(listingOffline);
        }

        return listingOfflines;
    }
}
