package com.example.eric.mymovies.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.eric.mymovies.Presenter;
import com.example.eric.mymovies.R;
import com.example.eric.mymovies.webservices.ConfigurationService;
import com.example.eric.mymovies.webservices.MovieService;
import com.example.eric.mymovies.models.ConfigurationResponse;
import com.example.eric.mymovies.models.MoviesResponse;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieSearchPresenter implements Presenter<MovieSearchMvpView> {

    private final Retrofit mRetrofit;
    private MovieSearchMvpView searchMvpView;

    public MovieSearchPresenter(Retrofit retrofit) {
        mRetrofit = retrofit;
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
        MovieService service = mRetrofit.create(MovieService.class);
        Call<MoviesResponse> call = service.searchMovies(query, pageNo);
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
        ConfigurationService service = mRetrofit.create(ConfigurationService.class);
        Call<ConfigurationResponse> call = service.fetchConfiguration();
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
