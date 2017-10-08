package com.blackbelt.careemkotlin.api.model

import android.text.TextUtils
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.view.model.MovieItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TvShow(@Expose var id: Int = 0,
             @Expose @SerializedName("name") var title: String = "",
             @Expose @SerializedName("original_name") var originalTitle: String = "",
             @Expose @SerializedName("backdrop_path") var backdropPath: String = "",
             @Expose @SerializedName("poster_path") var posterPath: String = "",
             @Expose @SerializedName("overview") var overview: String = "",
             @Expose @SerializedName("vote_average") var voteAverage: Float = 0f,
             @Expose @SerializedName("release_date") var releaseDate: String = "") : MovieItem {


    override fun getMovieTitle(): String {
        if (!TextUtils.isEmpty(title)) {
            return title
        }
        return originalTitle
    }

    override fun getMoviePosterPath(): String {
        return posterPath
    }

    override fun getMovieId(): Int {
        return id
    }

    override fun getMovieVoteAverage(): String {
        return "%.2f".format(voteAverage)
    }

    override fun getMediaType(): String {
        return MoviePage.TvShows.name
    }
}