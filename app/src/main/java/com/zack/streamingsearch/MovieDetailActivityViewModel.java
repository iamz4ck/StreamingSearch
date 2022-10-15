package com.zack.streamingsearch;

import androidx.lifecycle.ViewModel;

import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;
import com.zack.streamingsearch.streamingavailability.models.Service;
import com.zack.streamingsearch.streamingavailability.models.StreamingAvailabilityRequestModel;

import java.util.ArrayList;

public class MovieDetailActivityViewModel extends ViewModel {

    public boolean hasHitOMDBAPI = false;
    public boolean hasHitStreamingAvailabilityAPI = false;
    public OpenMovieRequestModel openMovieRequestModel;
    public StreamingAvailabilityRequestModel streamingAvailabilityRequestModel;
    public ArrayList<Service> streamingServicesData;
    public String mediaID;
    public String mediaPosterURL;
    public String mediaBackDropURL;
    public String mediaRatings;
    public String mediaData;

    public void setMediaRatings(String mediaRatings) {
        this.mediaRatings = mediaRatings;
    }

    public void setMediaData(String mediaData) {
        this.mediaData = mediaData;
    }

    public void setStreamingServicesData(ArrayList<Service> services) {
        this.streamingServicesData = services;
    }

    public void setMediaPosterURL(String url) {
        this.mediaPosterURL = url;
    }

    public void setHasAlreadyHitAPIForStreamingAvailability(boolean hasHitStreamingAvailabilityAPI) {
        this.hasHitStreamingAvailabilityAPI = hasHitStreamingAvailabilityAPI;
    }

    public void setOpenMovieRequestModel(OpenMovieRequestModel openMovieRequestModel) {
        this.openMovieRequestModel = openMovieRequestModel;
    }

    public void setStreamingAvailabilityRequestModel(StreamingAvailabilityRequestModel streamingAvailabilityRequestModel) {
        this.streamingAvailabilityRequestModel = streamingAvailabilityRequestModel;
    }

    public void setMediaBackDropURL(String mediaBackDropURL) {
        this.mediaBackDropURL = mediaBackDropURL;
    }

    public void setHasHitOMDBAPI(boolean hasHitOMDBAPI) {
        this.hasHitOMDBAPI = hasHitOMDBAPI;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }
}
