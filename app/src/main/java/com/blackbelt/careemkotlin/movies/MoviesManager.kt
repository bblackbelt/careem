package com.blackbelt.careemkotlin.movies

import android.util.SparseArray
import com.blackbelt.careemkotlin.api.IApiManager
import com.blackbelt.careemkotlin.api.model.PaginatedResponse
import com.blackbelt.careemkotlin.view.MovieItemDetails
import com.blackbelt.careemkotlin.view.model.MovieItem
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject

class MoviesManager(apiManager: IApiManager) : IMoviesManager {

    class PageDescriptor<T> {
        internal var mDisposable = Disposables.disposed()

        internal var mResultsNotifier: BehaviorSubject<T> = BehaviorSubject.create()
    }

    private val mDiscoverDescriptorSparseArray = SparseArray<PageDescriptor<PaginatedResponse<MovieItem>>>()

    val mApiManager = apiManager

    private fun getOrCreateDescriptor(moviePage: MoviePage): PageDescriptor<PaginatedResponse<MovieItem>> {
        if (mDiscoverDescriptorSparseArray[moviePage.ordinal] == null) {
            val descriptor = PageDescriptor<PaginatedResponse<MovieItem>>()
            descriptor.mResultsNotifier = BehaviorSubject.create()
            mDiscoverDescriptorSparseArray.put(moviePage.ordinal, descriptor)
        }
        return mDiscoverDescriptorSparseArray[moviePage.ordinal]
    }

    override fun getDiscoverResults(moviePage: MoviePage): Observable<PaginatedResponse<MovieItem>> {
        return getOrCreateDescriptor(moviePage).mResultsNotifier.hide()
    }

    override fun loadDiscoverResults(moviePage: MoviePage, currentPage: Int, additionalQuery: MutableMap<String, String>) {
        val resultsObservable: Observable<PaginatedResponse<MovieItem>>
        additionalQuery.put("page", currentPage.toString())
        when (moviePage) {
            MoviePage.Movie -> resultsObservable =
                    mApiManager.discoverMovies(additionalQuery)
                            .map { pageResults ->
                                val page = PaginatedResponse<MovieItem>()
                                page.results = ArrayList(pageResults.results)
                                page.page = pageResults.page
                                page.totalPages = pageResults.totalPages
                                page
                            }
            MoviePage.TvShows -> resultsObservable =
                    mApiManager.discoverTvShows(additionalQuery)
                            .map { pageResults ->
                                val page = PaginatedResponse<MovieItem>()
                                page.results = ArrayList(pageResults.results)
                                page.page = pageResults.page
                                page.totalPages = pageResults.totalPages
                                page
                            }
        }
        val descriptor = getOrCreateDescriptor(moviePage)
        descriptor.mDisposable.dispose()
        descriptor.mDisposable = resultsObservable
                .subscribe(descriptor.mResultsNotifier::onNext,
                        descriptor.mResultsNotifier::onError)
    }


    override fun loadDetails(movieId: Int, moviePage: MoviePage): Observable<MovieItemDetails> {
        return when (moviePage) {
            MoviePage.Movie -> mApiManager.getMovieDetails(movieId, "images").map { t -> t }
            MoviePage.TvShows -> mApiManager.getTvShowDetails(movieId, "images").map { t -> t }
        }
    }

}