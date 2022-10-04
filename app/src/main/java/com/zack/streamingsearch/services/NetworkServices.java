package com.zack.streamingsearch.services;


import com.zack.streamingsearch.openmoviedb.OpenMovieDatabaseClient;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestGameModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestMovieSearchModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestStoryModel;
import com.zack.streamingsearch.streamingavailability.StreamingAvailabilityClient;
import com.zack.streamingsearch.streamingavailability.models.StreamingAvailabilityRequestModel;
import com.zack.streamingsearch.tmdb.TMDBClient;
import com.zack.streamingsearch.tmdb.models.TMDBConfigInfo;
import com.zack.streamingsearch.tmdb.models.TMDBPage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServices {

    public static Retrofit streamingAvailabilityRetrofit;
    public static Retrofit openMovieDatabaseRetrofit;
    public static Retrofit tmdbDatabaseRetrofit;

    public static Retrofit.Builder retrofitBuilderStreamAvailability;
    public static Retrofit.Builder retrofitBuilderOpenMovieDatabase;
    public static Retrofit.Builder retrofitBuilderTMDB;

    public static StreamingAvailabilityClient streamingAvailabilityClient;
    public static OpenMovieDatabaseClient openMovieDatabaseClient;
    public static TMDBClient tmdbClient;

    private static final String streamingAvailabilityServiceURL = "https://streaming-availability.p.rapidapi.com";
    private static final String openMovieBaseURL = "https://www.omdbapi.com/";
    private static final String apiKey = "b36685ad";

    public static void buildTMDBRetroFitBase() {
        retrofitBuilderTMDB = new Retrofit.Builder()
                .baseUrl(TMDBConfigInfo.apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create());
        tmdbDatabaseRetrofit = retrofitBuilderTMDB.build();
        tmdbClient = (TMDBClient) tmdbDatabaseRetrofit.create(TMDBClient.class);
    }

    public static void buildOpenMovieDatabaseRetroFitBase() {
        retrofitBuilderOpenMovieDatabase = new Retrofit.Builder()
                .baseUrl(openMovieBaseURL)
                .addConverterFactory(GsonConverterFactory.create());
        openMovieDatabaseRetrofit = retrofitBuilderOpenMovieDatabase.build();
        openMovieDatabaseClient = (OpenMovieDatabaseClient) openMovieDatabaseRetrofit.create(OpenMovieDatabaseClient.class);
    }

    public static void buildStreamingAvailabilityRetroFitBase() {
        retrofitBuilderStreamAvailability = new Retrofit.Builder()
                .baseUrl(streamingAvailabilityServiceURL)
                .addConverterFactory(GsonConverterFactory.create());
        streamingAvailabilityRetrofit = retrofitBuilderStreamAvailability.build();
        streamingAvailabilityClient = (StreamingAvailabilityClient) streamingAvailabilityRetrofit.create(StreamingAvailabilityClient.class);

    }

    //Able to search for game titles as well
    public static void callOpenMovieDatabaseSearchWithType(String movieTitle, String type, Callback<OpenMovieRequestMovieSearchModel> callback) {
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestMovieSearchModel> call = openMovieDatabaseClient.getOpenMovieDBSearchWithType(movieTitle, type, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseSearchWithType(movieTitle, type, callback);
        }
    }

    public static void callOpenMovieDatabaseSearch(String mediaTitle, Callback<OpenMovieRequestMovieSearchModel> callback) {
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestMovieSearchModel> call = openMovieDatabaseClient.getOpenMovieDBSearchNoType(mediaTitle, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseSearch(mediaTitle, callback);
        }
    }

    public static void callOpenMovieDatabaseSearchWithPage(String mediaTitle,String pageNumber, Callback<OpenMovieRequestMovieSearchModel> callback) {
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestMovieSearchModel> call = openMovieDatabaseClient.getOpenMovieDBSearchNoTypeWithPage(mediaTitle, pageNumber, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseSearchWithPage(mediaTitle, pageNumber, callback);
        }
    }

    public static void callOpenMovieDatabaseGameSearch(String gameID, Callback<OpenMovieRequestGameModel> callback) {
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestGameModel> call = openMovieDatabaseClient.getGameDataByIDShort(gameID, "short",apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseGameSearch(gameID, callback);
        }
    }

    public static void callOpenMovieDatabaseShortWithTitle(String movieTitle, Callback<OpenMovieRequestModel> callback) {
        System.out.println("[NetworkServices] USING DATA.");
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestModel> call = openMovieDatabaseClient.getMovieDataByTitleSearch(movieTitle, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseShortWithTitle(movieTitle, callback);
        }
    }

    public static void callOpenMovieDatabaseLongOrShortByID(String mediaID, String plot ,Callback<OpenMovieRequestModel> callback) {
        System.out.println("[NetworkServices] Using data movieID=" + mediaID);
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestModel> call = openMovieDatabaseClient.getMediaDataByIDShortOrLong(mediaID, plot, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseLongOrShortByID(mediaID, plot, callback);
        }
    }

    public static void callOpenMovieDatabaseLongOrShortByIDWithPage(String mediaID, String plot ,Callback<OpenMovieRequestModel> callback, String pageNumber) {
        System.out.println("[NetworkServices] Using data movieID=" + mediaID);
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestModel> call = openMovieDatabaseClient.getMediaDataByIDShortOrLongWithPage(mediaID, pageNumber, plot, apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseLongOrShortByIDWithPage(mediaID, plot, callback, pageNumber);
        }
    }

    public static void callOpenMovieDatabaseLong(String movieID, Callback<OpenMovieRequestStoryModel> callback) {
        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestStoryModel> call = openMovieDatabaseClient.getMovieDataByIDStoryLong(movieID, "full", apiKey);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            callOpenMovieDatabaseLong(movieID, callback);
        }
    }

    public static void callStreamingAvailability(String movieID, Callback<StreamingAvailabilityRequestModel> callback) {
        if(streamingAvailabilityClient != null) {
            Call<StreamingAvailabilityRequestModel> call = streamingAvailabilityClient.getStreamingAvailability("us", movieID);
            call.enqueue(callback);
        } else {
            buildStreamingAvailabilityRetroFitBase();
            callStreamingAvailability(movieID, callback);
        }
    }

    public static void callTMDBTrendingWeekly(Callback<TMDBPage> callback) {
        if(tmdbClient != null) {
            Call call = tmdbClient.getTMDBMediaTrendingWeek(TMDBConfigInfo.apikey);
            call.enqueue(callback);
        } else {
            buildTMDBRetroFitBase();
            callTMDBTrendingWeekly(callback);
        }
    }
}
