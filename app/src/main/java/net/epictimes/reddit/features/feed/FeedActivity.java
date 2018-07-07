package net.epictimes.reddit.features.feed;

import android.content.Context;
import android.content.Intent;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.login.LoginActivity;

public class FeedActivity extends BaseActivity<FeedViewModel> {

    public static Intent newIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
    }

    private void updateView(FeedViewEntity viewEntity) {
        if (viewEntity instanceof FeedViewEntity.UserNotLoggedIn) {
            navigateToLogin();
        } else if (viewEntity instanceof FeedViewEntity.Error) {
            final FeedViewEntity.Error feedViewEntity = (FeedViewEntity.Error) viewEntity;
            showAlert(feedViewEntity.getAlertViewEntity());
        }
    }

    private void navigateToLogin() {
        final Intent intent = LoginActivity.newIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
