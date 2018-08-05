package net.epictimes.reddit.features.login;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.epictimes.reddit.R;
import net.epictimes.reddit.data.remote.RemoteHelper;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.features.alert.AlertViewEntity;
import net.epictimes.reddit.features.feed.FeedActivity;

import java.util.UUID;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private String uuid;
    private CustomTabsServiceConnection customTabsServiceConnection;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            openLoginScreen();
            logLoginAction();
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        final String action = intent.getAction();
        final Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            final String state = data.getQueryParameter("state");
            final String code = data.getQueryParameter("code");

            if (uuid.equals(state)) {
                viewModel.onUserLoggedIn(code);
            } else {
                Toast.makeText(this, "Cannot login", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (customTabsServiceConnection != null) {
            unbindService(customTabsServiceConnection);
            customTabsServiceConnection = null;
        }
    }

    private void updateView(LoginViewEntity loginViewEntity) {
        if (loginViewEntity instanceof LoginViewEntity.Loading) {

        } else if (loginViewEntity instanceof LoginViewEntity.Error) {
            final LoginViewEntity.Error loginViewEntity1 = (LoginViewEntity.Error) loginViewEntity;
            final AlertViewEntity alertViewEntity = loginViewEntity1.getAlertViewEntity();
            showAlert(alertViewEntity);
        } else if (loginViewEntity instanceof LoginViewEntity.LoginComplete) {
            Toast.makeText(this, "We logged in", Toast.LENGTH_SHORT).show();
            startActivity(FeedActivity.newIntent(this));
            finish();
        }
    }

    private void openLoginScreen() {
        uuid = UUID.randomUUID().toString();
        final Uri uri = RemoteHelper.getAuthorizationUri(uuid);

        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                client.warmup(0L); // This prevents backgrounding after redirection

                final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                final CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(LoginActivity.this, uri);
            }
        };

        CustomTabsClient.bindCustomTabsService(this, "com.android.chrome", customTabsServiceConnection);
    }

    private void logLoginAction() {
        final Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

}
