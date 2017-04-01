package com.example.eric.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 1/4/17.
 */

public class MoviesResponse {
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private ArrayList<Movie> results = null;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
            
    /**
     * No args constructor for use in serialization
     *
     */
    public MoviesResponse() {
    }

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public MoviesResponse(int page, ArrayList<Movie> results, int totalResults, int totalPages) {
        super();
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
