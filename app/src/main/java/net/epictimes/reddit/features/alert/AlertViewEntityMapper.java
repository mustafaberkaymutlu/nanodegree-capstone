package net.epictimes.reddit.features.alert;

import android.support.annotation.NonNull;

import net.epictimes.reddit.R;
import net.epictimes.reddit.util.StringProvider;

public class AlertViewEntityMapper {

    @NonNull
    private StringProvider stringProvider;

    public AlertViewEntityMapper(@NonNull StringProvider stringProvider) {
        this.stringProvider = stringProvider;
    }

    public AlertViewEntity create(Throwable throwable) {
        final String message = getUserFriendlyMessage(throwable);

        return new AlertViewEntity(message);
    }

    private String getUserFriendlyMessage(Throwable throwable) {
        // TODO can be improved
        return stringProvider.getString(R.string.error_network);
    }
}
