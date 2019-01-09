package me.aratech.popularmovies.utils;


public class Constants {

    public static final String RESULTS = "results";
    public static final String ID = "id";
    public static final String VOTE_AVERAGE = "vote_average";

    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";

    public final static String SELECTED_SORT_TYPE = "sortType";
    public static final String TAG_FILTERS = "Filters";
    public static final String MOVIE_ITEM = "movieItem";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String MOVIES_RESULT = "MoviesResult";


    /***
     * Movies Sort types
     */
    public enum SortType {
        Popular,
        TopRated
    }

    /***
     * Activity View's layout
     */
    public enum ViewLayouts {
        Loading,
        NoData,
        Data
    }
}
