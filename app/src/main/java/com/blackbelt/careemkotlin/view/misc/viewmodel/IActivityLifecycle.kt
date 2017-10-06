package com.blackbelt.careemkotlin.view.misc.viewmodel

import android.os.Bundle

interface IActivityLifecycle {

    fun onCreate(savedInstanceState: Bundle?) {}

    fun onPostCreate(savedInstanceState: Bundle?) {}

    fun onStart() {}

    fun onStop() {}

    fun onDestroy() {}
}