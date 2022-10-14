package com.zack.streamingsearch;

import androidx.lifecycle.ViewModel;

import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;
import com.zack.streamingsearch.streamingavailability.models.StreamingAvailabilityRequestModel;

public class MovieDetailActivityViewModel extends ViewModel {

    public boolean hasHitOMDBAPI = false;
    public boolean hasHitStreamingAvailabilityAPI = false;
    public OpenMovieRequestModel openMovieRequestModel;
    public StreamingAvailabilityRequestModel streamingAvailabilityRequestModel;
    public String mediaID;
    public String mediaTitle;
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

    public OpenMovieRequestModel getOpenMovieRequestModel() {
        return openMovieRequestModel;
    }

    public StreamingAvailabilityRequestModel getStreamingAvailabilityRequestModel() {
        //Handle null returns
        return streamingAvailabilityRequestModel;
    }

    public String getMediaID() {
        //Handle null returns
        return mediaID;
    }

    public String getMediaTitle() {
        //Handle null returns
        return mediaTitle;
    }

    public String getMediaPosterURL() {
        //Handle null returns
        return mediaPosterURL;
    }

    public String getMediaBackDropURL() {
        //Handle null returns
        return mediaBackDropURL;
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
