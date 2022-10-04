package com.zack.streamingsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.zack.streamingsearch.databinding.ActivityMovieDetailBinding;
import com.zack.streamingsearch.searchresults.MediaSearchResultsActivity;

public class MovieDetailActivity extends AppCompatActivity {

    public MovieDetailActivityViewModel movieDetailActivityViewModel;
    public ActivityMovieDetailBinding activityMovieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        activityMovieDetailBinding.setLifecycleOwner(this);
        movieDetailActivityViewModel = new ViewModelProvider(this).get(MovieDetailActivityViewModel.class);
        String query = getIntent().getExtras().getString("query");
        configureSearchViewProperties();
        if(!movieDetailActivityViewModel.hasHitAPI) {
            System.out.println("QUERY in MovieDetailActivity: " + query);
            //Call omdb API
            //Call StreamingAva API
            //Configure ChipGroup
            //Log info in mySQL DB
            movieDetailActivityViewModel.hasHitAPI = true;
        } else {
            //Configure omdb Data if needed
            //Configure ChipGroup

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


}