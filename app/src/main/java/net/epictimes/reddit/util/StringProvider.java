package net.epictimes.reddit.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public final class StringProvider {

    @NonNull
    private Context context;

    public StringProvider(@NonNull Context context) {
        this.context = Preconditions.checkNotNull(context, "Context cannot be null. ");
    }

    public final String getString(@StringRes int redId) {
        return context.getString(redId);
    }

    public final String getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

}
