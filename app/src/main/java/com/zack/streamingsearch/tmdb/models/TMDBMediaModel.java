package com.zack.streamingsearch.tmdb.models;

import com.google.gson.annotations.SerializedName;

public class TMDBMediaModel {

    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String release_date;

    /*
    @SerializedName("genre_ids")
    private TMDBGenreID[] genre_ids;
     */

    @SerializedName("id")
    public int id;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("title")
    public String title;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("vote_count")
    public int vote_count;

    @SerializedName("video")
    public boolean video;

    @SerializedName("vote_average")
    public double vote_average;

}
