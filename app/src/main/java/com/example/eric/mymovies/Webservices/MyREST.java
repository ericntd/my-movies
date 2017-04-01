package com.example.eric.mymovies.Webservices;

import com.example.eric.mymovies.BuildConfig;
import com.orhanobut.logger.Logger;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eric on 1/4/17.
 */

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
