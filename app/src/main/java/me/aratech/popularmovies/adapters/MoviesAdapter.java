package me.aratech.popularmovies.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aratech.popularmovies.R;
import me.aratech.popularmovies.data.Movie;
import me.aratech.popularmovies.helpers.UrlHelper;
import me.aratech.popularmovies.interfaces.IMovieListIemClickedListener;
import me.aratech.popularmovies.webApi.responses.MoviesResponse;

/***
 * Movies List/Grid Recycler View Adapter
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    /***
     * Vars
     */
    private final MoviesResponse mResponse;
    private final IMovieListIemClickedListener mListener;
    private int mLastPosition = -1;
    private final Activity mActivity;

    public MoviesAdapter(MoviesResponse response, IMovieListIemClickedListener listener, Activity activity) {
        mResponse = response;
        mListener = listener;
        mActivity = activity;
    }

    @NonNull @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mResponse.getMovies().get(position));
        setAnimation(movieViewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mResponse.getMovies().size();
    }


    /***
     * Start animation for view if it's does not appear before
     * @param viewToAnimate Target view
     * @param position View's Adapter position
     */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);

            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MovieViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
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

        //UI Events
        @SuppressWarnings("unused")
        @OnClick(R.id.iv_movie_cover)
        void movieCoverImageClicked(View v) {
            if (mListener != null && mResponse.getMovies().size() > getAdapterPosition()) {
                mListener.movieItemClicked(mResponse.getMovies().get(getAdapterPosition()));
            }
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

        /***
         * Clear animation
         */
        void clearAnimation() {
            ivMovieCover.clearAnimation();
        }
    }
}
