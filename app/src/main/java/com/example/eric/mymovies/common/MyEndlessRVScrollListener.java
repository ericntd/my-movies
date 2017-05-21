package com.example.eric.mymovies.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;

/**
 * Created by eric on 1/4/17.
 */

public class MyEndlessRVScrollListener extends EndlessRecyclerViewScrollListener {
    private final OnScrollEndListener mOnScrollEndListener;

    public MyEndlessRVScrollListener(LinearLayoutManager layoutManager, OnScrollEndListener listener) {
        super(layoutManager);
        this.mOnScrollEndListener = listener;
    }

    public MyEndlessRVScrollListener(GridLayoutManager layoutManager, OnScrollEndListener listener) {
        super(layoutManager);
        this.mOnScrollEndListener = listener;
    }

    public MyEndlessRVScrollListener(StaggeredGridLayoutManager layoutManager, OnScrollEndListener listener) {
        super(layoutManager);
        this.mOnScrollEndListener = listener;
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        Logger.i("onLoadMore - page %d - total item count %d", page, totalItemsCount);
        if (mOnScrollEndListener != null) {
            mOnScrollEndListener.onScrollEnd();
        }
    }

    public interface OnScrollEndListener {
        void onScrollEnd();
    }
}
