package com.blackbelt.careemkotlin.view.misc

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackbelt.careemkotlin.BR
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel
import dagger.android.support.AndroidSupportInjection

open class BaseInjectableFragment : Fragment() {

    private var mMoviesViewModel: BaseMovieViewModel? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun onCreateBindableView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, layoutId : Int, viewModel: BaseMovieViewModel): View? {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, container, false)
        mMoviesViewModel = viewModel
        viewDataBinding.setVariable(BR.moviesViewModel, viewModel)
        mMoviesViewModel?.onCreate(savedInstanceState)
        return viewDataBinding.root
    }


    override fun onStart() {
        super.onStart()
        mMoviesViewModel?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMoviesViewModel?.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        mMoviesViewModel?.onDestroy()
    }
}