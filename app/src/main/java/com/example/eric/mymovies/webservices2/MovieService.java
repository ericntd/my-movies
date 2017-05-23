package com.example.eric.mymovies.webservices2;

import com.example.eric.mymovies.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("search/movie?api_key=" + BuildConfig.API_KEY)
    Call<MoviesResponse> searchMovies(@Query("query") String query, @Query("page") int pageNo);
}
