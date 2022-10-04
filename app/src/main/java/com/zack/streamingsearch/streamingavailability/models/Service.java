package com.zack.streamingsearch.streamingavailability.models;

import com.google.gson.annotations.SerializedName;

public class Service {
    /*
        "Service" holding information on when added and when its leaving
        the service
         */
    public String serviceName;
    public boolean isAvailable = false;

    @SerializedName("link")
    public String link;
    @SerializedName("added")
    public String added;
    @SerializedName("leaving")
    public String leaving;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
