package net.epictimes.reddit.data.model.post;

import android.support.annotation.NonNull;
import android.util.Patterns;

import net.epictimes.reddit.data.model.media.Media;
import net.epictimes.reddit.data.model.media.MediaMapper;
import net.epictimes.reddit.data.model.preview.Preview;
import net.epictimes.reddit.data.model.preview.PreviewMapper;
import net.epictimes.reddit.data.model.vote.Vote;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class PostMapper implements ObservableTransformer<PostRaw, Post> {

    @NonNull
    private final PreviewMapper previewMapper;

    @NonNull
    private final MediaMapper mediaMapper;

    @Inject
    PostMapper(@NonNull PreviewMapper previewMapper, @NonNull MediaMapper mediaMapper) {
        this.previewMapper = previewMapper;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public ObservableSource<Post> apply(Observable<PostRaw> upstream) {
        return upstream
                .doOnNext(this::validate)
                .flatMap(postRaw -> {
                    final Observable<Post.Builder> builder = Observable.just(buildPost(postRaw));

                    final Observable<Post.Builder> withPreview;
                    if (postRaw.getPreviewRaw() == null) {
                        withPreview = builder;
                    } else {
                        withPreview = Observable
                                .just(postRaw.getPreviewRaw())
                                .compose(previewMapper)
                                .flatMap(preview ->
                                        builder.map(builder12 ->
                                                builder12.withPreviewImage(getImageIfExists(preview))));
                    }

                    final Observable<Post.Builder> withVideo;
                    if (postRaw.getMediaRaw() == null) {
                        withVideo = withPreview;
                    } else {
                        withVideo = Observable
                                .just(postRaw.getMediaRaw())
                                .compose(mediaMapper)
                                .flatMap(media ->
                                        withPreview.map(builder1 ->
                                                builder1.withVideoUrl(getVideoIfExists(media))));
                    }

                    return withVideo;
                })
                .map(Post.Builder::build);
    }

    @NonNull
    private Post.Builder buildPost(@NonNull PostRaw postRaw) {
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
//                .withPreviewImage(getImageIfExists(preview))
                .withCommentCount(postRaw.getCommentCount())
                .withUpVoteCount(postRaw.getUpVoteCount())
                .withDownVoteCount(postRaw.getDownVoteCount())
                .withLikes(mapToVote(postRaw.getLikes()))
                .withIsVideo(postRaw.isVideo());
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
            stringBuilder.append("created_utc cannot be empty, ");
        }

        if (postRaw.getDomain() == null) {
            stringBuilder.append("domain cannot be empty, ");
        }

        if (postRaw.getCommentCount() == null) {
            stringBuilder.append("num_comments cannot be empty, ");
        }

        if (postRaw.getUpVoteCount() == null) {
            stringBuilder.append("ups cannot be empty, ");
        }

        if (postRaw.getDownVoteCount() == null) {
            stringBuilder.append("downs cannot be empty. ");
        }

        if (postRaw.isVideo() == null) {
            stringBuilder.append("is_video cannot be empty. ");
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

    @Nullable
    private String getVideoIfExists(@Nullable Media media) {
        if (media == null) return null;
        if (media.getRedditVideo() == null) return null;
        return media.getRedditVideo().getHlsUrl();
    }

    private Vote mapToVote(@Nullable Boolean likes) {
        if (likes == null) return Vote.NONE;
        if (likes) return Vote.UP;
        return Vote.DOWN;
    }
}
