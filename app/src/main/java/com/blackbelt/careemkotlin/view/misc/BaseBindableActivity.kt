package com.blackbelt.careemkotlin.view.misc

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel


abstract class BaseBindableActivity : BaseInjectableActivity() {

    private  var mMovieBaseViewModel: BaseMovieViewModel? = null

    private lateinit var mBinding: ViewDataBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMovieBaseViewModel?.onCreate(savedInstanceState)
    }

    fun setContentView(bindingVariable: Int, viewModel: BaseMovieViewModel, @LayoutRes layoutRes: Int) {
        mMovieBaseViewModel = viewModel
        mBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRes);
        mBinding.setVariable(bindingVariable, viewModel)
        viewModel.onCreate(null)
    }

    override fun onStart() {
        super.onStart()
        mMovieBaseViewModel?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMovieBaseViewModel?.onStop()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mMovieBaseViewModel?.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMovieBaseViewModel?.onDestroy()
    }
}
