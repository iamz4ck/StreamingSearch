package com.zack.streamingsearch.tmdb;

import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;
import com.zack.streamingsearch.tmdb.models.TMDBPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBClient {

    //https://api.themoviedb.org/3/trending/all/day?api_key=<<api_key>>
    //https://developers.themoviedb.org/3/trending/get-trending

    //https://api.themoviedb.org/3/trending/all/week?api_key=<<api_key>>

    /*
    No Title Needed when querying Trending Weekly
     */
    @GET("/3/trending/all/week")
    Call<TMDBPage> getTMDBMediaTrendingWeek(@Query("api_key") String api_key);
}
