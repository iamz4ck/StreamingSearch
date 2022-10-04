package com.zack.streamingsearch.openmoviedb;

import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestGameModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestMovieSearchModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestStoryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMovieDatabaseClient {

    @GET("/")
    Call<OpenMovieRequestModel> getMovieDataByTitleSearch(@Query("t") String movieID, @Query("apikey") String apiKey);

    @GET("/")
    Call<OpenMovieRequestModel> getMediaDataByIDShortOrLong(@Query("i") String mediaID, @Query("plot") String plot, @Query("apikey") String apikey);

    @GET("/")
    Call<OpenMovieRequestModel> getMediaDataByIDShortOrLongWithPage(@Query("i") String mediaID, @Query("page") String page ,@Query("plot") String plot, @Query("apikey") String apikey);

    @GET("/")
    Call<OpenMovieRequestGameModel> getGameDataByIDShort(@Query("i") String gameID, @Query("plot") String plot, @Query("apikey") String apikey);

    @GET("/")
    Call<OpenMovieRequestStoryModel> getMovieDataByIDStoryLong(@Query("i") String movieID, @Query("plot") String plot, @Query("apikey") String apiKey);

    @GET("/")
    Call<OpenMovieRequestMovieSearchModel> getOpenMovieDBSearchWithType(@Query("s") String movieTitle, @Query("type") String movieType, @Query("apikey") String apiKey);

    @GET("/")
    Call<OpenMovieRequestMovieSearchModel> getOpenMovieDBSearchNoType(@Query("s") String mediaTitle, @Query("apikey") String apiKey);

    @GET("/")
    Call<OpenMovieRequestMovieSearchModel> getOpenMovieDBSearchNoTypeWithPage(@Query("s") String mediaTitle, @Query("page") String pageNumber, @Query("apikey") String apiKey);

    //http://www.omdbapi.com/?apikey=b36685ad&s=harry+potter

}
