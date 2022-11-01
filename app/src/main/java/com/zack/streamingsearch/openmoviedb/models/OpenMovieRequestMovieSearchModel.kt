package com.zack.streamingsearch.openmoviedb.models

import com.google.gson.annotations.SerializedName
import com.zack.streamingsearch.searchresults.MediaSearch

data class OpenMovieRequestMovieSearchModel(
    @SerializedName("Search")
    var mediaSearches: Array<MediaSearch?>?,

    @SerializedName("totalResults")
    val totalResults: String? = null,

    @SerializedName("Response")
    val response: String? = null,

    @SerializedName("Error")
    val error: String? = null
)


