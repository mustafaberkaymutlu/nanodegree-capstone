package net.epictimes.reddit.features.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.login.LoginActivity;

public class FeedActivity extends BaseActivity<FeedViewModel> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FeedRecyclerViewAdapter adapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void observeLiveData() {
        viewModel.userNotLoggedInLiveData.observe(this, aVoid -> navigateToLogin());
        viewModel.viewEntityLiveData.observe(this, this::updateView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.onUserRefreshed());
    }

    private void initRecyclerView() {
        adapter = new FeedRecyclerViewAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void updateView(FeedViewEntity viewEntity) {
        if (viewEntity instanceof FeedViewEntity.Content) {
            final FeedViewEntity.Content feedViewEntity = (FeedViewEntity.Content) viewEntity;
            adapter.setItems(feedViewEntity.getListing().getChildren());
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
}
