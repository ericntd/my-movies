package com.example.eric.mymovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.eric.mymovies.Webservices.MovieService;
import com.example.eric.mymovies.models.Movie;
import com.example.eric.mymovies.models.MoviesResponse;
import com.example.eric.mymovies.utils.MyJsonResponseUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchMovies();
    }

    private void fetchMovies() {
        Logger.i("fetchMovies");
    }

    private void handleResponse(Response<MoviesResponse> response) {
        Logger.i("handleResponse");
        if (response.isSuccessful()) {
            MoviesResponse tmp = response.body();
            List<Movie> movies = tmp.getResults();
            render(movies);
        } else {
            String errorMessage = MyJsonResponseUtils.extractErrMsg(response.errorBody());
            handleFailure(errorMessage);
        }
    }

    private void render(List<Movie> movies) {
    }

    private void handleFailure(String errorMessage) {
    }

    private void handleFailure() {
    }
}
