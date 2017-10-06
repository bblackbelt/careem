package com.blackbelt.careemkotlin.api

import com.blackbelt.careemkotlin.api.model.Movie
import com.blackbelt.careemkotlin.api.model.PaginatedResponse
import com.blackbelt.careemkotlin.api.model.TvShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    fun discoverMovies(@QueryMap query: Map<String, String>): Observable<PaginatedResponse<Movie>>

    @GET("3/discover/tv")
    fun discoverTvShows(@QueryMap query: Map<String, String>): Observable<PaginatedResponse<TvShow>>

}