package com.blackbelt.careemkotlin.view

import com.blackbelt.careemkotlin.api.model.Image
import com.blackbelt.careemkotlin.api.model.MovieTvShowImages
import com.blackbelt.careemkotlin.view.model.MovieItem

interface MovieItemDetails : MovieItem {

    fun getBackdrops(): List<Image> {
        return MovieTvShowImages.EMPTY.backdrops
    }

    fun getMovieOverview(): String {
        return ""
    }

    fun getMovieReleaseDate(): String {
        return ""
    }
}