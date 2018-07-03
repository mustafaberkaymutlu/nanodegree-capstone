package net.epictimes.reddit.features.feed;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;

public class FeedActivity extends BaseActivity<FeedViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void observeLiveData() {

    }
}
