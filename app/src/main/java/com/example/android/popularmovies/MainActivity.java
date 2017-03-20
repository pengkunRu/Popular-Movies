package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import utilities.NetworkUtils;
import utilities.OpenMovieJsonUtils;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private static final String TAG_LOG = MainActivity.class.getName();

    // Create a variable store a reference to our MovieAdapter
    private MovieAdapter mAdapter;
    // Create a variable store a reference to the RecyclerView
    private RecyclerView mRecyclerView;
    // Create a variable store a reference to the error message text view
    private TextView mErrorMessage;
    // Create a variable store a reference to the prograss bar loading indicator
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessage = (TextView) findViewById(R.id.tv_display_error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        /**
         * Creates a vertical GridLayoutManager
         *
         * Context:will be used to access resources.
         *     int:The number of columns in the grid
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        /**
         *  Assign setHasFixedSize to true allows RecyclerView to do some optimizations
         *  on our UI,namely,allowing it avoid invalidating the whole layout when the adapter
         *  contents change.
         */
        mRecyclerView.setHasFixedSize(true);
        /**
         * Create create a MovieAdapter to display movie information.
         */
        mAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Inflater our menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popular_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_refresh) {
            mAdapter.setMovieData(null);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            showJsonDataView();
            makeTheMovieDbSearchQuery();
        }
        if (menuItemThatWasSelected == R.id.action_setting) {
            Context context = MainActivity.this;
            String message = "Setting clicked";
            makeText(context, message, Toast.LENGTH_SHORT).show();
        }
        if(menuItemThatWasSelected == R.id.action_sort_by_vote_average){
            mAdapter.getSortedMovieInformationByVote();
        }
        if(menuItemThatWasSelected == R.id.action_sort_by_popularity){
            mAdapter.getSortedMovieInformationByPopularity();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hide the error message and show the json data
     */
    private void showJsonDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the JSON data and show the error message
     */
    private void showErrorMessageView() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    //Set up the background thread
    private void makeTheMovieDbSearchQuery() {
        URL theMovieDbSearchUrl = NetworkUtils.buildUrl();
        new TheMovieDbQueryTask().execute(theMovieDbSearchUrl);
    }

    @Override
    public void onListItemClicked(int ClickedItemIndex) {
        //Get data about the movie
        MovieInformation currentMovieInfo = mAdapter.getCurrentMovieData(ClickedItemIndex);
        // Set up the link from MainActivity to newly created Activity
        Intent startTheDetailActivity = new Intent(MainActivity.this,DetailActivity.class);
        startTheDetailActivity.putExtra(Intent.EXTRA_TEXT,currentMovieInfo);
        // Move to the DetailActivity
        startActivity(startTheDetailActivity);
    }

    //Fetch the popular movie data from internet on the background thread
    public class TheMovieDbQueryTask extends AsyncTask<URL, Void, ArrayList<MovieInformation>> {

        @Override
        protected ArrayList<MovieInformation> doInBackground(URL... urls) {
            URL searchUrl = urls[0];

            String themoviedbSearchResults = null;
            ArrayList<MovieInformation> simpleMovieStrings = null;

            try {
                themoviedbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


            try {
                simpleMovieStrings = OpenMovieJsonUtils.getSimpleMovieStringsFromJson(themoviedbSearchResults);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return simpleMovieStrings;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieInformation> simpleMovieStrings) {
            super.onPostExecute(simpleMovieStrings);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(simpleMovieStrings!=null&&simpleMovieStrings.size()!=0){
                mAdapter.setMovieData(simpleMovieStrings);
            }else {
                showErrorMessageView();
            }
        }
    }
}
