package com.example.android.popularmovies;

import java.net.URL;

/**
 * Created by kun on 2017/3/17.
 */

public class MovieInformation {

    private static final String TAG_LOG = MovieInformation.class.getName();

    // A member variable to store the poster path about the movie
    private final URL mPosterPath;
    // A member variable to store the vote average about the movie
    private final String mVoteAverage;
    // A member variable to store the release date about the movie
    private final String mReleaseDate;
    // A member variable to store the popularity about the movie
    private final String mPopularity;
    // A member variable to store the overview path about the movie
    private final String mOverview;
    // A member variable to store the title about the movie
    private final String mTitle;

    public MovieInformation(URL PosterPath,String VoteAverage,String ReleaseDate,
                            String Popularity,String Overview,String Title) {
        mPosterPath = PosterPath;
        mVoteAverage = VoteAverage;
        mReleaseDate = ReleaseDate;
        mPopularity = Popularity;
        mOverview = Overview;
        mTitle = Title;
    }

    public URL getmPosterPath(){
        return mPosterPath;
    }

    public String getmVoteAverage(){
        return mVoteAverage;
    }

    public String getmReleaseDate(){
        return mReleaseDate;
    }

    public String getmPopularity(){
        return mPopularity;
    }

    public String getmOverview(){
        return mOverview;
    }

    public String getmTitle(){
        return mTitle;
    }
}
