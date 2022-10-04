package com.zack.streamingsearch.openmoviedb.models

import com.google.gson.annotations.SerializedName

data class OpenMovieRequestStoryModel(
    @SerializedName("Plot")
    var plot: String? = null
)
