package net.epictimes.reddit.features;

public class LoadingViewEntity {

    private final boolean isLoading;

    public LoadingViewEntity(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

}
