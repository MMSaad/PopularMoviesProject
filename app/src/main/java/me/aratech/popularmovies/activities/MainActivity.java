package me.aratech.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.adapters.MoviesAdapter;
import me.aratech.popularmovies.data.Movie;
import me.aratech.popularmovies.fragments.MoviesFilterSheet;
import me.aratech.popularmovies.helpers.ApiHelper;
import me.aratech.popularmovies.helpers.DialogsHelper;
import me.aratech.popularmovies.helpers.UrlHelper;
import me.aratech.popularmovies.interfaces.IMovieListIemClickedListener;
import me.aratech.popularmovies.interfaces.ISortTypeChangeListener;
import me.aratech.popularmovies.utils.Constants;
import me.aratech.popularmovies.webApi.responses.MoviesResponse;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ISortTypeChangeListener, IMovieListIemClickedListener {

    /***
     * Vars
     */
    private MoviesResponse mResponse;
    private Constants.SortType mSelectedSortType = Constants.SortType.Popular;


    /***
     * Views
     */
    @BindView(R.id.v_loading) View vLoading;
    @BindView(R.id.v_no_data) View vNoData;
    @BindView(R.id.v_data) View vData;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rvMovies.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_columns), LinearLayoutManager.VERTICAL, false));
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.MOVIES_RESULT)) {
                mResponse = (MoviesResponse) savedInstanceState.getSerializable(Constants.MOVIES_RESULT);
            }
            if (savedInstanceState.containsKey(Constants.SELECTED_SORT_TYPE)) {
                mSelectedSortType = Constants.SortType.values()[savedInstanceState.getInt(Constants.SELECTED_SORT_TYPE)];
            }
        }
        new GetMoviesAsync(this, mResponse, mSelectedSortType)
                .execute();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResponse != null) {
            outState.putSerializable(Constants.MOVIES_RESULT, mResponse);
        }
        outState.putInt(Constants.SELECTED_SORT_TYPE, mSelectedSortType.ordinal());
    }

    private void switchLayout(Constants.ViewLayouts layout) {
        vLoading.setVisibility(layout == Constants.ViewLayouts.Loading ? View.VISIBLE : View.GONE);
        vNoData.setVisibility(layout == Constants.ViewLayouts.NoData ? View.VISIBLE : View.GONE);
        vData.setVisibility(layout == Constants.ViewLayouts.Data ? View.VISIBLE : View.GONE);
    }

    private void bindUi() {
        rvMovies.setAdapter(new MoviesAdapter(mResponse, this, this));
        rvMovies.setHasFixedSize(true);
    }

    /***
     * UI Actions
     */

    @SuppressWarnings("unused")
    @OnClick(R.id.fab_filter)
    void filterFabClicked(View v) {
        MoviesFilterSheet sheet = MoviesFilterSheet.newInstance(mSelectedSortType);
        sheet.show(getSupportFragmentManager(), Constants.TAG_FILTERS);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_retry)
    void retryButtonClicked(View v) {
        new GetMoviesAsync(this, null, mSelectedSortType)
                .execute();
    }


    /***
     * ISortTypeChangeListener implementation
     */

    @Override
    public void sortTypeChanged(Constants.SortType sortType) {
        mSelectedSortType = sortType;
        new GetMoviesAsync(this, null, mSelectedSortType)
                .execute();
    }


    /***
     * IMovieListIemClickedListener implementation
     */

    @Override
    public void movieItemClicked(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ITEM, movie);
        startActivity(intent);
    }


    /***
     * Get Movies List Async task
     */
    static class GetMoviesAsync extends AsyncTask<Void, Void, MoviesResponse> {

        private final WeakReference<MainActivity> mWeakReference;
        private final MoviesResponse mMoviesResponse;
        private final Constants.SortType mSortType;

        GetMoviesAsync(MainActivity activity, MoviesResponse response, Constants.SortType sortType) {
            mWeakReference = new WeakReference<>(activity);
            mMoviesResponse = response;
            mSortType = sortType;
        }

        @Override protected void onPreExecute() {
            MainActivity activity = mWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.switchLayout(Constants.ViewLayouts.Loading);
        }

        @Override protected void onPostExecute(MoviesResponse moviesResponse) {
            MainActivity activity = mWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            if (moviesResponse != null && moviesResponse.getMovies().size() > 0) {
                activity.mResponse = moviesResponse;
                activity.bindUi();
                activity.switchLayout(Constants.ViewLayouts.Data);
            } else {
                activity.switchLayout(Constants.ViewLayouts.NoData);
                DialogsHelper.showToast(activity, R.string.error_fetching_data);
            }
        }

        @Override protected MoviesResponse doInBackground(Void... voids) {

            if (mMoviesResponse != null) {
                return mMoviesResponse;
            }

            MainActivity activity = mWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            try {
                URL url = new UrlHelper()
                        .getMoviesUrl(activity, mSortType, 1);
                if (url == null) {
                    return null;
                }

                Response response = new ApiHelper().getMoviesResult(url);
                if (response != null && response.code() == 200 && response.body() != null) {
                    return new Gson().fromJson(response.body().string(), MoviesResponse.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

