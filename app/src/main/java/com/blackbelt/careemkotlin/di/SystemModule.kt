package com.blackbelt.careemkotlin.di

import android.content.Context
import android.content.res.Resources
import com.blackbelt.careemkotlin.MoviesApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SystemModule(private val moviesApp: MoviesApp) {

    @Singleton
    @Provides
    fun provideContext(): Context = moviesApp

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.getResources()
}