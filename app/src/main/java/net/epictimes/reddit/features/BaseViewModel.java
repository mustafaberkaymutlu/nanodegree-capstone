package net.epictimes.reddit.features;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel implements LifecycleObserver {

    protected final CompositeDisposable lifecycleDisposable = new CompositeDisposable();

    protected abstract void onBind(CompositeDisposable lifecycleDisposable);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        onBind(lifecycleDisposable);
    }

    @Override
    protected void onCleared() {
        lifecycleDisposable.clear();
        super.onCleared();
    }

}
