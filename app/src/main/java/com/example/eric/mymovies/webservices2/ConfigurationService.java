package com.example.eric.mymovies.webservices2;

import com.example.eric.mymovies.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eric on 1/4/17.
 */

public interface ConfigurationService {

    @GET("configuration?api_key=" + BuildConfig.API_KEY)
    Call<ConfigurationResponse> fetchConfiguration();
}
