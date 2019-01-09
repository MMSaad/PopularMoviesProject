package me.aratech.popularmovies.interfaces;

import me.aratech.popularmovies.data.Movie;

/***
 * Interface For Movies Adapter item click callback
 */
public interface IMovieListIemClickedListener {
    void movieItemClicked(Movie movie);
}
