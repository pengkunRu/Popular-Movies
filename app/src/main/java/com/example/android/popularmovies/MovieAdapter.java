package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kun on 2017/3/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    // Specify how many views the adapter will holder
    private String[] parsedMovieData = null;

    /**
     * Create the constructor for MovieAdapter that accepts the number of items to display
     */
    public MovieAdapter(String[] movieDataSource){
        parsedMovieData = movieDataSource;
    }

    /**
     * The onCreateViewHolder function is responsible for creating the views either by
     * inflating the item views from xml or creating them in code.
     *
     * @return a view holder object associated with this new item
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.movie_list_item;
        boolean shouldAttachToParrentImmediately = false;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParrentImmediately);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.mListItemMovieView.setText(parsedMovieData[position]);
    }

    @Override
    public int getItemCount() {
        if(parsedMovieData == null){
            return 0;
        }
        return parsedMovieData.length;
    }


    /**
     * Create our ViewHolder class as an inner class of MovieAdapter
     */
    class MovieViewHolder extends RecyclerView.ViewHolder{

        // Since we only had one textView in our layout {@movie_list_item.xml},we'll create a
        // single textView member varibale
        TextView mListItemMovieView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            // One of the most things a view holder does is cache these references to the views
            // that will be modified in the adapter
            mListItemMovieView = (TextView)itemView.findViewById(R.id.tv_item_movie);
        }
    }
}
