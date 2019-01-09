package me.aratech.popularmovies.interfaces;

import me.aratech.popularmovies.utils.Constants;

/***
 * Interface For changing Sort Type Callback
 */
public interface ISortTypeChangeListener {
    void sortTypeChanged(Constants.SortType sortType);
}
