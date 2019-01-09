package me.aratech.popularmovies.webApi.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import me.aratech.popularmovies.data.Movie;
import me.aratech.popularmovies.utils.Constants;

/***
 * Movies Web Methods Response
 */
public class MoviesResponse implements Serializable {

    @Expose
    @SerializedName(Constants.PAGE)
    private int Page;

    @Expose
    @SerializedName(Constants.TOTAL_RESULTS)
    private int TotalResults;

    @Expose
    @SerializedName(Constants.TOTAL_PAGES)
    private int TotalPages;

    @Expose
    @SerializedName(Constants.RESULTS)
    private ArrayList<Movie> Movies;

    public int getPage() {
        return Page;
    }

    public int getTotalResults() {
        return TotalResults;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public ArrayList<Movie> getMovies() {
        return Movies;
    }
}
