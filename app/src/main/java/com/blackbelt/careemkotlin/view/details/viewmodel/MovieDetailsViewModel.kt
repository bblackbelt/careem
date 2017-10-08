package com.blackbelt.careemkotlin.view.details.viewmodel

import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.text.TextUtils
import com.blackbelt.careemkotlin.BR
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.api.model.MovieDetails
import com.blackbelt.careemkotlin.api.model.TvShowDetails
import com.blackbelt.careemkotlin.bindable.AndroidBaseItemBinder
import com.blackbelt.careemkotlin.movies.IMoviesManager
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.view.MovieItemDetails
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(movieId: Int, moviePage: MoviePage,
                                                moviesManager: IMoviesManager, pictureManager: IPictureManager) : BaseMovieViewModel() {

    val mMovieId = movieId
    val mMoviePage = moviePage

    val mSimpleDateFormatIn = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val mSimpleDateFormatOut = SimpleDateFormat("yyyy", Locale.getDefault())

    val mMoviesManager = moviesManager
    val mPictureManager = pictureManager

    var mMovieItem: MovieItemDetails? = null

    val mImages = ObservableArrayList<ImageViewModel>()

    var mDetails: Disposable = Disposables.disposed()

    private var mError = false

    private var mLoading = true

    var mTemplates: Map<Class<*>, AndroidBaseItemBinder>
        @Bindable get() = field

    init {
        mTemplates = hashMapOf(
                ImageViewModel::class.java to AndroidBaseItemBinder(BR.imageViewModel, R.layout.movie_details_backdrop_item))
        loadDetails(movieId, moviePage)
    }

    private fun loadDetails(movieId: Int, moviePage: MoviePage) {
        setLoading(true)
        mDetails.dispose()
        mDetails = mMoviesManager.loadDetails(movieId, moviePage)
                .filter { t: MovieItemDetails ->
                    if ((t as? TvShowDetails) == TvShowDetails.EMPTY ||
                            (t as? MovieDetails) == MovieDetails.EMPTY) {
                        notifyError(true)
                        return@filter false
                    }
                    true
                }
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

    private fun notifyError(error: Boolean) {
        setLoading(false)
        mError = error
        notifyChange()
    }

    private fun setLoading(b: Boolean) {
        mLoading = b
        notifyPropertyChanged(BR.loading)
    }

    @Bindable
    fun isError(): Boolean {
        return mError
    }

    @Bindable
    fun isLoading(): Boolean {
        return mLoading;
    }

    @Bindable
    fun isContent(): Boolean {
        return !isLoading() && !isError()
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
        if (TextUtils.isEmpty(mMovieItem?.getMovieReleaseDate())) {
            return ""
        }
        val releaseDate = mMovieItem?.getMovieReleaseDate()
        val formatter = mSimpleDateFormatIn.parse(releaseDate)
        return mSimpleDateFormatOut.format(formatter)
    }

    fun getMovieVoteAverage(): String? {
        return mMovieItem?.getMovieVoteAverage()
    }

    fun onRetryClicked() {
        notifyError(false)
        loadDetails(mMovieId, mMoviePage)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetails.dispose()
    }
}