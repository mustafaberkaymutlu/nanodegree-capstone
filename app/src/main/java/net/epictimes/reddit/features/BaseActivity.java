package net.epictimes.reddit.features;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.epictimes.reddit.features.alert.AlertViewEntity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    @Inject
    protected VM viewModel;

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

    protected void showAlert(@NonNull AlertViewEntity alertViewEntity) {
        Toast.makeText(this, alertViewEntity.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
