package net.epictimes.reddit.data.model.listing;

import android.support.annotation.NonNull;

import net.epictimes.reddit.data.model.child.ChildRaw;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.data.model.post.PostMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class ListingMapper implements ObservableTransformer<ListingRaw, Listing> {

    @NonNull
    private final PostMapper postMapper;

    @Inject
    ListingMapper(@NonNull PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public ObservableSource<Listing> apply(Observable<ListingRaw> upstream) {
        return upstream
                .doOnNext(this::validateFields)
                .flatMap(listingRaw ->
                        Observable
                                .fromIterable(listingRaw.getChildren())
                                .map(ChildRaw::getData)
                                .compose(postMapper)
                                .toList()
                                .toObservable()
                                .map(posts -> buildListing(listingRaw, posts)));
    }

    @NonNull
    private Listing buildListing(@NonNull ListingRaw listingRaw, @NonNull List<Post> posts) {
        return new Listing.Builder()
                .withBefore(listingRaw.getBefore())
                .withAfter(listingRaw.getAfter())
                .withChildren(posts)
                .build();
    }

    private void validateFields(final ListingRaw listingRaw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (listingRaw.getChildren() == null) {
            stringBuilder.append("children cannot be null, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
