package net.epictimes.reddit.features;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    VM viewModel;

    protected abstract int getLayoutId();

    protected abstract void observeLiveData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initializeViewModel();
        observeLiveData();
    }

    private void initializeViewModel() {
        ViewModelProviders.of(this, new MyViewModelFactory<>(viewModel)).get(viewModel.getClass());
        getLifecycle().addObserver(viewModel);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
