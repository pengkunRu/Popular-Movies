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

    private static final String THEMOVIEDB_BASIC_URI = "https://api.themoviedb.org/3/movie/popular?api_key=7cd08965da83865e7aca5470202011e9";
    private static final String IMAGE_BASIC_URI = "https://image.tmdb.org/t/p";

    final static String PARAM_SORT = "sort";
    final static String PARAM_FILE_SIZE = "w500";

    /**
     * Build a url that contains all information about the movie
     */
    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASIC_URI).buildUpon()
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
