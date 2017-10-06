package com.blackbelt.careemkotlin.di

import com.blackbelt.careemkotlin.api.ApiKeyInterceptor
import com.blackbelt.careemkotlin.api.ApiManager
import com.blackbelt.careemkotlin.api.IApiManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagersModule {

    @Provides
    fun provideGson() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Singleton
    @Provides
    fun provideApiManager(gson: Gson, apiKeyInterceptor: ApiKeyInterceptor): IApiManager
            = ApiManager("https://api.themoviedb.org/", gson, apiKeyInterceptor)

}