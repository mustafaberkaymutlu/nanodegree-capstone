package net.epictimes.reddit.data.model.post;

import android.support.annotation.NonNull;
import android.util.Patterns;

import net.epictimes.reddit.data.model.preview.Preview;
import net.epictimes.reddit.data.model.preview.PreviewMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class PostMapper implements ObservableTransformer<PostRaw, Post> {

    private final PreviewMapper previewMapper;

    @Inject
    PostMapper(PreviewMapper previewMapper) {
        this.previewMapper = previewMapper;
    }

    @Override
    public ObservableSource<Post> apply(Observable<PostRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .flatMap(postRaw -> {
                    if (postRaw.getPreviewRaw() == null) {
                        return Observable
                                .just(buildPost(postRaw, null));
                    } else {
                        return Observable
                                .just(postRaw.getPreviewRaw())
                                .compose(previewMapper)
                                .map(preview -> buildPost(postRaw, preview));
                    }
                });
    }

    @NonNull
    private Post buildPost(@NonNull PostRaw postRaw, @Nullable Preview preview) {
        final String thumbnail = isValidUrl(postRaw.getThumbnail())
                ? postRaw.getThumbnail()
                : null;

        return new Post.Builder()
                .withId(postRaw.getId())
                .withAuthor(postRaw.getAuthor())
                .withTitle(postRaw.getTitle())
                .withSelfText(postRaw.getSelfText())
                .withSubreddit(postRaw.getSubreddit())
                .withSubredditNamePrefixed(postRaw.getSubredditNamePrefixed())
                .withHeaderImg(postRaw.getHeaderImg())
                .withThumbnail(thumbnail)
                .withBannerImg(postRaw.getBannerImg())
                .withCreatedUtc(postRaw.getCreatedUtc())
                .withUrl(postRaw.getUrl())
                .withDomain(postRaw.getDomain())
                .withPreviewImage(getImageIfExists(preview))
                .withCommentCount(postRaw.getCommentCount())
                .build();
    }

    private boolean isValidUrl(@NonNull String url) {
        return StringUtils.isNotBlank(url) && Patterns.WEB_URL.matcher(url).matches();
    }

    private void validate(final PostRaw postRaw) {
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
            stringBuilder.append("subreddit_name_prefixed cannot be empty, ");
        }

        if (postRaw.getCreatedUtc() == null) {
            stringBuilder.append("created_utc cannot be empty. ");
        }

        if (postRaw.getDomain() == null) {
            stringBuilder.append("domain cannot be empty. ");
        }

        if (postRaw.getCommentCount() == null) {
            stringBuilder.append("num_comments cannot be empty. ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }

    @Nullable
    private String getImageIfExists(@Nullable Preview preview) {
        if (preview == null) return null;
        if (CollectionUtils.isEmpty(preview.getImages())) return null;
        return preview.getImages().get(0).getSource().getUrl();
    }
}
