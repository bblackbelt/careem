package com.blackbelt.careemkotlin.di

import com.blackbelt.careemkotlin.view.SplashActivity
import com.blackbelt.careemkotlin.view.details.MovieDetailsActivity
import com.blackbelt.careemkotlin.view.details.di.MovieDetailsModule
import com.blackbelt.careemkotlin.view.misc.MainActivity
import com.blackbelt.careemkotlin.view.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun moviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(MovieDetailsModule::class))
    abstract fun movieDetailsActivity(): MovieDetailsActivity
}