package me.aratech.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
import me.aratech.popularmovies.helpers.UrlHelper;
import me.aratech.popularmovies.interfaces.IFilterChangeListener;
import me.aratech.popularmovies.interfaces.IMovieListIemClickedListener;
import me.aratech.popularmovies.utils.Constants;
import me.aratech.popularmovies.webApi.responses.MoviesResponse;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements IFilterChangeListener, IMovieListIemClickedListener {

    /***
     * Vars
     */
    private MoviesResponse mResponse;
    private Constants.SortType mSelectedFilter = Constants.SortType.Popular;


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
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.MOVIES_RESULT)) {
            mResponse = (MoviesResponse) savedInstanceState.getSerializable(Constants.MOVIES_RESULT);
        }
        new GetMoviesAsync(this, mResponse)
                .execute();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResponse != null) {
            outState.putSerializable(Constants.MOVIES_RESULT, mResponse);
        }
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

    @OnClick(R.id.fab_filter)
    void filterFabPressed(View v) {
        MoviesFilterSheet sheet = MoviesFilterSheet.newInstance(mSelectedFilter);
        sheet.show(getSupportFragmentManager(), Constants.TAG_FILTERS);
    }

    @Override
    public void filterChanged(Constants.SortType newFilter) {
        mSelectedFilter = newFilter;
        new GetMoviesAsync(this, null)
                .execute();
    }


    @Override
    public void movieItemClicked(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ITEM, movie);
        startActivity(intent);
    }

    static class GetMoviesAsync extends AsyncTask<Void, Void, MoviesResponse> {

        private final WeakReference<MainActivity> mWeakReference;
        private final MoviesResponse mMoviesResponse;

        GetMoviesAsync(MainActivity activity, MoviesResponse response) {
            mWeakReference = new WeakReference<>(activity);
            mMoviesResponse = response;
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
                Toast.makeText(activity, "Error fetching API, please check your internet connection and try again", Toast.LENGTH_LONG).show();
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
                URL url = new UrlHelper().getMoviesUrl(activity, Constants.SortType.Popular, 1);
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

