package net.epictimes.reddit.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import net.epictimes.reddit.BuildConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RemoteDataSourceModule {

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface AuthorizationApi {
    }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface AuthorizedApi {
    }

    @Singleton
    @Provides
    AuthorizationServices provideAuthorizationServices(@AuthorizationApi @NonNull Retrofit retrofit) {
        return retrofit.create(AuthorizationServices.class);
    }

    @Singleton
    @Provides
    Services provideAuthorizedServices(@AuthorizedApi @NonNull Retrofit retrofit) {
        return retrofit.create(Services.class);
    }

    @AuthorizationApi
    @Singleton
    @Provides
    Retrofit provideAuthorizationRetrofit(@AuthorizationApi @NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(AuthorizationServices.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @AuthorizedApi
    @Singleton
    @Provides
    Retrofit provideAuthorizedRetrofit(@AuthorizedApi @NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Services.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @AuthorizationApi
    @Singleton
    @Provides
    OkHttpClient provideAuthorizationOkHttpClient(@Nullable StethoInterceptor stethoInterceptor) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (stethoInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(stethoInterceptor);
        }

        return okHttpClientBuilder.build();
    }

    @AuthorizedApi
    @Singleton
    @Provides
    OkHttpClient provideAuthorizedOkHttpClient(@NonNull AuthInterceptor authInterceptor,
                                               @Nullable StethoInterceptor stethoInterceptor) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.addNetworkInterceptor(authInterceptor);

        if (stethoInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(stethoInterceptor);
        }

        return okHttpClientBuilder.build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Nullable
    @Provides
    StethoInterceptor provideStethoInterceptor() {
        if (BuildConfig.DEBUG) {
            return new StethoInterceptor();
        }

        return null;
    }

    @Singleton
    @Provides
    AuthInterceptor provideAuthInterceptor(){
        return new AuthInterceptor();
    }

}
