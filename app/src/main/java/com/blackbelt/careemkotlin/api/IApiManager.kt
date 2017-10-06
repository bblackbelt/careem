package com.blackbelt.careemkotlin.api

import com.blackbelt.careemkotlin.api.model.Movie
import com.blackbelt.careemkotlin.api.model.PaginatedResponse
import com.blackbelt.careemkotlin.api.model.TvShow
import io.reactivex.Observable

interface IApiManager {

    fun discoverMovies(query: Map<String, String>): Observable<PaginatedResponse<Movie>>

    fun discoverTvShows(query: Map<String, String>): Observable<PaginatedResponse<TvShow>>
}