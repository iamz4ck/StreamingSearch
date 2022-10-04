package com.zack.streamingsearch.streamingavailability.models;

import com.google.gson.annotations.SerializedName;

public class StreamingAvailabilityRequestModel {


    @SerializedName("age")
    public String age;

    @SerializedName("backdropPath")
    public String backdropPath;

    @SerializedName("backdropURLs")
    public BackDropURL backdropURLs;

    @SerializedName("cast")
    public String[] cast;

    @SerializedName("countries")
    public String[] countries;

    @SerializedName("genres")
    public String[] genres;

    @SerializedName("imdbID")
    public String imdbID;

    @SerializedName("imdbRating")
    public String imdbRating;

    @SerializedName("imdbVoteCount")
    public String imdbVoteCount;

    @SerializedName("originalTitle")
    public String originalTitle;

    @SerializedName("overview")
    public String overview;

    @SerializedName("posterPath")
    public String posterPath;

    @SerializedName("posterURLs")
    public PosterURLs posterURLs;

    @SerializedName("runtime")
    public String runtime;

    //API spells this this way
    @SerializedName("significants")
    public String[] significants;

    @SerializedName("streamingInfo")
    public StreamingInfo streamingInfo;

    @SerializedName("tagline")
    public String tagLine;

    @SerializedName("title")
    public String title;

    @SerializedName("tmdbID")
    public String tmdbID;

    @SerializedName("video")
    public String video;

    @SerializedName("year")
    public String year;

}
