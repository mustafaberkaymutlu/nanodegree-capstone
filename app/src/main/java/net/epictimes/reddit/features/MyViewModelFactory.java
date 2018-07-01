package net.epictimes.reddit.features;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class MyViewModelFactory<VM extends BaseViewModel> implements ViewModelProvider.Factory {

    private final VM viewModel;

    MyViewModelFactory(VM viewModel) {
        this.viewModel = viewModel;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) viewModel;
    }
}
