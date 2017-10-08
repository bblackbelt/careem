package com.blackbelt.careemkotlin

import android.app.Activity
import android.app.Application
import com.blackbelt.careemkotlin.di.DaggerMoviesComponent
import com.blackbelt.careemkotlin.di.ManagersModule
import com.blackbelt.careemkotlin.di.SystemModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MoviesApp : Application(), HasActivityInjector {

    @Inject
    lateinit var mAndroidDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerMoviesComponent
                .builder()
                .application(this)
                .systemModule(SystemModule(this))
                .managersModule(ManagersModule())
                .build()
                .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = mAndroidDispatchingInjector;

}