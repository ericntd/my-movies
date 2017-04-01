package com.example.eric.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eric on 1/4/17.
 */

public class ImageOptions {

    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("secure_base_url")
    @Expose
    private String secureBaseUrl;
    @SerializedName("backdrop_sizes")
    @Expose
    private List<String> backdropSizes = null;
    @SerializedName("logo_sizes")
    @Expose
    private List<String> logoSizes = null;
    @SerializedName("poster_sizes")
    @Expose
    private List<String> posterSizes = null;
    @SerializedName("profile_sizes")
    @Expose
    private List<String> profileSizes = null;
    @SerializedName("still_sizes")
    @Expose
    private List<String> stillSizes = null;

    /**
     * No args constructor for use in serialization
     */
    public ImageOptions() {
    }

    /**
     * @param baseUrl
     * @param profileSizes
     * @param posterSizes
     * @param logoSizes
     * @param stillSizes
     * @param backdropSizes
     * @param secureBaseUrl
     */
    public ImageOptions(String baseUrl, String secureBaseUrl, List<String> backdropSizes, List<String> logoSizes,
                        List<String> posterSizes, List<String> profileSizes, List<String> stillSizes) {
        super();
        this.baseUrl = baseUrl;
        this.secureBaseUrl = secureBaseUrl;
        this.backdropSizes = backdropSizes;
        this.logoSizes = logoSizes;
        this.posterSizes = posterSizes;
        this.profileSizes = profileSizes;
        this.stillSizes = stillSizes;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }
}
