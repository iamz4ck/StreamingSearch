package com.zack.streamingsearch.tmdb.models;

import com.google.gson.annotations.SerializedName;

public class TMDBPage {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public TMDBMediaModel[] results;

}
