package com.zack.streamingsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.zack.streamingsearch.tmdb.models.TMDBConfigInfo;
import com.zack.streamingsearch.tmdb.models.TMDBMediaModel;

import java.util.ArrayList;

public class TrendingMediaRecyclerAdapter extends RecyclerView.Adapter<TrendingMediaRecyclerAdapter.MyViewHolder>{

    public ArrayList<TMDBMediaModel> trendingMediaList;

    public TrendingMediaRecyclerAdapter(ArrayList<TMDBMediaModel> trendingMediaList) {
        this.trendingMediaList = trendingMediaList;
    }

    @NonNull
    @Override
    public TrendingMediaRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trendingMediaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_media, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(trendingMediaView);
        trendingMediaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = trendingMediaList.get(myViewHolder.getAdapterPosition()).original_title;
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("query", title);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingMediaRecyclerAdapter.MyViewHolder holder, int position) {
        holder.trendingDataTextView.setText(trendingMediaList.get(position).title);
        TextView textView = holder.trendingDataTextView;
        if(trendingMediaList != null) {
            TMDBMediaModel tmdbMediaModel = trendingMediaList.get(position);
            if(tmdbMediaModel != null) {
                textView.setText(tmdbMediaModel.title);
                Picasso.get().load(TMDBConfigInfo.imageBaseURLW500 + tmdbMediaModel.poster_path).into(holder.trendingMediaPosterImageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return trendingMediaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trendingDataTextView;
        ImageView trendingMediaPosterImageView;
        public MyViewHolder(final View view) {
            super(view);
            trendingDataTextView = view.findViewById(R.id.trendingMediaTextView);
            trendingMediaPosterImageView = view.findViewById(R.id.trendingMediaPosterImageView);
        }
    }

}
