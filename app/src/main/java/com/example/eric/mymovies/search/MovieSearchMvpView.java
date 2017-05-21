package com.example.eric.mymovies.search;

import com.example.eric.mymovies.models.ConfigurationResponse;
import com.example.eric.mymovies.models.Movie;
import com.example.eric.mymovies.models.MoviesResponse;

import java.util.List;

import retrofit2.Response;

public interface MovieSearchMvpView {
    void showMovies(Response<MoviesResponse> response);

    void showMessage(int stringId);

    void showProgressIndicator();

    void updateConfiguration(Response<ConfigurationResponse> response);
}
