package me.aratech.popularmovies.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import me.aratech.popularmovies.utils.Constants;

/***
 * Movie information data Model
 */
public class Movie implements Serializable {

    @Expose
    @SerializedName(Constants.ID)
    private long Id;

    @Expose
    @SerializedName(Constants.VOTES_COUNT)
    private long VotesCount;

    @Expose
    @SerializedName(Constants.HAS_VIDEO)
    private boolean HasVideo;

    @Expose
    @SerializedName(Constants.VOTE_AVERAGE)
    private double VoteAverage;

    @Expose
    @SerializedName(Constants.TITLE)
    private String Title;

    @Expose
    @SerializedName(Constants.ORIGINAL_TITLE)
    private String OriginalTitle;

    @Expose
    @SerializedName(Constants.POPULARITY)
    private double Popularity;

    @Expose
    @SerializedName(Constants.POSTER_PATH)
    private String PosterPath;

    @Expose
    @SerializedName(Constants.OVERVIEW)
    private String Overview;

    @Expose
    @SerializedName(Constants.RELEASE_DATE)
    private String ReleaseDate;

    @Expose
    @SerializedName(Constants.BACKDROP)
    private String Backdrop;

    public long getId() {
        return Id;
    }

    public long getVotesCount() {
        return VotesCount;
    }

    public boolean isHasVideo() {
        return HasVideo;
    }

    public double getVoteAverage() {
        return VoteAverage;
    }

    public String getTitle() {
        return Title;
    }

    public double getPopularity() {
        return Popularity;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public String getOverview() {
        return Overview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public String getBackdrop() {
        return Backdrop;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }
}

