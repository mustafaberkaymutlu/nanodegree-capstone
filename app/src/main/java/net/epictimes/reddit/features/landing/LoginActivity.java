package net.epictimes.reddit.features.landing;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void observeLiveData() {

    }
}
