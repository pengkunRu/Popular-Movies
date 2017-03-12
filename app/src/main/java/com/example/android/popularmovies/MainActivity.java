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
        protected void onPostExecute(String s) {
            if(s!=null&&!s.equals("")){
                mDisplayQueryResult.setText(s);
            }
        }
    }
}
