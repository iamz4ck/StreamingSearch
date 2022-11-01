package com.zack.streamingsearch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;
import com.zack.streamingsearch.databinding.ActivityMovieDetailBinding;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestMovieSearchModel;
import com.zack.streamingsearch.openmoviedb.models.Rating;
import com.zack.streamingsearch.searchresults.MediaSearchResultsActivity;
import com.zack.streamingsearch.services.MovieTextElementService;
import com.zack.streamingsearch.services.NetworkServices;
import com.zack.streamingsearch.streamingavailability.chips.ChipStreamingService;
import com.zack.streamingsearch.streamingavailability.models.Service;
import com.zack.streamingsearch.streamingavailability.models.StreamingAvailabilityRequestModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {


    public MovieDetailActivityViewModel movieDetailActivityViewModel;
    public ActivityMovieDetailBinding activityMovieDetailBinding;
    public Handler handler;
    public ArrayList<ChipStreamingService> chipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        handler = new Handler();
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        activityMovieDetailBinding.setLifecycleOwner(this);
        movieDetailActivityViewModel = new ViewModelProvider(this).get(MovieDetailActivityViewModel.class);
        String query = getIntent().getExtras().getString("query").trim();
        configureSearchViewProperties();
        updateBackDropImageViewWithClear();

        //Need to implement a mediaType when starting activity
        //to handle series and games

        if(!movieDetailActivityViewModel.hasHitOMDBAPI) {
            //*Check Streaming Ava Api doc to see if it can take TMDB ids as well*
            NetworkServices.callOpenMovieDatabaseShortWithTitle(query, omrmCallBackShort);
            //Log info in mySQL DB once website or raspberry pi setup
        } else {
            updateMoviePosterImageView(movieDetailActivityViewModel.mediaPosterURL);
            updateMovieRatings(movieDetailActivityViewModel.mediaRatings);
            updateMovieDataTextView(movieDetailActivityViewModel.openMovieRequestModel);
            updateStreamingServices(movieDetailActivityViewModel.streamingServicesData);
            updateBackDropImageView(movieDetailActivityViewModel.mediaBackDropURL);
            testPrintOutOfMysql(movieDetailActivityViewModel.openMovieRequestModel, movieDetailActivityViewModel.streamingAvailabilityRequestModel);
        }
    }

    public void testPrintOutOfMysql(OpenMovieRequestModel openMovieRequestModel, StreamingAvailabilityRequestModel streamingAvailabilityRequestModel) {
        String testResult = NetworkServices.createMysqlToInsertQueryDataIntoZDB("MEDIA_TABLE", openMovieRequestModel, streamingAvailabilityRequestModel);
        System.out.println(testResult);
    }

    /**
     * Updates RatingsTextView with movie information using Handler.
     *
     * @param updateText
     */
    public void updateMovieRatings(String updateText) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                movieDetailActivityViewModel.setMediaRatings(updateText);
                activityMovieDetailBinding.ratingsTextView.setText(updateText);
            }
        });
    }

    /**
     * Updates BackDropImageView with Picasso Library using
     * handler and a URL.
     *
     * @param url
     */
    public void updateBackDropImageView(String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                movieDetailActivityViewModel.setMediaBackDropURL(url);
                activityMovieDetailBinding.backDropImageView.setTag(url);
                Picasso.get().load(url).into(activityMovieDetailBinding.backDropImageView);
            }
        });
    }

    /**
     * Updates MoviePosterImageView with Picasso library using
     * Handler and a URL.
     *
     * @param url
     */
    public void updateMoviePosterImageView(String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                movieDetailActivityViewModel.setMediaPosterURL(url);
                activityMovieDetailBinding.moviePosterImageView.setTag(url);
                Picasso.get().load(url).into(activityMovieDetailBinding.moviePosterImageView);
            }
        });
    }

    /**
     * Configures Raw ratings data to display in a text view.
     *
     * @param ratings
     * @param metaScore
     */
    public void updateRatingsData(Rating[] ratings, String metaScore, String title) {
        if (ratings != null) {
            String ratingsResult = title + getNewLine();
            if(ratings.length == 1) {
                ratingsResult += ratings[0].getSource() + " Score: " + ratings[0].getValue() +  getNewLine();
            }
            if(ratings.length > 1) {
                for (int i = 0; i < ratings.length; i++) {

                    ratingsResult += ratings[i].getSource() + ": " + ratings[i].getValue() + getNewLine();
                }
                ratingsResult = ratingsResult + "Metascore: " + metaScore + getNewLine();
            }

            updateMovieRatings(ratingsResult);
        }
    }

    public void updateBackDropImageViewWithClear() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = getResources().getDrawable(R.drawable.placeholder220x220);
                activityMovieDetailBinding.backDropImageView.setImageDrawable(drawable);
                activityMovieDetailBinding.backDropImageView.setTag("defaultClear");
            }
        });
    }

    public void updateMovieDataTextView(OpenMovieRequestModel openMovieRequestModel) {
        movieDetailActivityViewModel.setMediaData(MovieTextElementService.ingestMovieDataForDisplay(openMovieRequestModel));
        updateMoviePlotTextView(MovieTextElementService.ingestMovieDataForDisplay(openMovieRequestModel));
    }

    /**
     * Updates MoviePlotTextView with movie information using Handler.
     *
     * @param updateText
     */
    public void updateMoviePlotTextView(String updateText) {
        if(updateText != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activityMovieDetailBinding.moviePlotTextView.setText(updateText);
                }
            });
        }
    }

    /**
     * Callback if successful will update and store response data
     */
    public Callback<OpenMovieRequestModel> omrmCallBackShort = new Callback<OpenMovieRequestModel>() {
        @Override
        public void onResponse(Call<OpenMovieRequestModel> call, Response<OpenMovieRequestModel> response) {
            updateMoviePosterImageView(response.body().getPoster());
            updateRatingsData(response.body().getRatings(), response.body().getMetaScore(), response.body().getTitle());
            updateMovieDataTextView(response.body());
            movieDetailActivityViewModel.setMediaID(response.body().getImdbID());
            movieDetailActivityViewModel.setOpenMovieRequestModel(response.body());
            movieDetailActivityViewModel.setHasHitOMDBAPI(true);
            if (movieDetailActivityViewModel.mediaID != null) {
                NetworkServices.callStreamingAvailability(movieDetailActivityViewModel.mediaID, sarmCallBack);
            }
            //Add method to store info in seperate api
        }

        @Override
        public void onFailure(Call<OpenMovieRequestModel> call, Throwable t) {
            System.out.println("OMRM CALL BACK onFailure() " + t.getMessage());
        }
    };

    /**
     * Updates text in RatingsTextView with available Streaming Service.
     *
     * @param services
     */
    public void updateStreamingServices(ArrayList<Service> services) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                movieDetailActivityViewModel.setStreamingServicesData(services);
                updateChipGroupWithStreamingServices(services);
            }
        });
    }

    /**
     * Streaming Availability Request Model starts call to api and
     * updates Back Drop Image and Checks for available Streaming
     * Services.
     */
    public Callback<StreamingAvailabilityRequestModel> sarmCallBack = new Callback<StreamingAvailabilityRequestModel>() {
        @Override
        public void onResponse(Call<StreamingAvailabilityRequestModel> call, Response<StreamingAvailabilityRequestModel> response) {
            if (response.isSuccessful()) {
                updateBackDropImageView(response.body().backdropURLs.getHighestResolution());
                updateStreamingServices(getAvailableServices(response.body()));
                movieDetailActivityViewModel.setStreamingAvailabilityRequestModel(response.body());
                movieDetailActivityViewModel.setMediaBackDropURL(response.body().backdropURLs.getHighestResolution());
                movieDetailActivityViewModel.setHasAlreadyHitAPIForStreamingAvailability(true);
            }
        }

        @Override
        public void onFailure(Call<StreamingAvailabilityRequestModel> call, Throwable t) {
            System.out.println("SARM CALL BACK onFailure() " + t.getMessage());
        }
    };

    public void updateChipGroupWithStreamingServices(ArrayList<Service> services) {
        ChipGroup chipGroup = activityMovieDetailBinding.servicesChipGroup;
        chipData = new ArrayList<>();
        for (int i = 0; i < services.size(); i++) {
            ChipStreamingService chipStreamingService = new ChipStreamingService(this, services.get(i).link);
            chipStreamingService.chipURL = services.get(i).link;
            chipStreamingService.setTag(services.get(i).serviceName + "," + services.get(i).link);
            chipStreamingService.setText(services.get(i).serviceName);
            if(services.get(i).isAvailable) {
                chipStreamingService.serviceAvailability = true;
                chipStreamingService.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orangePrimary)));
            }
            chipStreamingService.setCloseIconVisible(true);
            chipStreamingService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(chipStreamingService.serviceAvailability) {
                        Intent chipIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(chipStreamingService.chipURL));
                        startActivity(chipIntent);
                    }
                }
            });
            chipData.add(chipStreamingService);
            chipGroup.addView(chipStreamingService);
        }
    }

    public void startSearchResultsRecyclerView(String searchTerm) {
        Intent intent = new Intent(this, MediaSearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("query", searchTerm);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void configureSearchViewProperties() {
        activityMovieDetailBinding.mediaSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMovieDetailBinding.mediaSearchView.setIconified(false);
            }
        });

        activityMovieDetailBinding.mediaSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                startSearchResultsRecyclerView(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Checks through all Streaming Services Streaming Availability
     * api provides access to
     *
     * @param streamingAvailabilityRequestModel
     * @return ArrayList
     */
    public ArrayList<Service> getAvailableServices(StreamingAvailabilityRequestModel streamingAvailabilityRequestModel) {
        ArrayList<Service> services = new ArrayList<>();
        if (streamingAvailabilityRequestModel != null) {
            if (streamingAvailabilityRequestModel.streamingInfo.peacock != null) {
                Service peacockServ = streamingAvailabilityRequestModel.streamingInfo.peacock.us;
                peacockServ.isAvailable = true;
                peacockServ.setServiceName("peacock");
                services.add(peacockServ);
            } else {
                Service peacockServ = new Service();
                peacockServ.isAvailable = false;
                peacockServ.setServiceName("peacock");
                services.add(peacockServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.netflix != null) {
                Service netflixServ = streamingAvailabilityRequestModel.streamingInfo.netflix.us;
                netflixServ.isAvailable = true;
                netflixServ.setServiceName("netflix");
                services.add(netflixServ);
            } else {
                Service netflixServ = new Service();
                netflixServ.isAvailable = false;
                netflixServ.setServiceName("netflix");
                services.add(netflixServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.hulu != null) {
                Service huluServ = streamingAvailabilityRequestModel.streamingInfo.hulu.us;
                huluServ.isAvailable = true;
                huluServ.setServiceName("hulu");
                services.add(huluServ);
            } else {
                Service huluServ = new Service();
                huluServ.isAvailable = false;
                huluServ.setServiceName("hulu");
                services.add(huluServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.prime != null) {
                Service primeServ = streamingAvailabilityRequestModel.streamingInfo.prime.us;
                primeServ.isAvailable = true;
                primeServ.setServiceName("prime");
                services.add(primeServ);
            } else {
                Service primeServ = new Service();
                primeServ.isAvailable = false;
                primeServ.setServiceName("prime");
                services.add(primeServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.disney != null) {
                Service disneyServ = streamingAvailabilityRequestModel.streamingInfo.disney.us;
                disneyServ.isAvailable = true;
                disneyServ.setServiceName("disney");
                services.add(disneyServ);
            } else {
                Service disneyServ = new Service();
                disneyServ.isAvailable = false;
                disneyServ.setServiceName("disney");
                services.add(disneyServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.hbo != null) {
                Service hboServ = streamingAvailabilityRequestModel.streamingInfo.hbo.us;
                hboServ.isAvailable = true;
                hboServ.setServiceName("hbo");
                services.add(hboServ);
            } else {
                Service hboServ = new Service();
                hboServ.isAvailable = false;
                hboServ.setServiceName("hbo");
                services.add(hboServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.paramount != null) {
                Service paramountServ = streamingAvailabilityRequestModel.streamingInfo.paramount.us;
                paramountServ.isAvailable = true;
                paramountServ.setServiceName("paramount");
                services.add(paramountServ);
            } else {
                Service paramountServ = new Service();
                paramountServ.isAvailable = false;
                paramountServ.setServiceName("paramount");
                services.add(paramountServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.starz != null) {
                Service starzServ = streamingAvailabilityRequestModel.streamingInfo.starz.us;
                starzServ.isAvailable = true;
                starzServ.setServiceName("starz");
                services.add(starzServ);
            } else {
                Service starzServ = new Service();
                starzServ.isAvailable = false;
                starzServ.setServiceName("starz");
                services.add(starzServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.showtime != null) {
                Service showtimeServ = streamingAvailabilityRequestModel.streamingInfo.showtime.us;
                showtimeServ.isAvailable = true;
                showtimeServ.setServiceName("showtime");
                services.add(showtimeServ);
            } else {
                Service showtimeServ = new Service();
                showtimeServ.isAvailable = false;
                showtimeServ.setServiceName("showtime");
                services.add(showtimeServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.apple != null) {
                Service appleServ = streamingAvailabilityRequestModel.streamingInfo.apple.us;
                appleServ.isAvailable = true;
                appleServ.setServiceName("apple");
                services.add(appleServ);
            } else {
                Service appleServ = new Service();
                appleServ.isAvailable = false;
                appleServ.setServiceName("apple");
                services.add(appleServ);
            }
            if (streamingAvailabilityRequestModel.streamingInfo.mubi != null) {
                Service mubiServ = streamingAvailabilityRequestModel.streamingInfo.mubi.us;
                mubiServ.isAvailable = true;
                mubiServ.setServiceName("mubi");
                services.add(mubiServ);
            } else {
                Service mubiServ = new Service();
                mubiServ.isAvailable = false;
                mubiServ.setServiceName("mubi");
                services.add(mubiServ);
            }
            return services;
        }
        return services;
    }

    public String getNewLine() {
        return System.lineSeparator();
    }
}