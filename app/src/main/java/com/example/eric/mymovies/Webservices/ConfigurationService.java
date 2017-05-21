package com.example.eric.mymovies.Webservices;

import com.example.eric.mymovies.BuildConfig;
import com.example.eric.mymovies.models.ConfigurationResponse;
import com.example.eric.mymovies.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eric on 1/4/17.
 */

public interface ConfigurationService {

    @GET("configuration?api_key=" + BuildConfig.API_KEY)
    Call<ConfigurationResponse> fetchConfiguration();
}
