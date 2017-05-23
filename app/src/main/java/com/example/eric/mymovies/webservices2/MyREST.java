package com.example.eric.mymovies.webservices2;

import com.example.eric.mymovies.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This was traditionally used in a non-dagger approach
 */
@Deprecated
public class MyREST {
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel
            (HttpLoggingInterceptor
                    .Level.BODY);
    static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(getOkHttpClient())
            .build();

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }
}
