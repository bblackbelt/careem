package com.blackbelt.careemkotlin.api

import com.blackbelt.careemkotlin.api.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    fun discoverMovies(@QueryMap query: Map<String, String>): Observable<PaginatedResponse<Movie>>

    @GET("3/discover/tv")
    fun discoverTvShows(@QueryMap query: Map<String, String>): Observable<PaginatedResponse<TvShow>>

    @GET("3/configuration")
    fun getConfiguration(): Observable<Configuration>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int,
                       @Query("append_to_response") appendToResponse: String): Observable<MovieDetails>

    @GET("3/tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId: Int,
                         @Query("append_to_response") appendToResponse: String): Observable<TvShowDetails>
}