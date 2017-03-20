package utilities;

/**
 * Created by kun on 2017/3/16.
 */

import com.example.android.popularmovies.MovieInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Utility functions to handle TheMovieDB JSON data
 */
public class OpenMovieJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the movie information.
     *
     * @param moviesJsonStr JSON response from server
     * @return Array of Strings describing movie data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<MovieInformation> getSimpleMovieStringsFromJson(String moviesJsonStr)
            throws JSONException {

        final String TMD_LIST = "results";

        final String TMD_POSTER_PATH = "poster_path";
        final String TMD_OVERVIEW = "overview";
        final String TMD_RELEASE_DATE = "release_date";
        final String TMD_TITLE = "title";
        final String TMD_POPULARITY = "popularity";
        final String TMD_VOTE_AVERAGE = "vote_average";

        final String TMD_MESSAGE_CODE = "cod";

        /* String array to hold each movie's information String */
        ArrayList<MovieInformation> parsedMovieData = new ArrayList<MovieInformation>();

        // Create the json root object by constructor and passing in
        // the jsonResponse String,this class will parse the whole JSON String
        // we got back from the movie db api
        JSONObject baseJsonResponse = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (baseJsonResponse.has(TMD_MESSAGE_CODE)) {
            int errorCode = baseJsonResponse.getInt(TMD_MESSAGE_CODE);
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        // Get the values associated with the "results" key
        JSONArray resultsArray = baseJsonResponse.getJSONArray(TMD_LIST);

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject currentMovie = resultsArray.getJSONObject(i);

            String imageUri = NetworkUtils.imageUrl(currentMovie.getString(TMD_POSTER_PATH));
            String overView = currentMovie.getString(TMD_OVERVIEW);
            String releaseDate = currentMovie.getString(TMD_RELEASE_DATE);
            String title = currentMovie.getString(TMD_TITLE);
            Double popularity = currentMovie.getDouble(TMD_POPULARITY);
            Double voteAverage = currentMovie.getDouble(TMD_VOTE_AVERAGE);

            parsedMovieData.add(new MovieInformation(imageUri,voteAverage,releaseDate,
                                popularity,overView,title));
        }

        return parsedMovieData;
    }
}
