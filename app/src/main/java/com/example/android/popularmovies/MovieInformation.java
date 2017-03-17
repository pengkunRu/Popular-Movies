package com.example.android.popularmovies;

/**
 * Created by kun on 2017/3/17.
 */

public class MovieInformation {

    private static final String TAG_LOG = MovieInformation.class.getName();

    // A member variable to store the poster path about the movie
    private final String mPosterPath;
    // A member variable to store the vote average about the movie
    private final String mVoteAverage;
    // A member variable to store the release date about the movie
    private final String mReleaseDate;
    // A member variable to store the popularity about the movie
    private final String mPopularity;
    // A member variable to store the overview path about the movie
    private final String mOverview;

    public MovieInformation(String PosterPath,String VoteAverage,String ReleaseDate,
                            String Popularity,String Overview) {
        mPosterPath = PosterPath;
        mVoteAverage = VoteAverage;
        mReleaseDate = ReleaseDate;
        mPopularity = Popularity;
        mOverview = Overview;
    }

    public String getmPosterPath(){
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
}
