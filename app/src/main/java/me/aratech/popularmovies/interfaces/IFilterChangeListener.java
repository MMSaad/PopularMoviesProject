package me.aratech.popularmovies.interfaces;

import me.aratech.popularmovies.utils.Constants;

/**
 * Created by mustafa on 1/7/19.
 * Release the GEEK
 */
public interface IFilterChangeListener {
    void filterChanged( Constants.SortType newFilter);
}
