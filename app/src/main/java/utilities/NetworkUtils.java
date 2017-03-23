package utilities;

/**
 * Created by kun on 2017/3/12.
 */

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.popularmovies.BuildConfig.MY_API_KEY;

/**
 * these utilities will be used to communicate with THE MOVIE DB servers
 */
public class NetworkUtils {

    private static final String TAG_LOG = NetworkUtils.class.getName();

    private static final String THEMOVIEDB_BASIC_URI = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASIC_URI = "https://image.tmdb.org/t/p";

    final static String PARAM_FILE_SIZE = "w500";
    final static String PARAM_API_KEY = "api_key";
    final static String PATH_MOVIES = "movie";
    final static String PATH_POPULAR = "popular";
    final static String PATH_NOW_PLAYING = "now_playing";
    final static String PATH_TOP_RATED = "top_rated";
    final static String PATH_UPCOMING = "upcoming";

    final static String QUERY = "?";

    /**
     * Build a popular url used to fetch the popular movies data
     */
    public static URL buildPopularUrl() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASIC_URI).buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(PATH_POPULAR)
                .query(QUERY)
                .appendQueryParameter(PARAM_API_KEY,MY_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Build a now playing url used to fetch the now playing movies data
     */
    public static URL buildNowPlayingUrl() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASIC_URI).buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(PATH_NOW_PLAYING)
                .query(QUERY)
                .appendQueryParameter(PARAM_API_KEY,MY_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Build a top rated url used to fetch the top rated movies data
     */
    public static URL buildTopRatedUrl() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASIC_URI).buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(PATH_TOP_RATED)
                .query(QUERY)
                .appendQueryParameter(PARAM_API_KEY,MY_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Build a upcoming url used to fetch the upcoming movies data
     */
    public static URL buildUpComingUrl() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASIC_URI).buildUpon()
                .appendPath(PATH_MOVIES)
                .appendPath(PATH_UPCOMING)
                .query(QUERY)
                .appendQueryParameter(PARAM_API_KEY,MY_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Build the image url In order to generate a fully working image URL,
     * you'll need 3 pieces of data. Those pieces are a base_url,
     * a file_size and a file_path.
     *
     * @param posterPath contains the last piece of data
     */
    public static String imageUrl(String posterPath){
        String builtImageUrl = IMAGE_BASIC_URI + "/" +
                          PARAM_FILE_SIZE +
                          posterPath;
        return builtImageUrl;
    }

    /**
     * This method returns the entire results from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /*milliseconds*/);
        urlConnection.setConnectTimeout(15000 /*milliseconds*/);
        urlConnection.connect();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
