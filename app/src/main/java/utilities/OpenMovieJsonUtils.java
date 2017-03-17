package utilities;

/**
 * Created by kun on 2017/3/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

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
    public static String[] getSimpleMovieStringsFromJson(String moviesJsonStr)
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
        String[] parsedMovieData = null;

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

        parsedMovieData = new String[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject currentMovie = resultsArray.getJSONObject(i);

            String posterPath = currentMovie.getString(TMD_POSTER_PATH);
            String imageUri = "https://image.tmdb.org/t/p/w500"+posterPath;
            String overView = currentMovie.getString(TMD_OVERVIEW);
            String releaseDate = currentMovie.getString(TMD_RELEASE_DATE);
            String title = currentMovie.getString(TMD_TITLE);
            String popularity = currentMovie.getString(TMD_POPULARITY);
            String voteAverage = currentMovie.getString(TMD_VOTE_AVERAGE);

            parsedMovieData[i] = imageUri;
        }

        return parsedMovieData;
    }
}
