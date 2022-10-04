package com.zack.streamingsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zack.streamingsearch.databinding.ActivityMainBinding;
import com.zack.streamingsearch.searchresults.MediaSearchResultsActivity;
import com.zack.streamingsearch.services.NetworkServices;
import com.zack.streamingsearch.tmdb.TMDBClient;
import com.zack.streamingsearch.tmdb.models.TMDBConfigInfo;
import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;
import com.zack.streamingsearch.tmdb.models.TMDBPage;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    public RecyclerView trendingMediaRecyclerView;
    public TrendingMediaRecyclerAdapter trendingMediaRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        if (!viewModel.hasHitAPI) {
            callTMDBWeeklyTrendingMovieSearch(TMDBTrendingSearchCallback);
            configureSearchViewProperties();
            addServicesCheckedListToInfoTextView();
            viewModel.hasHitAPI = true;
        } else {
            addServicesCheckedListToInfoTextView();
            configureSearchViewProperties();
            configureRecyclerView(viewModel.mediaModels);
        }
    }

    public void configureRecyclerView(ArrayList<TMDBMediaModel> trendingMediaList) {
        trendingMediaRecyclerView = (RecyclerView) findViewById(R.id.trendingMediaRecyclerView);
        trendingMediaRecyclerViewAdapter = new TrendingMediaRecyclerAdapter(trendingMediaList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        trendingMediaRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trendingMediaRecyclerView.setLayoutManager(layoutManager);
        trendingMediaRecyclerView.setAdapter(trendingMediaRecyclerViewAdapter);
    }

    public void configureSearchViewProperties() {
        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.searchView.setIconified(false);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                startSearchResultsRecyclerView(s.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void addServicesCheckedListToInfoTextView() {
        String listToAdd = "Search through 11 streaming services" + System.lineSeparator() +
                "to see if your show or movie is available." + System.lineSeparator();
        listToAdd += "Peacock   Netflix" + System.lineSeparator();
        listToAdd += System.lineSeparator() + "Hulu   Prime" + System.lineSeparator();
        listToAdd += System.lineSeparator() + "Disney   HBO" + System.lineSeparator();
        listToAdd += System.lineSeparator() + "Paramount   Starz" + System.lineSeparator();
        listToAdd += System.lineSeparator() + "Showtime   Apple" + System.lineSeparator();
        listToAdd += System.lineSeparator() + "Mubi" + System.lineSeparator();
        binding.mainInformationTextView.setText(System.lineSeparator() + listToAdd);
    }

    public void startSearchResultsRecyclerView(String searchTerm) {
        Intent intent = new Intent(this, MediaSearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("query", searchTerm);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public Callback<TMDBPage> TMDBTrendingSearchCallback = new Callback<TMDBPage>() {
        @Override
        public void onResponse(Call<TMDBPage> call, Response<TMDBPage> response) {
            ArrayList<TMDBMediaModel> mediaModelsTEST = new ArrayList<TMDBMediaModel>();
            if(response.body().results != null) {
                for(int i = 0; i < response.body().results.length; i++) {
                    if(response.body().results[i].title != null) {
                        mediaModelsTEST.add(response.body().results[i]);
                    }
                }
            }
            viewModel.setMediaModels(mediaModelsTEST);
            configureRecyclerView(viewModel.mediaModels);
            viewModel.hasHitAPI = true;
        }

        @Override
        public void onFailure(Call<TMDBPage> call, Throwable t) {

        }
    };

    public void callTMDBWeeklyTrendingMovieSearch(Callback<TMDBPage> callback) {
        NetworkServices.callTMDBTrendingWeekly(callback);
    }


}