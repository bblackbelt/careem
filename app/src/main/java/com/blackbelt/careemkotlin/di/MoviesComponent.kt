package com.blackbelt.careemkotlin.di

import com.blackbelt.careemkotlin.MoviesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        BuildersModule::class,
        SystemModule::class))
interface MoviesComponent {

    fun inject(app: MoviesApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MoviesApp): Builder

        fun systemModule(systemModule: SystemModule): Builder

        fun build(): MoviesComponent
    }
}