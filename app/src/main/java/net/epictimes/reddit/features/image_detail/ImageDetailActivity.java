package net.epictimes.reddit.features.image_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.chrisbanes.photoview.PhotoView;

import net.epictimes.reddit.R;
import net.epictimes.reddit.features.BaseActivity;
import net.epictimes.reddit.util.GlideApp;
import net.epictimes.reddit.util.Preconditions;

import javax.annotation.Nonnull;

public class ImageDetailActivity extends BaseActivity<ImageDetailViewModel> {
    static final String KEY_URL = "url";

    private PhotoView photoView;

    public static Intent newIntent(@Nonnull Context context, @Nonnull String url) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(url);
        final Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void observeLiveData() {
        viewModel.viewEntityLiveData.observe(this, this::updateView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoView = findViewById(R.id.photoView);
    }

    private void updateView(@Nullable ImageDetailViewEntity viewEntity) {
        if (viewEntity != null) {
            updateContent(viewEntity);
        }
    }

    private void updateContent(@NonNull ImageDetailViewEntity content) {
        GlideApp
                .with(this)
                .load(content.getImageUrl())
                .into(photoView);
    }
}
