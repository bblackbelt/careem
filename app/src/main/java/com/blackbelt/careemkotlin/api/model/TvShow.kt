package com.blackbelt.careemkotlin.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class TvShow {

    @Expose
    var id: Int = 0

    @Expose
    @SerializedName("poster_path")
    lateinit var posterPath: String

    @Expose
    lateinit var overview: String

    @Expose
    @SerializedName("first_air_date")
    lateinit var firstAirDate: Date

    @Expose
    @SerializedName("origin_country")
    lateinit var originCountry: List<String>

    @Expose
    @SerializedName("original_name")
    lateinit var originalTitle: String

    @Expose
    @SerializedName("name")
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