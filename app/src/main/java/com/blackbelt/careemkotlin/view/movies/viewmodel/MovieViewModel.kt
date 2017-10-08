package com.blackbelt.careemkotlin.view.movies.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.view.model.MovieItem

class MovieViewModel(pictureManager: IPictureManager, movieItem: MovieItem, posterWidth: Int = -1) : BaseObservable() {

    val mMovie = movieItem

    val mPictureManager = pictureManager

    val mPosterWidth = posterWidth

    @Bindable
    fun getTitle(): String? {
        return mMovie.getMovieTitle()
    }

    fun getImageUrl(): String? {
        return mPictureManager.getPosterUrl(mMovie.getMoviePosterPath(), mPosterWidth)
    }

    fun getMovieVoteAverage(): String? {
        return mMovie.getMovieVoteAverage()
    }
}