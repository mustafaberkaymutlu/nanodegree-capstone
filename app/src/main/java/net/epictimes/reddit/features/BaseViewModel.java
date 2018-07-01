package net.epictimes.reddit.features;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel implements LifecycleObserver {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected abstract void onBind(CompositeDisposable compositeDisposable);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        onBind(compositeDisposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

}
