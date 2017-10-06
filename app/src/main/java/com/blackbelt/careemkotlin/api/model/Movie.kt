package com.blackbelt.careemkotlin.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Movie {

    @Expose
    @SerializedName("poster_path")
    lateinit var posterPath: String

    @Expose
    lateinit var overview: String

    @Expose
    @SerializedName("release_date")
    lateinit var releaseDate: Date

    @Expose
    var adult: Boolean = false

    @Expose
    var video: Boolean = false

    @Expose
    @SerializedName("genre_ids")
    lateinit var genreIds: List<Int>

    @Expose
    var id: Int = 0

    @Expose
    @SerializedName("original_title")
    lateinit var originalTitle: String

    @Expose
    @SerializedName("original_language")
    lateinit var originalLanguage: String

    @Expose
    lateinit var title: String

    @Expose
    @SerializedName("backdrop_path")
    lateinit var backdropPath: String

    @Expose
    var popularity: Float = 0f

    @Expose
    @SerializedName("vote_count")
    var voteCount: Long = 0

    @Expose
    @SerializedName("vote_average")
    var voteAverage: Float = 0f

}