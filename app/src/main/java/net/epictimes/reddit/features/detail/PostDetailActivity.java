package net.epictimes.reddit.features.detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.post.Post;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.Preconditions;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

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
    private Button buttonUrl;

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

        imageViewIcon = findViewById(R.id.imageViewIcon);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewSubredditName = findViewById(R.id.textViewSubredditName);
        textViewPostTitle = findViewById(R.id.textViewPostTitle);
        textViewPostSelfText = findViewById(R.id.textViewPostSelfText);
        textViewTimeAgo = findViewById(R.id.textViewTimeAgo);
        imageViewPostImage = findViewById(R.id.imageViewPostImage);
        buttonUrl = findViewById(R.id.buttonUrl);
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
    }

    private void updateView(@Nullable PostDetailViewEntity viewEntity) {
        if (viewEntity instanceof PostDetailViewEntity.Content) {
            final PostDetailViewEntity.Content content = (PostDetailViewEntity.Content) viewEntity;
            updateContent(content);
        } else if (viewEntity instanceof PostDetailViewEntity.Error) {
            final PostDetailViewEntity.Error feedViewEntity = (PostDetailViewEntity.Error) viewEntity;
            showAlert(feedViewEntity.getAlertViewEntity());
        }
    }

    private void updateContent(PostDetailViewEntity.Content content) {
        final Post post = content.getPost();
        final String prefixedAuthorName = textViewUserName.getContext().getString(R.string.prefixed_author_name,
                post.getAuthor());
        textViewUserName.setText(prefixedAuthorName);
        textViewSubredditName.setText(post.getSubredditNamePrefixed());
        textViewPostTitle.setText(Html.fromHtml(post.getTitle()));
        textViewPostSelfText.setText(post.getSelfText());
        textViewTimeAgo.setText(TimeAgo.using(post.getCreatedUtc() * 1000));

        final boolean isDomainNotSelf = !post.getDomain().equals("self." + post.getSubreddit());
        if (isDomainNotSelf) {
            buttonUrl.setVisibility(View.VISIBLE);
            buttonUrl.setText(getString(R.string.go_to_link, post.getDomain()));
            buttonUrl.setOnClickListener(v -> openUrl(post.getUrl()));
        }

        GlideApp
                .with(this)
                .load(post.getThumbnail())
                .into(imageViewIcon);

        if (StringUtils.isBlank(post.getPreviewImage())) {
            imageViewPostImage.setVisibility(View.GONE);
        } else {
            imageViewPostImage.setVisibility(View.VISIBLE);
            GlideApp
                    .with(this)
                    .load(post.getPreviewImage())
                    .into(imageViewPostImage);
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
}
