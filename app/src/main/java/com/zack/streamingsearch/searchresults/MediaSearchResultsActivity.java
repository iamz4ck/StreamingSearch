package com.zack.streamingsearch.searchresults;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.zack.streamingsearch.R;
import com.zack.streamingsearch.openmoviedb.OpenMovieDatabaseClient;
import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestMovieSearchModel;
import com.zack.streamingsearch.services.NetworkServices;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaSearchResultsActivity extends AppCompatActivity {

    public SearchResultsRecyclerAdapter recyclerViewAdapter;
    public SearchView mediaSearchView;
    public RecyclerView mediaSearchRecyclerView;
    public String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_results);
        this.query = getIntent().getExtras().getString("query");
        callAPIMediaSearch(query);
        configureMediaSearchView();
    }

    public void callAPIMediaSearch(String searchedTerm) {
        NetworkServices.callOpenMovieDatabaseSearch(searchedTerm, omrmMovieSearchCallBack);
    }

    public void configureRecyclerView(List movieSearchResults) {
        mediaSearchRecyclerView = findViewById(R.id.mediaSearchRecyclerView);
        recyclerViewAdapter = new SearchResultsRecyclerAdapter(movieSearchResults,
                new MediaClickListener() {
                    @Override
                    public void onClick(MediaSearch mediaSearch) {
                        System.out.println("[MediaSearchResultsActivity configureRecyclerView()] Title:" + mediaSearch.getTitle());
                    }
                });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mediaSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mediaSearchRecyclerView.setAdapter(recyclerViewAdapter);
        mediaSearchRecyclerView.setLayoutManager(layoutManager);
    }

    public Callback<OpenMovieRequestMovieSearchModel> omrmMovieSearchCallBack = new Callback<OpenMovieRequestMovieSearchModel>() {
        @Override
        public void onResponse(Call<OpenMovieRequestMovieSearchModel> call, Response<OpenMovieRequestMovieSearchModel> response) {
            configureRecyclerView(Arrays.asList(response.body().getMediaSearches()));
        }

        @Override
        public void onFailure(Call<OpenMovieRequestMovieSearchModel> call, Throwable t) {
            System.out.println("[MovieSearchResultsActivity] callback omrmMovieSearchCallBack ERROR " + t.getMessage());
        }
    };

    public void configureMediaSearchView() {
        mediaSearchView = findViewById(R.id.mediaSearchView_InRecView);
        mediaSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaSearchView.setIconified(false);
            }
        });
        mediaSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void startSearchResultsRecyclerView(String searchTerm) {
        Intent intent = new Intent(this, MediaSearchResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("query", searchTerm);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}