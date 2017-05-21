package com.example.eric.mymovies;

import com.example.eric.mymovies.search.MovieSearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MovieSearchFragment fragment);
}
