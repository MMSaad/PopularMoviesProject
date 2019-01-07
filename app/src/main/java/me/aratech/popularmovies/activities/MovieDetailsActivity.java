package me.aratech.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.data.Movie;
import me.aratech.popularmovies.helpers.IntentHelper;
import me.aratech.popularmovies.helpers.UrlHelper;
import me.aratech.popularmovies.utils.Constants;

public class MovieDetailsActivity extends AppCompatActivity {

    /***
     * Vars
     */
    private Movie mMovie;

    /***
     * Views
     */
    @BindView(R.id.iv_movie_cover) ImageView ivMovieCover;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.tv_rating) TextView tvRating;
    @BindView(R.id.tv_release_date) TextView tvReleaseDate;
    @BindView(R.id.tv_original_title) TextView tvOriginalTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().hasExtra(Constants.MOVIE_ITEM)) {
            mMovie = (Movie) getIntent().getSerializableExtra(Constants.MOVIE_ITEM);
            bindUi();
        } else {
            //Show Error Toast
            finish();
        }
    }

    private void bindUi() {

        setTitle(mMovie.getTitle());
        tvOriginalTitle.setText(mMovie.getOriginalTitle());
        Picasso
                .get()
                .load(UrlHelper.IMAGE_BASE_PATH + mMovie.getBackdrop())
                .into(ivMovieCover);
        tvOverview.setText(mMovie.getOverview());
        tvRating.setText(String.valueOf(mMovie.getPopularity()));
        tvReleaseDate.setText(mMovie.getReleaseDate());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab_webpage)
    void shareButtonClicked(View v) {
        IntentHelper.openUrl(UrlHelper.MOVIES_BASE_PATH + String.valueOf(mMovie.getId()), this);
    }
}
