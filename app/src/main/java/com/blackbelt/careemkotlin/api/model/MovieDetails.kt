package com.blackbelt.careemkotlin.api.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.blackbelt.careemkotlin.view.MovieItemDetails
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class MovieDetails(@PrimaryKey @Expose var id: Int = 0,
                        @Expose var title: String = "",
                        @Expose @SerializedName("poster_path") var posterPath: String = "",
                        @Expose var overview: String = "",
                        @Expose var runtime: Int = 0,
                        @Expose var status: String = "",
                        @Expose @SerializedName("release_date") var releaseDate: String = "",
                        @Expose @Ignore @Embedded var images: MovieTvShowImages = MovieTvShowImages.EMPTY,
                        @Expose @SerializedName("vote_average") var voteAverage: Float = 0f) : MovieItemDetails {

    companion object {
        val EMPTY = MovieDetails()
    }

    override fun getMovieTitle(): String {
        return title
    }

    override fun getMoviePosterPath(): String {
        return posterPath
    }

    override fun getMovieVoteAverage(): String {
        return "%.2f".format(voteAverage)
    }

    override fun getMovieId(): Int {
        return id
    }

    override fun getBackdrops(): List<Image> {
        return images.backdrops
    }

    override fun getMovieSubtitle(): String {
        return "$status - $runtime min"
    }

    override fun getMovieOverview(): String {
        return overview
    }

    override fun getMovieReleaseDate(): String {
        return releaseDate
    }
}

@Entity
data class TvShowDetails(@PrimaryKey @Expose var id: Int = 0,
                         @Expose var name: String = "",
                         @Expose @SerializedName("poster_path") var posterPath: String = "",
                         @Expose var overview: String = "",
                         @Expose var status: String = "",
                         @Expose @SerializedName("first_air_date") var releaseDate: String = "",
                         @Expose @Ignore @Embedded var images: MovieTvShowImages = MovieTvShowImages.EMPTY,
                         @Expose @SerializedName("episode_run_time") var episodesRuntime: List<Int> = ArrayList(),
                         @Expose @SerializedName("number_of_episodes") var numberOfEpisodes: Int = 0,
                         @Expose @SerializedName("number_of_seasons") var numberOfSeasons: Int = 0,
                         @Expose @SerializedName("vote_average") var voteAverage: Float = 0f) : MovieItemDetails {

    companion object {
        val EMPTY = TvShowDetails()
    }

    override fun getMovieTitle(): String {
        return name
    }

    override fun getMoviePosterPath(): String {
        return posterPath
    }

    override fun getMovieVoteAverage(): String {
        return "%.2f".format(voteAverage)
    }

    override fun getMovieId(): Int {
        return id
    }

    override fun getBackdrops(): List<Image> {
        return images.backdrops
    }

    override fun getMovieSubtitle(): String {
        val runtime = episodesRuntime.getOrElse(0) { 0 }
        return "$status - $runtime min"
    }

    override fun getMovieOverview(): String {
        return overview
    }

    override fun getMovieReleaseDate(): String {
        return releaseDate
    }
}

data class MovieTvShowImages(@Expose var backdrops: List<Image> = ArrayList()) {
    companion object {
        val EMPTY = MovieTvShowImages()
    }
}

@Entity
data class Image(@Expose @SerializedName("file_path") var filePath: String = "",
                 @Expose @SerializedName("aspect_ratio") var aspectRatio: Float = 1f,
                 @Expose var width: Int = 0, @Expose var height: Int = 0)


