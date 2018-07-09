package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.epictimes.reddit.data.model.login.AccessToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Nullable
    private AccessToken accessToken;

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder newRequestBuilder = chain
                .request()
                .newBuilder();

        if (accessToken != null) {
            newRequestBuilder.addHeader("Authorization", "Bearer " +
                    accessToken.getAccessToken());
        }

        return chain.proceed(newRequestBuilder.build());
    }

    public void setAccessToken(@Nullable AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
