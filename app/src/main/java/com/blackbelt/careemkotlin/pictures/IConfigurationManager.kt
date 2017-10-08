package com.blackbelt.careemkotlin.pictures

import com.blackbelt.careemkotlin.api.model.Configuration
import io.reactivex.Observable

interface IConfigurationManager {
    fun getConfigurationObservable(): Observable<Configuration>

    fun getPosterBucket(size: Int): String

    fun getMaxPosterBucket(): String

    fun getBackdropBucket(size: Int): String

    fun getMaxBackdropBucket(): String

    fun getBaseUrl() : String
}