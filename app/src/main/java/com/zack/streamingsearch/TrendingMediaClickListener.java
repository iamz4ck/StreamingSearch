package com.zack.streamingsearch;

import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;

public interface TrendingMediaClickListener {
    void onClick(TMDBMediaModel tmdbMediaModel);
}
