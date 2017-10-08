package com.blackbelt.careemkotlin.api

import com.blackbelt.careemkotlin.api.model.*
import io.reactivex.Observable

interface IApiManager {

    fun discoverMovies(query: Map<String, String>): Observable<PaginatedResponse<Movie>>

    fun discoverTvShows(query: Map<String, String>): Observable<PaginatedResponse<TvShow>>

    fun getConfiguration(): Observable<Configuration>

    fun getMovieDetails(id: Int, append: String = ""): Observable<MovieDetails>

    fun getTvShowDetails(id: Int, append: String = ""): Observable<TvShowDetails>
}