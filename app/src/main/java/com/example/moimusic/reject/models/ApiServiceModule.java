package com.example.moimusic.reject.models;

import android.app.Application;

import com.example.moimusic.mvp.model.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


/**
 * Created by GuDong on 2015/6/10.
 */
@Module
public class ApiServiceModule {
    private static final String ENDPOINT="https://api.bmob.cn/";

    final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Application application, OkHttpClient okHttpClient) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(okHttpClient))
                .setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(RestAdapter restAdapter) {
        return restAdapter.create(ApiService.class);
    }
}
