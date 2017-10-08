package com.blackbelt.careemkotlin.view.model


interface MovieItem {

    fun getMovieTitle(): String {
        return ""
    }

    fun getMovieSubtitle(): String {
        return ""
    }

    fun getMoviePosterPath(): String {
        return ""
    }

    fun getMovieVoteAverage(): String {
        return ""
    }

    fun getMovieId(): Int {
        return 0
    }

    fun getMediaType(): String{
        return ""
    }
}