package com.example.android.popularmovies;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    // Create a variable store a reference to our MovieAdapter
    private MovieAdapter mAdapter;
    // Create a variable store a reference to the RecyclerView
    private RecyclerView mRecyclerView;
    // Create a variable store a reference to the error message text view
    private TextView mErrorMessage;
    // Create a variable store a reference to the prograss bar loading indicator
    private ProgressBar mLoadingIndicator;

    private String[] parsedMovieData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessage = (TextView)findViewById(R.id.tv_display_error_message);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_movies);

        /**
         * Creates a vertical GridLayoutManager
         *
         * Context:will be used to access resources.
         *     int:The number of columns in the grid
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        /**
         *  Assign setHasFixedSize to true allows RecyclerView to do some optimizations
         *  on our UI,namely,allowing it avoid invalidating the whole layout when the adapter
         *  contents change.
         */
//        mRecyclerView.setHasFixedSize(true);
    }

    // Inflater our menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popular_movie,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if(menuItemThatWasSelected==R.id.action_refresh){
            mRecyclerView.setAdapter(null);
            mLoadingIndicator.setVisibility(View.VISIBLE);
            showJsonDataView();
            makeTheMovieDbSearchQuery();
        }
        if(menuItemThatWasSelected==R.id.action_setting){
            Context context = MainActivity.this;
            String message = "Setting clicked";
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hide the error message and show the json data
     */
    private void showJsonDataView(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the JSON data and show the error message
     */
    private void showErrorMessageView(){
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    //Set up the background thread
    private void makeTheMovieDbSearchQuery(){
        URL theMovieDbSearchUrl = NetworkUtils.buildUrl("popularity");
        new TheMovieDbQueryTask().execute(theMovieDbSearchUrl);
    }

    //Fetch the popular movie data from internet on the background thread
    public class TheMovieDbQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String themoviedbSearchResults = null;

            try {
                themoviedbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return themoviedbSearchResults;
        }


        @Override
        protected void onPostExecute(String jsonResponse) {
            //set the progress loading indicator invisibility before show the json data or error message
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(jsonResponse!=null&&!jsonResponse.equals("")){
                // Parse the response given by the jsonResponse
                try {
                    // Create the json root object by constructor and passing in
                    // the jsonResponse String,this class will parse the whole JSON String
                    // we got back from the movie db api
                    JSONObject baseJsonResponse = new JSONObject(jsonResponse);
                    // Get the values associated with the "results" key
                    JSONArray resultsArray = baseJsonResponse.getJSONArray("results");
                    parsedMovieData = new String[resultsArray.length()];

                    // Loop through each result in the result array
                    for (int i = 0; i < resultsArray.length(); i++){
                        JSONObject currentMovie = resultsArray.getJSONObject(i);

                        String posterPath = currentMovie.getString("poster_path");
                        String overView = currentMovie.getString("overview");
                        String releaseDate = currentMovie.getString("release_date");
                        String title = currentMovie.getString("title");
                        String popularity = currentMovie.getString("popularity");
                        String voteAverage = currentMovie.getString("vote_average");

                        parsedMovieData[i] = posterPath + " - " + overView + " - " + releaseDate + " - " +
                                            title + " - " + popularity + " - " + voteAverage + " - ";
                    }
                    mAdapter = new MovieAdapter(parsedMovieData);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                showErrorMessageView();
            }
        }
    }
}
