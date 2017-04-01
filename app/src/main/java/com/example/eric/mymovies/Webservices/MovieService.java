package com.example.eric.mymovies.Webservices;

import com.example.eric.mymovies.BuildConfig;
import com.example.eric.mymovies.models.Movie;
import com.example.eric.mymovies.models.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eric on 1/4/17.
 */

public interface MovieService {
    MovieService service = MyREST.retrofit.create(MovieService.class);

    @GET("search/movie?api_key=" + BuildConfig.API_KEY)
    Call<MoviesResponse> searchMovies(@Query("query") String query, @Query("page") int pageNo);
}
