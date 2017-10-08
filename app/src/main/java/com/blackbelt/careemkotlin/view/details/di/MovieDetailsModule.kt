package com.blackbelt.careemkotlin.view.details.di

import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.view.details.MovieDetailsActivity
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieId(activity: MovieDetailsActivity): Int = activity.intent.getIntExtra(MovieDetailsActivity.MOVIE_ID_KEY, 0)

    @Provides
    fun provideMoviePage(activity: MovieDetailsActivity) : MoviePage
            = MoviePage.valueOf(activity.intent.getStringExtra(MovieDetailsActivity.MOVIE_ID_PAGE))

}