package com.zack.streamingsearch.streamingavailability;

import com.zack.streamingsearch.streamingavailability.models.StreamingAvailabilityRequestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface StreamingAvailabilityClient {

    @Headers({"x-rapidapi-host: streaming-availability.p.rapidapi.com", "x-rapidapi-key: 70b1773494msh394b9e1abcc2fbbp1d8dd8jsnac2ec1e0a3fa"})
    @GET("/get/basic")
    Call<StreamingAvailabilityRequestModel> getStreamingAvailability(@Query("country") String country, @Query("imdb_id") String imdb_id);
}
