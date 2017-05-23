package com.example.eric.mymovies.webservices2;

import com.example.eric.mymovies.models.ImageOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eric on 1/4/17.
 */

public class ConfigurationResponse {
    @SerializedName("images")
    @Expose
    private ImageOptions images;
    @SerializedName("change_keys")
    @Expose
    private List<String> changeKeys = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ConfigurationResponse() {
    }

    /**
     *
     * @param images
     * @param changeKeys
     */
    public ConfigurationResponse(ImageOptions images, List<String> changeKeys) {
        super();
        this.images = images;
        this.changeKeys = changeKeys;
    }

    public ImageOptions getImages() {
        return images;
    }

    public void setImages(ImageOptions images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }
}
