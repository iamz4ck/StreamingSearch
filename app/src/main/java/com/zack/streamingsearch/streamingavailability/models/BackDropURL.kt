package com.zack.streamingsearch.streamingavailability.models

import com.google.gson.annotations.SerializedName

data class BackDropURL(
    @SerializedName("1280")
    var highestResolution: String? = null,

    @SerializedName("300")
    var lowestResolution: String? = null,

    @SerializedName("780")
    var mediumResolution: String? = null,

    @SerializedName("original")
    var originalResolution: String? = null)