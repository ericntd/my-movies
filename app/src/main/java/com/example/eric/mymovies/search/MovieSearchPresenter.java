package com.example.eric.mymovies.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.eric.mymovies.common.Presenter;
import com.example.eric.mymovies.R;
import com.example.eric.mymovies.webservices2.ConfigurationService;
import com.example.eric.mymovies.webservices2.MovieService;
import com.example.eric.mymovies.webservices2.ConfigurationResponse;
import com.example.eric.mymovies.webservices2.MoviesResponse;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchPresenter implements Presenter<MovieSearchMvpView> {

    private final ConfigurationService mConfigService;
    //    private Retrofit mRetrofit;
    private final MovieService mMovieService;
    private MovieSearchMvpView searchMvpView;

//    public MovieSearchPresenter(Retrofit retrofit) {
//        mRetrofit = retrofit;
//    }

    public MovieSearchPresenter(MovieService service, ConfigurationService configService) {
        mMovieService = service;
        mConfigService = configService;
    }

    @Override
    public void attachView(MovieSearchMvpView view) {
        searchMvpView = view;
    }

    @Override
    public void detachView() {
        searchMvpView = null;
    }

    public void searchMovies(@NonNull String query, int pageNo) {
        if (TextUtils.isEmpty(query) || query.trim().isEmpty()) return;

        searchMvpView.showProgressIndicator();
//        MovieService service = mRetrofit.create(MovieService.class);
        Call<MoviesResponse> call = mMovieService.searchMovies(query, pageNo);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call,
                                   Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    searchMvpView.showMovies(response);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Logger.e("onFailure", t);
                searchMvpView.showMessage(R.string.error_something_wrong);
            }
        });
    }

    /**
     * This should be called in Application class or a service
     * Configuration should be stored in a local data store
     * The view can then subscribe to the change ot the configuration
     */
    public void fetchConfiguration() {
        Call<ConfigurationResponse> call = mConfigService.fetchConfiguration();
        call.enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call,
                                   Response<ConfigurationResponse> response) {
                searchMvpView.updateConfiguration(response);
            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                Logger.e("onFailure", t);
            }
        });
    }
}
