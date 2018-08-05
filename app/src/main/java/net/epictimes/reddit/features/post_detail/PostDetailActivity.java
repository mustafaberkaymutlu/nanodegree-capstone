package net.epictimes.reddit.features.post_detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.data.model.vote.Vote;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.image_detail.ImageDetailActivity;
import net.epictimes.reddit.features.video_detail.VideoDetailActivity;
import net.epictimes.reddit.util.AnalyticsConstants;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.Preconditions;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class PostDetailActivity extends BaseActivity<PostDetailViewModel> {
    static final String KEY_POST_ID = "post_id";

    private ImageView imageViewIcon;
    private TextView textViewUserName;
    private TextView textViewSubredditName;
    private TextView textViewPostTitle;
    private TextView textViewPostSelfText;
    private TextView textViewTimeAgo;
    private ImageView imageViewPostImage;
    private ImageView imageViewPlay;
    private Button buttonUrl;
    private TextView textViewUpVoteCount;
    private ImageButton imageButtonUpVote;
    private ImageButton imageButtonDownVote;
    private TextView textViewCommentCount;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private CustomTabsServiceConnection customTabsServiceConnection;

    public static Intent newIntent(@Nonnull Context context, @Nonnull String postId) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(postId);
        final Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(KEY_POST_ID, postId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        imageViewIcon = findViewById(R.id.imageViewIcon);
        textViewUserName = findViewById(R.id.textViewSubscriberCount);
        textViewSubredditName = findViewById(R.id.textViewSubredditName);
        textViewPostTitle = findViewById(R.id.textViewPostTitle);
        textViewPostSelfText = findViewById(R.id.textViewDescription);
        textViewTimeAgo = findViewById(R.id.textViewTimeAgo);
        imageViewPostImage = findViewById(R.id.imageViewPostImage);
        imageViewPlay = findViewById(R.id.imageViewPlay);
        buttonUrl = findViewById(R.id.buttonUrl);
        textViewUpVoteCount = findViewById(R.id.textViewUpVoteCount);
        imageButtonUpVote = findViewById(R.id.imageButtonUpVote);
        imageButtonDownVote = findViewById(R.id.imageButtonDownVote);
        textViewCommentCount = findViewById(R.id.textViewCommentCount);
        final Button buttonShare = findViewById(R.id.buttonShare);

        imageViewPostImage.setOnClickListener(v -> viewModel.onImageClicked());

        buttonShare.setOnClickListener(v -> {
            final PostDetailViewEntity viewEntity = viewModel.viewEntityLiveData.getValue();

            if (viewEntity != null) {
                share(viewEntity.getPost());
            }
        });

        imageButtonUpVote.setOnClickListener(v -> viewModel.onUpVoteClicked());
        imageButtonDownVote.setOnClickListener(v -> viewModel.onDownVoteClicked());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (customTabsServiceConnection != null) {
            unbindService(customTabsServiceConnection);
            customTabsServiceConnection = null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
        viewModel.alertViewEntitySingleLiveEvent.observe(this, this::showAlert);
        viewModel.navigateToImageDetailEvent.observe(this, this::navigateToImageDetail);
        viewModel.navigateToVideoDetailEvent.observe(this, this::navigateToVideoDetail);
        viewModel.voteEvent.observe(this, this::displayVoteMessage);
    }

    private void updateView(@Nullable PostDetailViewEntity viewEntity) {
        if (viewEntity == null) return;

        final Post post = viewEntity.getPost();
        final String prefixedAuthorName = textViewUserName.getContext().getString(R.string.prefixed_author_name,
                post.getAuthor());
        textViewUserName.setText(prefixedAuthorName);
        textViewSubredditName.setText(post.getSubredditNamePrefixed());
        textViewPostTitle.setText(Html.fromHtml(post.getTitle()));
        textViewPostSelfText.setText(post.getSelfText());
        textViewTimeAgo.setText(TimeAgo.using(post.getCreatedUtc() * 1000));
        textViewCommentCount.setText(String.valueOf(post.getCommentCount()));
        textViewUpVoteCount.setText(String.valueOf(post.getUpVoteCount()));

        updateVoteButtons(post.getVote());

        final boolean isDomainNotSelf = !post.getDomain().equals("self." + post.getSubreddit());
        if (isDomainNotSelf) {
            buttonUrl.setVisibility(View.VISIBLE);
            buttonUrl.setText(getString(R.string.go_to_link, post.getDomain()));
            buttonUrl.setOnClickListener(v -> openUrl(post.getUrl()));
        }

        if (post.getThumbnail() != null) {
            GlideApp
                    .with(this)
                    .load(post.getThumbnail())
                    .into(imageViewIcon);
        }

        if (StringUtils.isBlank(post.getPreviewImage())) {
            imageViewPostImage.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
        } else {
            imageViewPostImage.setVisibility(View.VISIBLE);
            GlideApp
                    .with(this)
                    .load(post.getPreviewImage())
                    .into(imageViewPostImage);

            if (post.isVideo()) {
                imageViewPlay.setVisibility(View.VISIBLE);
            } else {
                imageViewPlay.setVisibility(View.GONE);
            }
        }

        logPostView(post);
    }

    private void updateVoteButtons(@NonNull Vote vote) {
        switch (vote) {
            case UP:
                imageButtonUpVote.setImageResource(R.drawable.ic_keyboard_arrow_up_green_24dp);
                imageButtonDownVote.setImageResource(R.drawable.ic_keyboard_arrow_down_gray_24dp);
                break;
            case DOWN:
                imageButtonUpVote.setImageResource(R.drawable.ic_keyboard_arrow_up_gray_24dp);
                imageButtonDownVote.setImageResource(R.drawable.ic_keyboard_arrow_down_red_24dp);
                break;
            case NONE:
                imageButtonUpVote.setImageResource(R.drawable.ic_keyboard_arrow_up_gray_24dp);
                imageButtonDownVote.setImageResource(R.drawable.ic_keyboard_arrow_down_gray_24dp);
                break;
        }
    }

    private void openUrl(String url) {
        final Uri uri = Uri.parse(url);

        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                new CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(PostDetailActivity.this, uri);
            }
        };

        CustomTabsClient.bindCustomTabsService(this, "com.android.chrome", customTabsServiceConnection);
    }

    private void navigateToImageDetail(@Nullable String url) {
        if (url == null) return;

        final Intent imageDetailIntent = ImageDetailActivity.newIntent(this, url);
        startActivity(imageDetailIntent);
    }

    private void navigateToVideoDetail(@Nullable String videoUrl) {
        if (videoUrl == null) return;

        final Intent videoDetailIntent = VideoDetailActivity.newIntent(this, videoUrl);
        startActivity(videoDetailIntent);
    }

    private void share(@NonNull Post post) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, post.getUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_chooser_title)));

        logShare(post);
    }

    private void displayVoteMessage(Vote vote) {
        switch (vote) {
            case UP:
                Toast.makeText(this, R.string.vote_up, Toast.LENGTH_SHORT).show();
                break;
            case DOWN:
                Toast.makeText(this, R.string.vote_down, Toast.LENGTH_SHORT).show();
                break;
            case NONE:
                Toast.makeText(this, R.string.vote_removed, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void logPostView(@NonNull Post post) {
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, post.getId());
        bundle.putString(AnalyticsConstants.POST_TITLE, post.getTitle());
        bundle.putString(AnalyticsConstants.POST_AUTHOR, post.getAuthor());
        bundle.putString(AnalyticsConstants.SUBREDDIT_NAME, post.getSubreddit());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    private void logShare(@NonNull Post post) {
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, post.getId());
        bundle.putString(AnalyticsConstants.POST_TITLE, post.getTitle());
        bundle.putString(AnalyticsConstants.POST_AUTHOR, post.getAuthor());
        bundle.putString(AnalyticsConstants.SUBREDDIT_NAME, post.getSubreddit());
        bundle.putString(AnalyticsConstants.POST_SHARE_TEXT, post.getUrl());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
    }
}
