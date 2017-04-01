package com.example.eric.mymovies;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.eric.mymovies.ui.MovieSearchFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Runnable, MovieSearchFragment.MovieSearchFragmentCallback {
    private final int TIME_BTWN_TYPING_MS = 450;

    /**
     * value from the search bar
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
            mHandler.removeCallbacks(MainActivity.this);
            mQuery = newText;
            if (newText.isEmpty()) {
                progressBar.setVisibility(View.GONE);
            } else {
                mHandler.postDelayed(MainActivity.this, TIME_BTWN_TYPING_MS);
            }
            return true;
        }
    };

    private void resetSearch() {
        Logger.i("resetSearch");
        if (mFrag != null) {
            progressBar.setVisibility(View.VISIBLE);
            mFrag.search(mQuery);
        }
    }

    private void render() {
        searchView.setQuery(mQuery, true);
        searchView.setOnQueryTextListener(mSearchQueryListener);
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mHandler.removeCallbacks(SearchActivity.this);
//                mRecyclerViewSearch2.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
//                mAdapter2.clear();
//                return true;
//            }
//        });

        mFrag = MovieSearchFragment.newInstance(mQuery);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, mFrag).commit();
    }

    @Override
    public void run() {
        Logger.i("run");
        resetSearch();
    }

    @Override
    public void onFetchedMovies() {
        progressBar.setVisibility(View.GONE);
    }
}
