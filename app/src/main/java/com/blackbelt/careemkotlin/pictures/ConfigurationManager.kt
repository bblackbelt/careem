package com.blackbelt.careemkotlin.pictures

import com.blackbelt.careemkotlin.api.IApiManager
import com.blackbelt.careemkotlin.api.model.Configuration
import com.blackbelt.careemkotlin.database.MoviesDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject

class ConfigurationManager(apiManager: IApiManager, moviesDatabase: MoviesDatabase) : IConfigurationManager {

    val mApiManager = apiManager
    val mMovieDatabase = moviesDatabase

    var mConfigurationSubscription = Disposables.disposed()

    private var mConfigurationSubject = BehaviorSubject.create<Configuration>()

    init {
        loadConfiguration()
    }

    private fun loadConfiguration() {
        val networkConfiguration =
                mApiManager.getConfiguration()
                        .onErrorReturn({ Configuration.EMPTY })
                        .doOnNext({ conf ->
                            if (conf != Configuration.EMPTY) {
                                mMovieDatabase.getConfigurationDao().add(conf)
                            }
                        })

        mConfigurationSubscription = mMovieDatabase
                .getConfigurationDao()
                .configuration
                .toObservable()
                .map({ configurations ->
                    if (configurations.isEmpty()) {
                        return@map Configuration.EMPTY
                    }
                    configurations[0]
                })
                .distinctUntilChanged()
                .flatMap({ networkConfiguration })
                .subscribe({ conf ->
                    if (!mConfigurationSubject.hasValue()) {
                        mConfigurationSubject.onNext(conf)
                    }
                }, Throwable::printStackTrace)
    }

    override fun getConfigurationObservable(): Observable<Configuration> {
        if (mConfigurationSubject.value === Configuration.EMPTY) {
            mConfigurationSubject = BehaviorSubject.create()
            mConfigurationSubscription.dispose()
            loadConfiguration()
        }
        return mConfigurationSubject.hide()
    }

    override fun getMaxPosterBucket(): String {
        return getPosterBucket(-1)
    }

    override fun getPosterBucket(size: Int): String {
        val configuration = mConfigurationSubject.value
        if (configuration == null || configuration == Configuration.EMPTY) {
            return ""
        }
        return getSizeFrom(configuration.imagesConfiguration.posterSizes, size)
    }

    override fun getMaxBackdropBucket(): String {
        return getBackdropBucket(-1)
    }

    override fun getBackdropBucket(size: Int): String {
        val configuration = mConfigurationSubject.value
        if (configuration == null || configuration == Configuration.EMPTY) {
            return ""
        }
        return getSizeFrom(configuration.imagesConfiguration.backdropSizes, size)
    }

    private fun getSizeFrom(sizes: List<String>?, maxWidth: Int): String {
        if (sizes == null || sizes.isEmpty()) {
            return ""
        }
        if (maxWidth > 0) {
            for (size in sizes) {
                try {
                    val bucketSize = Integer.valueOf(size.substring(1, size.length))!!
                    if (bucketSize >= maxWidth) {
                        return size
                    }
                } catch (e: Exception) {
                }

            }
        }
        return sizes[sizes.size - 1]
    }

    override fun getBaseUrl(): String {
        val configuration = mConfigurationSubject.value
        if (configuration == null || configuration == Configuration.EMPTY) {
            return ""
        }
        return configuration.imagesConfiguration.baseUrl
    }
}