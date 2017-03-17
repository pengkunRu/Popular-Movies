package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kun on 2017/3/16.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private static final String TAG_LOG = MovieAdapter.class.getName();

    // Specify how many views the adapter will holder
    private ArrayList<MovieInformation> parsedMovieData = null;
    /**
     * Create a member variable to store a reference to a list item click listener
     * This allows us to use MovieAdapter as component woth an external click handler,
     * (such as from our Activity)
     */
    private final ListItemClickListener mOnClickListener;

    /**
     * Create an interface that will define your listener
     */
    public interface ListItemClickListener{
        void onListItemClicked(int ClickedItemIndex);
    }

    /**
     * Create the constructor for MovieAdapter that accepts the number of items to display
     */
    public MovieAdapter(ListItemClickListener listener){
        mOnClickListener = listener;
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
        holder.bind(parsedMovieData.get(position).getmPosterPath());
    }

    @Override
    public int getItemCount() {
        if(parsedMovieData == null){
            return 0;
        }
        return parsedMovieData.size();
    }

    /**
     * This method is used to set the movie data source on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new movie data to be displayed.
     */
    public void setMovieData(ArrayList<MovieInformation> movieData) {
        parsedMovieData = movieData;
        notifyDataSetChanged();
    }


    /**
     * Create our ViewHolder class as an inner class of MovieAdapter
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Since we only had one textView in our layout {@movie_list_item.xml},we'll create a
        // single textView member variable
        ImageView mListItemMovieView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            // One of the most things a view holder does is cache these references to the views
            // that will be modified in the adapter
            mListItemMovieView = (ImageView)itemView.findViewById(R.id.iv_poster_of_movie);
            itemView.setOnClickListener(this);
        }

        public void bind(URL imageUri){
            Context context = mListItemMovieView.getContext();
            Picasso.with(context).load(imageUri.toString()).into(mListItemMovieView);
        }

        /**
         * Now the MovieAdapter has access to a listener,we need to pass it to the view holder
         * so that view can invoke it.
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClicked(clickedPosition);
        }
    }
}
