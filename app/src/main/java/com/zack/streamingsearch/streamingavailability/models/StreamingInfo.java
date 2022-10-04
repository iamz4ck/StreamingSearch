package com.zack.streamingsearch.streamingavailability.models;

import com.google.gson.annotations.SerializedName;

public class StreamingInfo {
    /*
    Streaming info to hold services like
    Netflix, Hulu, peacock
    Total of 11 Services
     */
    @SerializedName("peacock")
    public StreamingService peacock;

    @SerializedName("netflix")
    public StreamingService netflix;

    @SerializedName("hulu")
    public StreamingService hulu;

    @SerializedName("prime")
    public StreamingService prime;

    @SerializedName("disney")
    public StreamingService disney;

    @SerializedName("hbo")
    public StreamingService hbo;

    @SerializedName("paramount")
    public StreamingService paramount;

    @SerializedName("starz")
    public StreamingService starz;

    @SerializedName("showtime")
    public StreamingService showtime;

    @SerializedName("apple")
    public StreamingService apple;

    @SerializedName("mubi")
    public StreamingService mubi;


}
