package com.zack.streamingsearch.streamingavailability.chips;

import android.content.Context;

import com.google.android.material.chip.Chip;

public class ChipStreamingService extends Chip {

    public String chipURL;
    public boolean serviceAvailability;

    public ChipStreamingService(Context context , String chipURL) {
        super(context);
        this.chipURL = chipURL;
    }
}
