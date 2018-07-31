package net.epictimes.reddit.features.image_detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import net.epictimes.reddit.features.BaseViewModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ImageDetailViewModel extends BaseViewModel {

    @Nonnull
    final MutableLiveData<ImageDetailViewEntity> viewEntityLiveData = new MutableLiveData<>();

    @NonNull
    private final String url;

    @Inject
    public ImageDetailViewModel(@Url @NonNull String url) {
        this.url = url;
    }

    @Override
    protected void onBind(CompositeDisposable lifecycleDisposable) {
        viewEntityLiveData.postValue(new ImageDetailViewEntity(url));
    }

}
