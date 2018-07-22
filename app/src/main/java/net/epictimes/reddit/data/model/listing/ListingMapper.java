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
        final Observable<ListingRaw> validatedStream = upstream
                .doOnNext(this::validateFields);

        final Observable<List<Post>> mappedChildrenStream = validatedStream
                .flatMap(listingRaw -> Observable.fromIterable(listingRaw.getChildren()))
                .map(ChildRaw::getData)
                .compose(postMapper)
                .toList()
                .toObservable();

        return Observable.zip(validatedStream, mappedChildrenStream, (t1, t2) ->
                new Listing.Builder()
                        .withBefore(t1.getBefore())
                        .withAfter(t1.getAfter())
                        .withChildren(t2)
                        .build());
    }

    private void validateFields(final ListingRaw listingRaw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (CollectionUtils.isEmpty(listingRaw.getChildren())) {
            stringBuilder.append("children cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
