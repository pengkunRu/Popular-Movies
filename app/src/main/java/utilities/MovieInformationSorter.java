package utilities;

import com.example.android.popularmovies.MovieInformation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kun on 2017/3/20.
 */

public class MovieInformationSorter {

    ArrayList<MovieInformation> movieListItems = new ArrayList<>();

    public MovieInformationSorter(ArrayList<MovieInformation> movieInformations){
        movieListItems = movieInformations;
    }

    public ArrayList<MovieInformation> getSortedMoviesByVoteAverage(){
        Collections.sort(movieListItems,MovieInformation.voteAverageComparator);
        return movieListItems;
    }

    public ArrayList<MovieInformation> getSortedMoviesByPopularity(){
        Collections.sort(movieListItems,MovieInformation.popularityComparator);
        return movieListItems;
    }
}
