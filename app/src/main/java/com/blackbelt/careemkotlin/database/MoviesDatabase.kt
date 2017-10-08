package com.blackbelt.careemkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.blackbelt.careemkotlin.api.model.*
import com.blackbelt.careemkotlin.database.dao.*

@Database(version = 1, entities = arrayOf(Configuration::class, Movie::class, TvShow::class, MovieDetails::class, TvShowDetails::class))
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    abstract fun getTvShowsDao(): TvShowsDao

    abstract fun getMovieDetailsDao(): MovieDetailsDao

    abstract fun getTvShowDetailsDao(): TvShowDetailsDao

    abstract fun getConfigurationDao(): ConfigurationDao

}