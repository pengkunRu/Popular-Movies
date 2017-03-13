package com.example.android.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplayQueryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayQueryResult = (TextView)findViewById(R.id.tv_display_query_result);
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
            makeTheMovieDbSearchQuery();
        }
        if(menuItemThatWasSelected==R.id.action_setting){
            Context context = MainActivity.this;
            String message = "Setting clicked";
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

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
            if(jsonResponse!=null&&!jsonResponse.equals("")){
                // Parse the response given by the jsonResponse
                try {
                    // Create the json root object by constructor and passing in
                    // the jsonResponse String,this class will parse the whole JSON String
                    // we got back from the movie db api
                    JSONObject baseJsonResponse = new JSONObject(jsonResponse);
                    // Get the values associated with the "results" key
                    JSONArray resultsArray = baseJsonResponse.getJSONArray("results");
                    // Loop through each result in the result array
                    for (int i = 0; i < resultsArray.length(); i++){
                        JSONObject currentMovie = resultsArray.getJSONObject(i);

                        String posterPath = currentMovie.getString("poster_path");
                        String overView = currentMovie.getString("overview");
                        String releaseDate = currentMovie.getString("release_date");
                        String title = currentMovie.getString("title");
                        String popularity = currentMovie.getString("popularity");
                        String voteAverage = currentMovie.getString("vote_average");

                        mDisplayQueryResult.append(posterPath+" - "+overView+" - "+releaseDate+" - "+
                                            title+" - "+popularity+" - "+voteAverage+" - "+"\n\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
