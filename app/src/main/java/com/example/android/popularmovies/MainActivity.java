package com.example.android.popularmovies;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import utilities.NetworkUtils;
import utilities.OpenMovieJsonUtils;

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
    // Create a variable store a reference to the spinner items adapter
    private ArrayAdapter mSpinnerAdapter;
    // Create a variable store a reference to the spinner
    private Spinner mSpinner;
    // Create a variable to avoid onItemSelected calls during initialization
    private Boolean mIfCallOnItemSelected = false;
    // Create a variable to show the category information
    private TextView mDisplayCategoryInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessage = (TextView) findViewById(R.id.tv_display_error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mDisplayCategoryInformation = (TextView)findViewById(R.id.tv_display_category);

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

        mSpinner = (Spinner)findViewById(R.id.spinner_sortby);

        /**
         *  Bind the string array of items to the spinner item layout
         */
        mSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.sorts_array,R.layout.spinner_list_item);
        mSpinner.setAdapter(mSpinnerAdapter);

        /**
         * If mIfCallOnItemSelected is false avoid onItemSelected
         * calls during initialization
         */
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the Item that was selected
                if(mIfCallOnItemSelected) {
                    String selectedItem = adapterView.getSelectedItem().toString();
                    if (selectedItem.equals("Popularity")) {
                        mAdapter.getSortedMovieInformationByPopularity();
                    }
                    if (selectedItem.equals("Vote Average")) {
                        mAdapter.getSortedMovieInformationByVote();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fetchPopularMovies();
        setTitle("The Movie DB");
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
        switch (menuItemThatWasSelected){
            case R.id.action_popular:
                fetchPopularMovies();
                return true;
            case R.id.action_now_playing:
                fetchNowPlayingMovies();
                return true;
            case R.id.action_top_rated:
                fetchTopRatedMovies();
                return true;
            case R.id.action_upcoming:
                fetchUpcomingMovies();
                return true;
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

    /**
     * Fetch the popular movies data
     */
    private void fetchPopularMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showJsonDataView();
        mDisplayCategoryInformation.setText("Popular");
        URL theMovieDbSearchUrl = NetworkUtils.buildPopularUrl();
        new TheMovieDbQueryTask().execute(theMovieDbSearchUrl);
    }

    /**
     * Fetch the now playing movies data
     */
    private void fetchNowPlayingMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showJsonDataView();
        mDisplayCategoryInformation.setText("Now Playing");
        URL theMovieDbSearchUrl = NetworkUtils.buildNowPlayingUrl();
        new TheMovieDbQueryTask().execute(theMovieDbSearchUrl);
    }

    /**
     * Fetch the top rated movies data
     */
    private void fetchTopRatedMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showJsonDataView();
        mDisplayCategoryInformation.setText("Top Rated");
        URL theMovieDbSearchUrl = NetworkUtils.buildTopRatedUrl();
        new TheMovieDbQueryTask().execute(theMovieDbSearchUrl);
    }

    /**
     * Fetch the upcoming movies data
     */
    private void fetchUpcomingMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showJsonDataView();
        mDisplayCategoryInformation.setText("Upcoming");
        URL theMovieDbSearchUrl = NetworkUtils.buildUpComingUrl();
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
                mIfCallOnItemSelected = true;
            }else {
                showErrorMessageView();
            }
        }
    }
}
