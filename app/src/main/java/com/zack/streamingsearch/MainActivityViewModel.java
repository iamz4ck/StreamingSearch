package com.zack.streamingsearch;

import androidx.lifecycle.ViewModel;

import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    public boolean hasHitAPI = false;

    public ArrayList<TMDBMediaModel> mediaModels;

    public MainActivityViewModel() {

    }

    public void setMediaModels(ArrayList<TMDBMediaModel> mediaModels) {
        this.mediaModels = mediaModels;
    }

}
