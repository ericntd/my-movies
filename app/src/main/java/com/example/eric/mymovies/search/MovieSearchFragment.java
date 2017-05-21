package com.example.eric.mymovies.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.eric.mymovies.MyApp;
import com.example.eric.mymovies.common.MyEndlessRVScrollListener;
import com.example.eric.mymovies.R;
import com.example.eric.mymovies.models.ConfigurationResponse;
import com.example.eric.mymovies.models.ImageOptions;
import com.example.eric.mymovies.models.Movie;
import com.example.eric.mymovies.models.MoviesResponse;
import com.example.eric.mymovies.ui.MovieSearchAdapter;
import com.example.eric.mymovies.utils.MyJsonResponseUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieSearchFragment extends Fragment implements MyEndlessRVScrollListener.OnScrollEndListener,
        MovieSearchMvpView {
    private static final String ARG_QUERY = "ARG_QUERY";

    @Inject
    Retrofit mRetrofit;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.message_no_result)
    View messageNoResult;
    /**
     * Empty string can't be used so we need a starter keyword
     * why "big"? No particular reason, it's arbitrary
     */
    private String mQuery = "big";
    private MyEndlessRVScrollListener mEndlessScrollListener;
    private int mCurrentPage = 1;
    private int mTotalPageCount;
    private MovieSearchAdapter mAdapter;
    private MovieSearchFragmentCallback mListener;
    private MovieSearchPresenter mPresenter;

    public static MovieSearchFragment newInstance(String query) {

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);

        MovieSearchFragment fragment = new MovieSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);

        super.onCreate(savedInstanceState);
        String tmp = getArguments().getString(ARG_QUERY);
        if (!TextUtils.isEmpty(tmp)) mQuery = tmp;
        mAdapter = new MovieSearchAdapter();
        mPresenter = new MovieSearchPresenter(mRetrofit);
        mPresenter.attachView(this);

        fetchConfiguration();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieSearchFragmentCallback) {
            mListener = (MovieSearchFragmentCallback) context;
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void fetchConfiguration() {
        mPresenter.fetchConfiguration();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mEndlessScrollListener = new MyEndlessRVScrollListener(layoutManager, this);
        recyclerView.addOnScrollListener(mEndlessScrollListener);
        recyclerView.setAdapter(mAdapter);
    }

    private void fetchMovies() {
        Logger.i("fetchMovies");
        mPresenter.searchMovies(mQuery, mCurrentPage);
    }

    private void render(List<Movie> movies) {
        mAdapter.addItems(movies);
    }

    private void onErrorMovies(String errorMessage) {
        Logger.w("onErrorMovies %s", errorMessage);
    }

    /**
     * Fetch the next page of movies immediately for smoother scrolling
     */
    @Override
    public void onScrollEnd() {
        Logger.i("onScrollEnd");
        if (mCurrentPage < mTotalPageCount) {
            mCurrentPage++;
            Logger.i("fetching movies page number %d", mCurrentPage);
            fetchMovies();
        } else {
            recyclerView.removeOnScrollListener(mEndlessScrollListener);
        }
    }

    void setLoading(boolean b) {
        Logger.i("setLoading " + b);
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.setLoading(b);
        }
    }

    @Override
    public void onDestroyView() {
        recyclerView.removeOnScrollListener(mEndlessScrollListener);
        super.onDestroyView();
    }

    public void resetSearch(String query) {
        mQuery = query;
        mCurrentPage = 1;
        mAdapter.clear();
        fetchMovies();
    }

    @Override
    public void showMovies(Response<MoviesResponse> response) {
        setLoading(false);
        hideKeyboard();
        if (mListener != null) mListener.onFetchedMovies();
        if (response.isSuccessful()) {
            MoviesResponse tmp = response.body();
            if (tmp.getResults() == null || tmp.getResults().size() == 0) {
                messageNoResult.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                messageNoResult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            List<Movie> movies = tmp.getResults();
            mCurrentPage = tmp.getPage();
            mTotalPageCount = tmp.getTotalPages();
            render(movies);
        } else {
            String errorMessage = MyJsonResponseUtils.extractErrMsg(response.errorBody());
            onErrorMovies(errorMessage);
        }
    }

    @Override
    public void showMessage(int stringId) {
        setLoading(false);
        hideKeyboard();
    }

    @Override
    public void showProgressIndicator() {
        setLoading(true);
    }

    @Override
    public void updateConfiguration(Response<ConfigurationResponse> response) {
        ImageOptions options = response.body().getImages();
        // Assumption: second smallest poster size is optimal here, now it's 154px, even if it changes, should still be
        // good
        String mListItemImageSize = options.getPosterSizes().get(2);
        mAdapter.setImageConfig(options.getBaseUrl(), mListItemImageSize);
    }

    private void hideKeyboard() {
        if (getActivity() == null || !isAdded()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View rootView = getView();
        if (rootView != null) {
            Log.v(this.getClass().getSimpleName(), "root view not null " + rootView);
            rootView.requestFocus();
            imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
        }
    }

    /**
     * Interface to communicate back to the activity
     */
    public interface MovieSearchFragmentCallback {
        void onFetchedMovies();
    }
}
