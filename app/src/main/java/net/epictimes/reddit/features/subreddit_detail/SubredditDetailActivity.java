package net.epictimes.reddit.features.subreddit_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.subreddit.Subreddit;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

public class SubredditDetailActivity extends BaseActivity<SubredditDetailViewModel> {
    static final String KEY_SUBREDDIT_NAME = "subreddit_name";

    private ImageView imageViewIcon;
    private TextView textViewSubredditName;
    private TextView textViewSubscriberCount;
    private TextView textViewDescription;
    private Button buttonSubscribe;

    public static Intent newIntent(@Nonnull Context context, @Nonnull String subredditName) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(subredditName);
        final Intent intent = new Intent(context, SubredditDetailActivity.class);
        intent.putExtra(KEY_SUBREDDIT_NAME, subredditName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subreddit_detail;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
        viewModel.alertViewEntitySingleLiveEvent.observe(this, this::showAlert);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        imageViewIcon = findViewById(R.id.imageViewIcon);
        textViewSubredditName = findViewById(R.id.textViewSubredditName);
        textViewSubscriberCount = findViewById(R.id.textViewSubscriberCount);
        textViewDescription = findViewById(R.id.textViewDescription);
        buttonSubscribe = findViewById(R.id.buttonSubscribe);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void updateView(@Nullable SubredditDetailViewEntity viewEntity) {
        if (viewEntity == null) return;

        final Subreddit subreddit = viewEntity.getSubreddit();

        textViewSubredditName.setText(subreddit.getDisplayNamePrefixed());
        final String subscriberCount = getResources().getQuantityString(R.plurals.subscriber_count,
                subreddit.getSubscribers(), subreddit.getSubscribers());
        textViewSubscriberCount.setText(subscriberCount);
        textViewDescription.setText(subreddit.getDescription());
        buttonSubscribe.setText(subreddit.isUserIsSubscriber() ? R.string.unsubscribe : R.string.subscribe);
    }
}
