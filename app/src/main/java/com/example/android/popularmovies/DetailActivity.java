package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG_LOG = DetailActivity.class.getName();

    // Create a variable member to store the current movie
    private MovieInformation mCurrentMovie;

    // Create a variable member to store the reference our title
    private TextView mDisplayMovieTitle;
    // Create a variable member to store the reference our Image
    private ImageView mDisplayMovieImage;
    //Create a variable member to store the reference our release date
    private TextView mDisplayMovieReleaseDate;
    //Create a variable member to store the reference our vote average
    private TextView mDisplayMovieVoteAverage;
    //Create a variable member to store the reference our popularity
    private TextView mDisplayMoviePopularity;
    //Create a variable member to store the reference our overview
    private TextView mDisplayMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this,"Add your Action!",Toast.LENGTH_SHORT).show();
            }
        });

        mDisplayMovieTitle = (TextView)findViewById(R.id.tv_title);
        mDisplayMovieImage = (ImageView)findViewById(R.id.iv_image);
        mDisplayMovieVoteAverage = (TextView)findViewById(R.id.tv_vote_average);
        mDisplayMovieReleaseDate = (TextView)findViewById(R.id.tv_release_data);
        mDisplayMoviePopularity = (TextView)findViewById(R.id.tv_popularity);
        mDisplayMovieOverview = (TextView)findViewById(R.id.tv_overview);

        /**
         * Get the intent that created this Activity, then we can retrieve data
         * That passed in it. pretty much like opening the envelope
         */
        Intent intentThatStartThisActivity = getIntent();

        /**
         * Activities always has an Intent which triggers to stop that Activity
         * but, It didn't have any data in it. so you shouldn't assume that every
         * intent has extra data and always check if it does.
         */
        if(intentThatStartThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            mCurrentMovie = intentThatStartThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);

            //Set movie title
            mDisplayMovieTitle.setText(mCurrentMovie.getmTitle());

            //Set movie poster
            Context context = mDisplayMovieImage.getContext();
            String imageUrl = mCurrentMovie.getmPosterPath();
            Picasso.with(context).load(imageUrl).into(mDisplayMovieImage);

            //Set movie vote average
            mDisplayMovieVoteAverage.setText(mCurrentMovie.getmVoteAverage());

            //Set movie release data
            mDisplayMovieReleaseDate.setText(mCurrentMovie.getmReleaseDate());

            //Set movie popularity
            mDisplayMoviePopularity.setText(mCurrentMovie.getmPopularity());

            //Set movie overview
            mDisplayMovieOverview.setText(mCurrentMovie.getmOverview());
        }
    }

}
