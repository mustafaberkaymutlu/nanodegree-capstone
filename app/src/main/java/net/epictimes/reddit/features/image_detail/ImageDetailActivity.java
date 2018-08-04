package net.epictimes.reddit.features.image_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.chrisbanes.photoview.PhotoView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

import dagger.android.AndroidInjection;

public class ImageDetailActivity extends AppCompatActivity {
    private static final String KEY_URL = "url";

    public static Intent newIntent(@Nonnull Context context, @Nonnull String url) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(url);
        final Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final PhotoView photoView = findViewById(R.id.photoView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        final Bundle extras = Preconditions.checkNotNull(getIntent().getExtras());
        final String imageUrl = extras.getString(ImageDetailActivity.KEY_URL);

        GlideApp
                .with(this)
                .load(imageUrl)
                .into(photoView);
    }
}
