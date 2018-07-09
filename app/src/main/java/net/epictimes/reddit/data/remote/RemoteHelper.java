package net.epictimes.reddit.data.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

public class RemoteHelper {

    private RemoteHelper() {
    }

    public static Uri getAuthorizationUri(@NonNull String state) {
        return Uri.parse(AuthorizationServices.AUTHORIZATION_URL)
                .buildUpon()
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("duration", "permanent")
                .appendQueryParameter("client_id", AuthorizationServices.CLIENT_ID)
                .appendQueryParameter("redirect_uri", AuthorizationServices.REDIRECT_URI)
                .appendQueryParameter("scope", AuthorizationServices.SCOPES)
                .appendQueryParameter("state", state)
                .build();
    }
}
