package com.example.eric.mymovies.common;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();
}
