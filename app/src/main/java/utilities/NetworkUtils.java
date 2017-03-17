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

/**
 * these utilities will be used to communicate with THE MOVIE DB servers
 */
public class NetworkUtils {

    private static final String TAG_LOG = NetworkUtils.class.getName();

    private static final String THE_MOVIE_DB_BASIC_URI = "https://api.themoviedb.org/3/movie/popular?api_key=7cd08965da83865e7aca5470202011e9";

    final static String PARAM_SORT = "sort";

    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(THE_MOVIE_DB_BASIC_URI).buildUpon()
                .appendQueryParameter(PARAM_SORT, sortBy)
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
