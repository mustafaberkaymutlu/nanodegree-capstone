package net.epictimes.reddit.data.model.post;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class PostMapper implements ObservableTransformer<PostRaw, Post> {

    @Inject
    public PostMapper() {
    }

    @Override
    public ObservableSource<Post> apply(Observable<PostRaw> upstream) {
        return upstream
                .doOnNext(this::validateFields)
                .map(postRaw -> new Post.Builder()
                        .withId(postRaw.getId())
                        .withDescription(postRaw.getDescription())
                        .withDisplayNamePrefixed(postRaw.getDisplayNamePrefixed())
                        .withHeaderImg(postRaw.getHeaderImg())
                        .withIconImg(postRaw.getIconImg())
                        .withBannerImg(postRaw.getBannerImg())
                        .withCreatedUtc(postRaw.getCreatedUtc())
                        .build());
    }

    private void validateFields(final PostRaw postRaw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(postRaw.getId())) {
            stringBuilder.append("id cannot be empty, ");
        }

        if (StringUtils.isBlank(postRaw.getDescription())) {
            stringBuilder.append("description cannot be empty, ");
        }

        if (StringUtils.isBlank(postRaw.getDisplayNamePrefixed())) {
            stringBuilder.append("displayNamePrefixed cannot be empty, ");
        }

        if (postRaw.getCreatedUtc() == null) {
            stringBuilder.append("createdUtc cannot be empty. ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
