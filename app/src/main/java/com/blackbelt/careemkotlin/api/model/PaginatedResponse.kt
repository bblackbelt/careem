package com.blackbelt.careemkotlin.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

open class PaginatedResponse<T> {

    @Expose
    var page: Int = 0

    @Expose
    @SerializedName("total_pages")
    var totalPages: Int = 0

    @Expose
    @SerializedName("total_results")
    var totalResults: Int = 0

    @Expose
    lateinit var results: List<T>

    @Expose
    lateinit var dates: Date
}