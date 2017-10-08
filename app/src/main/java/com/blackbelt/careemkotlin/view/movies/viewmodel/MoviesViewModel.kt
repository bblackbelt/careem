package com.blackbelt.careemkotlin.view.movies.viewmodel

import android.content.Intent
import android.content.res.Resources
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.view.View
import com.blackbelt.careemkotlin.BR
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.bindable.AndroidBaseItemBinder
import com.blackbelt.careemkotlin.bindable.PageDescriptor
import com.blackbelt.careemkotlin.bindable.RecyclerViewClickListener
import com.blackbelt.careemkotlin.movies.IMoviesManager
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.view.details.MovieDetailsActivity
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel
import com.blackbelt.careemkotlin.view.misc.viewmodel.ProgressLoader
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import java.util.*
import javax.inject.Inject


class MoviesViewModel @Inject constructor(moviesManager: IMoviesManager, pictureManager: IPictureManager, resources: Resources) : BaseMovieViewModel() {

    private val mProgressLoader = ProgressLoader()

    private val mMoviesManager = moviesManager

    private val mPictureManager = pictureManager

    private val mResources = resources;

    var mItems: ObservableList<Any> = ObservableArrayList()

    val mPageDescriptor = PageDescriptor()

    lateinit var moviePage: MoviePage

    var mYear = Calendar.getInstance()[Calendar.YEAR]
    var mMonth = Calendar.getInstance()[Calendar.MONTH]
    var mDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    val mAdditionalInfo: MutableMap<String, String> = hashMapOf("primary_release_date.lte" to "$mYear-$mMonth-$mDay")

    var mTemplates: Map<Class<*>, AndroidBaseItemBinder>
        @Bindable get() = field

    init {
        mTemplates = hashMapOf(
                MovieViewModel::class.java to AndroidBaseItemBinder(BR.movieViewModel, R.layout.movie_item),
                ProgressLoader::class.java to AndroidBaseItemBinder(BR.progressLoader, R.layout.material_progress_bar))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResults()
    }

    var mDiscoverResults: Disposable = Disposables.disposed()

    private fun getResults() {
        mDiscoverResults = mMoviesManager.getDiscoverResults(moviePage)
                .map { page ->
                    page.results.mapTo(mItems)
                    { MovieViewModel(mPictureManager, it, mResources.displayMetrics.widthPixels / 3) }
                    page
                }
                .subscribe({
                    notifyChange()
                    setLoading(false)
                }, { t: Throwable ->
                    setLoading(false)
                    t.printStackTrace()
                })
    }

    @Bindable
    fun getPageDescriptor(): PageDescriptor {
        return mPageDescriptor
    }

    @Bindable
    fun setPageDescriptor(pageDescriptor: PageDescriptor?) {
        if (pageDescriptor == null) {
            return
        }
        setLoading(true)
        loadNextPage(pageDescriptor.mCurrentPage)
    }

    fun loadNextPage(currentPage: Int) {
        mMoviesManager.loadDiscoverResults(moviePage, currentPage, mAdditionalInfo)
    }

    fun getItemClickListener(): RecyclerViewClickListener {
        return object : RecyclerViewClickListener {
            override fun onItemClick(view: View, any: Any) {
                val intent = Intent(view.context, MovieDetailsActivity::class.java)
                intent.putExtra(MovieDetailsActivity.MOVIE_ID_KEY, (any as MovieViewModel).mMovie.getMovieId())
                intent.putExtra(MovieDetailsActivity.MOVIE_ID_PAGE, any.mMovie.getMediaType())
                intent.putExtra(MovieDetailsActivity.MOVIE_TITLE, any.mMovie.getMovieTitle())
                view.context.startActivity(intent)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading && !mItems.contains(mProgressLoader)) {
            mItems.add(mProgressLoader)
        } else {
            mItems.remove(mProgressLoader)
        }
    }

    fun isProgressLoader(pos: Int): Boolean {
        return mItems.getOrNull(pos) is ProgressLoader
    }

    fun updateDate(year: Int, month: Int, day: Int) {
        mMonth = month
        mYear = year
        mDay = day
        getPageDescriptor().mCurrentPage = 1
        mItems.clear()
        mAdditionalInfo.put("primary_release_date.lte", "$mYear-$mMonth-$mDay")
        setPageDescriptor(mPageDescriptor)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDiscoverResults.dispose()
    }

}