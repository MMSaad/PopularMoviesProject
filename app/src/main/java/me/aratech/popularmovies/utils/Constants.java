package me.aratech.popularmovies.utils;

/**
 * Created by mustafa on 1/7/19.
 * Release the GEEK
 */
public class Constants {
    public static final String PAGE = "page";
    public static final String TOTAL_RESULTS = "total_results";
    public static final String TOTAL_PAGES = "total_pages";
    public static final String RESULTS = "results";
    public static final String ID = "id";
    public static final String VOTES_COUNT = "vote_count";
    public static final String HAS_VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";

    public static final String TITLE = "title";
    public static final String POPULARITY = "popularity";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String BACKDROP = "backdrop_path";
    public static final int FILTER_MOST_POPULAR = 0;
    public static final int FILTER_HIGHEST_RATED = 1;


    public final static String SELECTED_FILTER_FLAG = "selectedFilter";
    public static final String TAG_FILTERS = "Filters";


    public enum SortType{
        Popular,
        TopRated
    }

    public enum ViewLayouts{
        Loading,
        NoData,
        Data
    }
}
