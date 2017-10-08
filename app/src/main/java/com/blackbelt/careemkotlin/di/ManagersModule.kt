package com.blackbelt.careemkotlin.di

import android.arch.persistence.room.Room
import android.content.Context
import com.blackbelt.careemkotlin.api.ApiKeyInterceptor
import com.blackbelt.careemkotlin.api.ApiManager
import com.blackbelt.careemkotlin.api.IApiManager
import com.blackbelt.careemkotlin.database.MoviesDatabase
import com.blackbelt.careemkotlin.movies.IMoviesManager
import com.blackbelt.careemkotlin.movies.MoviesManager
import com.blackbelt.careemkotlin.pictures.ConfigurationManager
import com.blackbelt.careemkotlin.pictures.IConfigurationManager
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.pictures.PictureManager
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

    @Singleton
    @Provides
    fun provideMoviesManager(apiManager: IApiManager, moviesDatabase: MoviesDatabase): IMoviesManager
            = MoviesManager(apiManager, moviesDatabase)

    @Singleton
    @Provides
    fun provideConfigurationManager(apiManager: IApiManager, database: MoviesDatabase): IConfigurationManager
            = ConfigurationManager(apiManager, database)

    @Singleton
    @Provides
    fun providesMoviesDatabase(context: Context): MoviesDatabase =
            Room.databaseBuilder(context, MoviesDatabase::class.java, "moviesdb")
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun providesPicturManagers(configurationManager: IConfigurationManager): IPictureManager
            = PictureManager(configurationManager)
}