package com.example.eric.mymovies.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;

/**
 * Created by eric on 1/4/17.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    /**
     * In case there is a header and a footer, value would be 2
     */
    private static final int INITIAL_TOTAL_COUNT = 0;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 100; // 30/2; page size 30, 2 products on a row
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = INITIAL_TOTAL_COUNT; // the Header + the Footer are always there
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;

    private RecyclerView.LayoutManager mLayoutManager;
    private int mSpanCount = 1;

    EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        mSpanCount = layoutManager.getSpanCount();
        visibleThreshold = visibleThreshold * mSpanCount;
    }

    EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        mSpanCount = layoutManager.getSpanCount();
        visibleThreshold = visibleThreshold * mSpanCount;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (dy > 0) {
            Logger.i("real scroll");
            int lastVisibleItemPosition = 0;
            int totalItemCount = mLayoutManager.getItemCount();

            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and resetSearch the current page
            // number and total item count.
            if (totalItemCount > previousTotalItemCount) {
                Logger.i("loading finished");
                previousTotalItemCount = totalItemCount;
                return;
            }

            if (!loading) {
                Logger.i("loading more");
                currentPage++;
                visibleThreshold = (totalItemCount - lastVisibleItemPosition) / mSpanCount;
                onLoadMore(currentPage, totalItemCount, view);
            }
        } else {
            Logger.d("no scroll");
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    public void setLoading(boolean loading) {
        Logger.i("setLoading %b", loading);
        this.loading = loading;
    }
}
