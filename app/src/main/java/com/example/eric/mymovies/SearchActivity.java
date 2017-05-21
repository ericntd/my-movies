package com.example.eric.mymovies;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.eric.mymovies.search.MovieSearchFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements Runnable, MovieSearchFragment.MovieSearchFragmentCallback {
    public final int TIME_BETWEEN_TYPING_MS = 450;
    /**
     * Search query
     */
    private String mQuery = "big";

    @BindView(R.id.searchview)
    SearchView searchView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private MovieSearchFragment mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHandler = new Handler();
        render();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler != null && searchView != null) {
            String query = searchView.getQuery().toString();
            if (!query.isEmpty()) {
                mHandler.post(this);
            }
        }
    }

    /**
     * Handler to trigger search every {@link #TIME_BETWEEN_TYPING_MS} milliseconds after user stops typing
     */
    private Handler mHandler;
    SearchView.OnQueryTextListener mSearchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Logger.i("onQueryTextSubmit %s", query);
            progressBar.setVisibility(View.GONE);
            mQuery = query;
            resetSearch();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Logger.i("onQueryTextSubmit %s", newText);
            mHandler.removeCallbacks(SearchActivity.this);
            mQuery = newText;
            if (newText.isEmpty()) {
                progressBar.setVisibility(View.GONE);
            } else {
                mHandler.postDelayed(SearchActivity.this, TIME_BETWEEN_TYPING_MS);
            }
            return true;
        }
    };

    private void resetSearch() {
        Logger.i("resetSearch");
        if (mFrag != null) {
            progressBar.setVisibility(View.VISIBLE);
            mFrag.resetSearch(mQuery);
        }
    }

    private void render() {
        searchView.setQuery(mQuery, true);
        searchView.setOnQueryTextListener(mSearchQueryListener);

        mFrag = MovieSearchFragment.newInstance(mQuery);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, mFrag).commit();
    }

    @Override
    public void run() {
        Logger.i("run");
        resetSearch();
    }

    /**
     * Hide the progressbar as soon as the 1st page of movies are fetched
     */
    @Override
    public void onFetchedMovies() {
        progressBar.setVisibility(View.GONE);
    }
}
