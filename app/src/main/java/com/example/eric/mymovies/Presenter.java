package com.example.eric.mymovies;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();
}
