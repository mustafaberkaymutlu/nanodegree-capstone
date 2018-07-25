package net.epictimes.reddit.features.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

import dagger.android.AndroidInjection;

public class PostDetailActivity extends BaseActivity<PostDetailViewModel> {
    static final String KEY_POST_ID = "post_id";

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
    }

    private void updateView(@Nullable PostDetailViewEntity postDetailViewEntity) {
        // TODO implement
    }
}
