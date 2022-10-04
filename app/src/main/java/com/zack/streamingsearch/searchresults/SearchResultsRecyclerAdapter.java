package com.zack.streamingsearch.searchresults;

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
import com.zack.streamingsearch.MovieDetailActivity;
import com.zack.streamingsearch.R;

import java.util.List;


public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter<SearchResultsRecyclerAdapter.MyViewHolder> {

    public List<MediaSearch> searchResults;
    public MediaClickListener mediaClickListener;

    public SearchResultsRecyclerAdapter(List<MediaSearch> searchResults, MediaClickListener mediaClickListener) {
        this.searchResults = searchResults;
        this.mediaClickListener = mediaClickListener;
    }

    @NonNull
    @Override
    public SearchResultsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchResults.get(myViewHolder.getAdapterPosition()) != null) {
                    //System.out.println("CLICK: " + searchResults.get(myViewHolder.getAdapterPosition()).getTitle() + " ," +
                    //        searchResults.get(myViewHolder.getAdapterPosition()).getImdbID());
                    Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("query", searchResults.get(myViewHolder.getAdapterPosition()).getTitle());
                    bundle.putString("imdbID", searchResults.get(myViewHolder.getAdapterPosition()).getImdbID());
                    view.getContext().startActivity(intent);
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsRecyclerAdapter.MyViewHolder holder, int position) {
            TextView textView = holder.mediaDataTextView;
            if(searchResults != null) {
                MediaSearch mediaSearch = searchResults.get(position);
                if(mediaSearch != null) {
                    textView.setTag(mediaSearch.getImdbID());
                    String[] rowData = new String[3];
                    rowData[0] = mediaSearch.getTitle();
                    rowData[1] = mediaSearch.getYear();
                    rowData[2] = mediaSearch.getType();
                    Picasso.get().load(mediaSearch.getPoster()).into(holder.mediaPosterImageView);
                    textView.setText(configureRowData(rowData));
                }
            }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mediaDataTextView;
        ImageView mediaPosterImageView;
        public MyViewHolder(final View view) {
            super(view);
            mediaDataTextView = view.findViewById(R.id.recyclerMediaTextView);
            mediaPosterImageView = view.findViewById(R.id.item_search_poster_imageView);
        }
    }

    public String configureRowData(String[] rowData) {
        String configuredData = "";
        configuredData += rowData[0] + getNewLine();
        configuredData += rowData[1] + getNewLine();
        configuredData += rowData[2] + getNewLine();
        return configuredData;
    }

    public String getNewLine() {
        return System.lineSeparator();
    }

}
