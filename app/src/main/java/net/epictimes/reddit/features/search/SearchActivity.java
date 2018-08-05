package net.epictimes.reddit.features.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.subreddit_detail.SubredditDetailActivity;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<SearchViewModel> {

    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter adapter;

    @SearchQuery
    @Inject
    String searchQuery;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
        viewModel.alertViewEntitySingleLiveEvent.observe(this, this::showAlert);
        viewModel.navigateToSubredditDetailEvent.observe(this, this::navigateToSubredditDetail);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();

        toolbar.setTitle(searchQuery);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initRecyclerView() {
        adapter = new SearchRecyclerViewAdapter();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        final DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setSubredditClickListener(viewModel::onSubredditClicked);
    }

    private void updateView(@Nullable SearchViewEntity searchViewEntity) {
        if (searchViewEntity == null) return;

        adapter.setItems(searchViewEntity.getResults());
    }

    private void navigateToSubredditDetail(@Nullable String subredditName) {
        if (subredditName == null) return;

        final Intent subredditDetailIntent = SubredditDetailActivity.newIntent(this, subredditName);
        startActivity(subredditDetailIntent);
    }
}
