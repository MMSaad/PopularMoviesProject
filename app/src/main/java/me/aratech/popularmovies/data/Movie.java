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
    @SerializedName(Constants.VOTE_AVERAGE)
    private double VoteAverage;

    @Expose
    @SerializedName(Constants.TITLE)
    private String Title;

    @Expose
    @SerializedName(Constants.ORIGINAL_TITLE)
    private String OriginalTitle;

    @Expose
    @SerializedName(Constants.POSTER_PATH)
    private String PosterPath;

    @Expose
    @SerializedName(Constants.OVERVIEW)
    private String Overview;

    @Expose
    @SerializedName(Constants.RELEASE_DATE)
    private String ReleaseDate;

    public long getId() {
        return Id;
    }

    public double getVoteAverage() {
        return VoteAverage;
    }

    public String getTitle() {
        return Title;
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

    public String getOriginalTitle() {
        return OriginalTitle;
    }
}

