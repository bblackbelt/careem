package com.blackbelt.careemkotlin.view.details.viewmodel

import android.databinding.Bindable
import android.databinding.ObservableArrayList
import com.blackbelt.careemkotlin.BR
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.bindable.AndroidBaseItemBinder
import com.blackbelt.careemkotlin.movies.IMoviesManager
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.view.MovieItemDetails
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(movieId: Int, moviePage: MoviePage,
                                                moviesManager: IMoviesManager, pictureManager: IPictureManager) : BaseMovieViewModel() {

    val mSimpleDateFormatIn = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val mSimpleDateFormatOut = SimpleDateFormat("yyyy", Locale.getDefault())

    val mMoviesManager = moviesManager
    val mPictureManager = pictureManager

    var mMovieItem: MovieItemDetails? = null

    val mImages = ObservableArrayList<ImageViewModel>()

    var mTemplates: Map<Class<*>, AndroidBaseItemBinder>
        @Bindable get() = field

    init {
        mTemplates = hashMapOf(
                ImageViewModel::class.java to AndroidBaseItemBinder(BR.imageViewModel, R.layout.movie_details_backdrop_item))
        loadDetails(movieId, moviePage)
    }

    private fun loadDetails(movieId: Int, moviePage: MoviePage) {
        mMoviesManager.loadDetails(movieId, moviePage)
                .map { details ->
                    details.getBackdrops().mapTo(mImages, { ImageViewModel(it, mPictureManager) })
                    details
                }
                .subscribe({ details ->
                    mMovieItem = details
                    setLoading(false)
                    notifyChange()
                }, Throwable::printStackTrace)
    }

    private var mLoading: Boolean = true

    private fun setLoading(b: Boolean) {
        mLoading = b
        notifyPropertyChanged(BR.loading)
    }

    @Bindable
    fun isLoading(): Boolean {
        return mLoading;
    }

    @Bindable
    fun getImageUrl(): String {
        val path = mMovieItem?.getMoviePosterPath() ?: return ""
        return mPictureManager.getPosterUrl(path)
    }

    @Bindable
    fun getTitle(): String? {
        return mMovieItem?.getMovieTitle()
    }

    @Bindable
    fun getSubtitle(): String? {
        return mMovieItem?.getMovieSubtitle()
    }

    @Bindable
    fun getOverview(): String? {
        return mMovieItem?.getMovieOverview()
    }

    @Bindable
    fun getReleaseDate(): String? {
        val releaseDate = mMovieItem?.getMovieReleaseDate() ?: return ""
        val formatter = mSimpleDateFormatIn.parse(releaseDate)
        return mSimpleDateFormatOut.format(formatter)
    }

    fun getMovieVoteAverage() : String? {
        return mMovieItem?.getMovieVoteAverage()
    }
}