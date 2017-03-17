package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG_LOG = DetailActivity.class.getName();

    private TextView mDisplayDetailInfomation;
    private MovieInformation mCurrentMovie;
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

        mDisplayDetailInfomation = (TextView)findViewById(R.id.tv_display_detail_information);

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
            mDisplayDetailInfomation.setText(mCurrentMovie.getmTitle()+"\n\n"+
                            mCurrentMovie.getmReleaseDate()+"\n\n"+
                            mCurrentMovie.getmPopularity()+"\n\n"+
                            mCurrentMovie.getmPosterPath()+"\n\n"+
                            mCurrentMovie.getmVoteAverage()+"\n\n"+
                            mCurrentMovie.getmOverview());
        }
    }

}
