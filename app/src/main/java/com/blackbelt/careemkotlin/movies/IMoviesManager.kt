package com.blackbelt.careemkotlin.movies

import com.blackbelt.careemkotlin.api.model.PaginatedResponse
import com.blackbelt.careemkotlin.view.MovieItemDetails
import com.blackbelt.careemkotlin.view.model.MovieItem
import io.reactivex.Observable

interface IMoviesManager {

    fun getDiscoverResults(moviePage: MoviePage): Observable<PaginatedResponse<MovieItem>>

    fun loadDiscoverResults(moviePage: MoviePage, currentPage: Int,
                            additionalQuery: MutableMap<String, String> = HashMap())

    fun loadDetails(movieId: Int, moviePage: MoviePage): Observable<MovieItemDetails>
}