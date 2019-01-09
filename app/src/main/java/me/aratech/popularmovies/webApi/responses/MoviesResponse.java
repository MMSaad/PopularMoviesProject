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
    @SerializedName(Constants.RESULTS)
    private ArrayList<Movie> Movies;

    public ArrayList<Movie> getMovies() {
        return Movies;
    }
}
