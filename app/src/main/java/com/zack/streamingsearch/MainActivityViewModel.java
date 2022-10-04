package com.zack.streamingsearch;

import androidx.lifecycle.ViewModel;

import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private final boolean debug = true;
    public boolean hasHitAPI = false;

    public ArrayList<TMDBMediaModel> mediaModels;

    public MainActivityViewModel() {
        printLine("ViewModel Created.");
    }

    public void printLine(String textToPrintOut) {
        if(debug) {
            System.out.println("[DEBUG] " + textToPrintOut + System.lineSeparator());
        }
    }

    public void setMediaModels(ArrayList<TMDBMediaModel> mediaModels) {
        this.mediaModels = mediaModels;
        printLine("Setting MediaModels in ViewModel.");
    }

}
