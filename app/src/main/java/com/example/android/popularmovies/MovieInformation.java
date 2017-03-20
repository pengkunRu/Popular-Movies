package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by kun on 2017/3/17.
 */

public class MovieInformation implements Parcelable{

    private static final String TAG_LOG = MovieInformation.class.getName();

    // A member variable to store the poster path about the movie
    private final String mPosterPath;
    // A member variable to store the vote average about the movie
    private final Double mVoteAverage;
    // A member variable to store the release date about the movie
    private final String mReleaseDate;
    // A member variable to store the popularity about the movie
    private final Double mPopularity;
    // A member variable to store the overview path about the movie
    private final String mOverview;
    // A member variable to store the title about the movie
    private final String mTitle;

    public MovieInformation(String PosterPath,Double VoteAverage,String ReleaseDate,
                            Double Popularity,String Overview,String Title) {
        mPosterPath = PosterPath;
        mVoteAverage = VoteAverage;
        mReleaseDate = ReleaseDate;
        mPopularity = Popularity;
        mOverview = Overview;
        mTitle = Title;
    }

    public String getmPosterPath(){
        return mPosterPath;
    }

    public Double getmVoteAverage(){
        return mVoteAverage;
    }

    public String getmReleaseDate(){
        return mReleaseDate;
    }

    public Double getmPopularity(){
        return mPopularity;
    }

    public String getmOverview(){
        return mOverview;
    }

    public String getmTitle(){
        return mTitle;
    }


    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mPosterPath);
        out.writeDouble(mVoteAverage);
        out.writeString(mReleaseDate);
        out.writeDouble(mPopularity);
        out.writeString(mOverview);
        out.writeString(mTitle);
    }

    // this is used to regenerate your object.
    // All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<MovieInformation> CREATOR = new Parcelable.Creator<MovieInformation>() {
        public MovieInformation createFromParcel(Parcel in) {
            return new MovieInformation(in);
        }

        public MovieInformation[] newArray(int size) {
            return new MovieInformation[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private MovieInformation(Parcel in) {
        mPosterPath = in.readString();
        mVoteAverage = in.readDouble();
        mReleaseDate = in.readString();
        mPopularity = in.readDouble();
        mOverview = in.readString();
        mTitle = in.readString();
    }

    /**
     * The Comparator interface similar to the Comparable interface provides
     * a single comparison method named compare(). However, unlike the compareTo()
     * method of Comparable, the compare() method takes two different objects of
     * the same type for comparison.
     */

    /**
     * Sort MovieInformation objects by vote average by implementing
     * Comparator as anonymous inner class
     */
    public static Comparator<MovieInformation> voteAverageComparator = new Comparator<MovieInformation>() {
        @Override
        public int compare(MovieInformation mf1, MovieInformation mf2) {
            return (mf2.getmVoteAverage() < mf1.getmVoteAverage() ? -1:
                    (mf2.getmVoteAverage()==mf1.getmVoteAverage()?0:1));
        }
    };

    /**
     * Sort MovieInformation objects by popularity by implementing
     * Comparator as anonymous inner class
     */
    public static Comparator<MovieInformation> popularityComparator = new Comparator<MovieInformation>() {
        @Override
        public int compare(MovieInformation mf1, MovieInformation mf2) {
            return (mf2.getmPopularity()<mf1.getmPopularity() ? -1:
                    (mf2.getmPopularity()==mf1.getmPopularity()?0:1));
        }
    };
}
