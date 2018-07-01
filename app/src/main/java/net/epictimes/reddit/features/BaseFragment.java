package net.epictimes.reddit.features;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    @Inject
    VM viewModel;

    protected abstract int getLayoutId();

    protected abstract void observeLiveData();

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = getLayoutInflater().inflate(getLayoutId(), container, false);
        initializeViewModel();
        observeLiveData();
        return view;
    }

    private void initializeViewModel() {
        ViewModelProviders.of(this, new MyViewModelFactory<>(viewModel)).get(viewModel.getClass());
        getLifecycle().addObserver(viewModel);
    }

}
