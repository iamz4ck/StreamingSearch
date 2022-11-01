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
        tmdbClient = tmdbDatabaseRetrofit.create(TMDBClient.class);
    }

    public static void buildOpenMovieDatabaseRetroFitBase() {
        retrofitBuilderOpenMovieDatabase = new Retrofit.Builder()
                .baseUrl(openMovieBaseURL)
                .addConverterFactory(GsonConverterFactory.create());
        openMovieDatabaseRetrofit = retrofitBuilderOpenMovieDatabase.build();
        openMovieDatabaseClient = openMovieDatabaseRetrofit.create(OpenMovieDatabaseClient.class);
    }

    public static void buildStreamingAvailabilityRetroFitBase() {
        retrofitBuilderStreamAvailability = new Retrofit.Builder()
                .baseUrl(streamingAvailabilityServiceURL)
                .addConverterFactory(GsonConverterFactory.create());
        streamingAvailabilityRetrofit = retrofitBuilderStreamAvailability.build();
        streamingAvailabilityClient = streamingAvailabilityRetrofit.create(StreamingAvailabilityClient.class);

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

        if(openMovieDatabaseClient != null) {
            Call<OpenMovieRequestModel> call = openMovieDatabaseClient.getMovieDataByTitleSearch(movieTitle, apiKey);
            System.out.println("[NetworkServices] callOpenMovieDatabaseShortWithTitle() USING DATA. " + " Movie Title: " + movieTitle);
            call.enqueue(callback);
        } else {
            buildOpenMovieDatabaseRetroFitBase();
            System.out.println("[NetworkServices] creating retrofit connection...");
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
            System.out.println("[NetworkServices] callTMDBTrendingWeekly() USING DATA.");
            call.enqueue(callback);
        } else {
            buildTMDBRetroFitBase();
            System.out.println("[NetworkServices] creating retrofit connection...");
            callTMDBTrendingWeekly(callback);
        }
    }

    //Need these Methods done before we are able to store in mysql DB. -
    //Need to replace comma with special character then replace again upon receiving from DB
    public static String getSanitizedSQLActors(OpenMovieRequestModel openMovieRequestModel) {
        String actorsRaw = openMovieRequestModel.getActors();
        return actorsRaw.replace(",", " ");
    }

    public static String getSanitizedSQLPlot(OpenMovieRequestModel openMovieRequestModel) {
        return openMovieRequestModel.getPlot().replace("'", " ");
    }

    public static String getSanitizedSQLRatings(OpenMovieRequestModel openMovieRequestModel) {
        String result = "";
        if(openMovieRequestModel.getRatings().length > 0) {
            result = openMovieRequestModel.getRatings()[0].getSource() + " Score: " + openMovieRequestModel.getRatings()[0].getValue() + " ";
        }
        if(openMovieRequestModel.getRatings().length > 1) {
            result += openMovieRequestModel.getRatings()[1].getSource() + " Score: " + openMovieRequestModel.getRatings()[1].getValue() + " ";
        }
        if(openMovieRequestModel.getRatings().length > 2) {
            result += openMovieRequestModel.getRatings()[2].getSource() + " Score: " + openMovieRequestModel.getRatings()[2].getValue() + " ";
        }
        return result;
    }

    ////end mysql DB methods we need -- ^

    public static String createMysqlToInsertQueryDataIntoZDB(String tableName, OpenMovieRequestModel openMovieRequestModel, StreamingAvailabilityRequestModel streamingAvailabilityRequestModel) {
        String mySQLInput = "INSERT INTO " + tableName + " ";
        String mySQLElementSeparator = ", ";
        String mySQLResult = mySQLInput + "VALUES" + "(" +
                "'" + openMovieRequestModel.getTitle() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getYear() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getRated() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getReleased() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getRuntime() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getGenre() + "'" + //Needs to be sanitized for mysql
                mySQLElementSeparator + "'" + openMovieRequestModel.getDirector() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getWriter() + "'" + //Needs to be sanitized for mysql
                mySQLElementSeparator + "'" + getSanitizedSQLActors(openMovieRequestModel) + "'" +
                mySQLElementSeparator + "'" + getSanitizedSQLPlot(openMovieRequestModel) + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getLanguage() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getCountry() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getAwards() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getPoster() + "'" +
                mySQLElementSeparator + "'" + getSanitizedSQLRatings(openMovieRequestModel) + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getMetaScore() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getImdbRating() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getImdbVotes() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getImdbID() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getType() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getDvd() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getBoxOffice() + "'" + //Needs to be sanitized for mysql has commas
                mySQLElementSeparator + "'" + openMovieRequestModel.getProduction() + "'" +
                mySQLElementSeparator + "'" + openMovieRequestModel.getWebsite() + "'" +
                ");";
                //Need to add Streaming Ava to this mySql statement.
        return mySQLResult;
    }

    /*
    omdb info
    -title
    -year
    -rated
    -released
    -runtime
    -genre
    -director
    -writer
    -actors
    -movie plot 'needs scrubbed of chars to store as string in sql'
    -language
    -country
    -awards
    -poster url
    -Ratings 'as string'
    -MetaScore
    -imdbRating
    -imdbVotes
    -imdbID
    -type
    -dvd
    -Box office
    -production
    -website
    -date added
    -response 'probably dont need'

    Streaming ava info
    -peacock
    -netflix
    -hulu
    -prime
    -disney
    -hbo
    -paramount
    -starz
    -showtime
    -apple
    -mubi
    -------------
    -age
    -backdropPath
    -backdropURLs 'contains urls for multiple resolutions'
    -cast
    -countries
    -genres
    -imdbID
    -imdbRating
    -imdbVoteCount
    -originalTitle
    -overview
    -posterPath
    -posterURLs 'contains urls for multiple resolutions'
    -runtime
    -significants 'API spells this this way'
    -streamingInfo 'contains which stream media is on'
    -tagline
    -title
    -tmdbID
    -video
    -year
     */
}
