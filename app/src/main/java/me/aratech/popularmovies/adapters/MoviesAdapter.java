package me.aratech.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.data.Movie;
import me.aratech.popularmovies.helpers.UrlHelper;
import me.aratech.popularmovies.webApi.responses.MoviesResponse;

/***
 * Movies List/Grid Recycler View Adapter
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    /***
     * Vars
     */
    private MoviesResponse mResponse;

    public MoviesAdapter(MoviesResponse response) {
        mResponse = response;
    }

    @NonNull @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(mResponse.getMovies().get(i));
    }

    @Override
    public int getItemCount() {
        return mResponse.getMovies().size();
    }

    /***
     * Movie Adapter View Holder
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.iv_movie_cover) ImageView ivMovieCover;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /***
         * Load Movie Image
         * @param movie Current Adapter Movie
         */
        void bind(Movie movie) {
            Picasso
                    .get()
                    .load(UrlHelper.IMAGE_BASE_PATH + movie.getPosterPath())
                    .into(ivMovieCover);
        }
    }
}
