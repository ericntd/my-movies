package com.example.eric.mymovies.search;

import com.example.eric.mymovies.webservices2.ConfigurationResponse;
import com.example.eric.mymovies.webservices2.MoviesResponse;

import retrofit2.Response;

public interface MovieSearchMvpView {
    void showMovies(Response<MoviesResponse> response);

    void showMessage(int stringId);

    void showProgressIndicator();

    void updateConfiguration(Response<ConfigurationResponse> response);
}
