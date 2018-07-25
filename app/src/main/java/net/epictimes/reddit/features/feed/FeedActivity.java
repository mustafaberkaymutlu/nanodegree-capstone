package net.epictimes.reddit.features.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.model.listing.Listing;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.detail.PostDetailActivity;
import net.epictimes.reddit.features.login.LoginActivity;
import net.epictimes.reddit.util.EndlessRecyclerViewScrollListener;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

public class FeedActivity extends BaseActivity<FeedViewModel> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FeedRecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    public static Intent newIntent(@Nonnull Context context) {
        Preconditions.checkNotNull(context);
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void observeLiveData() {
        viewModel.userNotLoggedInLiveData.observe(this, __ -> navigateToLogin());
        viewModel.viewEntityLiveData.observe(this, this::updateView);
        viewModel.navigateToPostDetail.observe(this, this::navigateToPostDetail);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            endlessRecyclerViewScrollListener.resetState();
            viewModel.onUserRefreshed();
        });
    }

    private void initRecyclerView() {
        adapter = new FeedRecyclerViewAdapter();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        final DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager,
                (page, totalItemsCount, view) -> viewModel.loadMore(adapter.getLastPostId()));
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);

        adapter.setPostClickListener(viewModel::onPostClicked);
    }

    private void updateView(@Nullable FeedViewEntity viewEntity) {
        if (viewEntity instanceof FeedViewEntity.Content) {
            final FeedViewEntity.Content feedViewEntity = (FeedViewEntity.Content) viewEntity;
            final Listing listing = feedViewEntity.getListing();

            if (feedViewEntity.isPaginated()) {
                adapter.addItems(listing.getChildren());
            } else {
                adapter.setItems(listing.getChildren());
            }
        } else if (viewEntity instanceof FeedViewEntity.Error) {
            final FeedViewEntity.Error feedViewEntity = (FeedViewEntity.Error) viewEntity;
            showAlert(feedViewEntity.getAlertViewEntity());
        } else if (viewEntity instanceof FeedViewEntity.Loading) {
            final FeedViewEntity.Loading feedViewEntity = (FeedViewEntity.Loading) viewEntity;
            swipeRefreshLayout.setRefreshing(feedViewEntity.isLoading());
        }
    }

    private void navigateToLogin() {
        final Intent intent = LoginActivity.newIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void navigateToPostDetail(@Nullable String postId) {
        if (postId == null) return;

        final Intent postDetailIntent = PostDetailActivity.newIntent(this, postId);
        startActivity(postDetailIntent);
    }
}
