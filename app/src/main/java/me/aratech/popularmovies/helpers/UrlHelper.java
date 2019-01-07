package me.aratech.popularmovies.helpers;

import android.content.Context;
import android.net.Uri;

import java.net.URL;

import me.aratech.popularmovies.R;
import me.aratech.popularmovies.utils.Constants;

/***
 * Helper to build Web API methods urls
 */
public class UrlHelper {

    private static final String HTTPS_SCHEMA = "https";
    private static final String BASE_URL_PATH = "api.themoviedb.org/3/movie/";
    private static final String POPULAR_PATH = "popular";
    public static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185/";
    private static final String TOP_RATED_PATH = "top_rated";
    private static final String API_KEY_PARAM = "api_key";
    private static final String PAGE_PARAM = "page";

    /***
     * Get Movies List Url based on filter type
     * @param context Context to access app resources
     * @param sortType Movies Sort type
     * @param page which page to fetch
     * @return Movies URL
     */
    public URL getMoviesUrl(Context context, Constants.SortType sortType, int page) {
        try {
            String apiKey = context.getString(R.string.api_key);
            Uri.Builder builder = new Uri.Builder()
                    .scheme(HTTPS_SCHEMA)
                    .path(BASE_URL_PATH)
                    .appendPath(sortType == Constants.SortType.Popular ? POPULAR_PATH : TOP_RATED_PATH)
                    .appendQueryParameter(API_KEY_PARAM, apiKey)
                    .appendQueryParameter(PAGE_PARAM, String.valueOf(page));

            Uri uri = builder.build();
            return new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
