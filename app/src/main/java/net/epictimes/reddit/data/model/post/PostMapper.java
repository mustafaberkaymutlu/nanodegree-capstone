package net.epictimes.reddit.data.model.post;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class PostMapper implements ObservableTransformer<PostRaw, Post> {

    @Inject
    PostMapper() {
    }

    @Override
    public ObservableSource<Post> apply(Observable<PostRaw> upstream) {
        return upstream
                .doOnNext(this::validateFields)
                .map(postRaw -> new Post.Builder()
                        .withId(postRaw.getId())
                        .withAuthor(postRaw.getAuthor())
                        .withTitle(postRaw.getTitle())
                        .withSelfText(postRaw.getSelfText())
                        .withSubredditNamePrefixed(postRaw.getSubredditNamePrefixed())
                        .withHeaderImg(postRaw.getHeaderImg())
                        .withThumbnail(postRaw.getThumbnail())
                        .withBannerImg(postRaw.getBannerImg())
                        .withCreatedUtc(postRaw.getCreatedUtc())
                        .build());
    }

    private void validateFields(final PostRaw postRaw) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(postRaw.getId())) {
            stringBuilder.append("id cannot be empty, ");
        }

        if (StringUtils.isBlank(postRaw.getAuthor())) {
            stringBuilder.append("author cannot be empty, ");
        }

        if (StringUtils.isBlank(postRaw.getTitle())) {
            stringBuilder.append("title cannot be empty, ");
        }

        if (StringUtils.isBlank(postRaw.getSubredditNamePrefixed())) {
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
