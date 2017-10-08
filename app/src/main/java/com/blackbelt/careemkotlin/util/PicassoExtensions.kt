package com.blackbelt.careemkotlin.util

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

@BindingAdapter("backdrop")
fun ImageView.loadBackdrop(url: String?) {
    if (TextUtils.isEmpty(url)) {
        return
    }
    val res = context.resources
    layoutParams.width = res.displayMetrics.widthPixels;
    layoutParams.height = (layoutParams.width * 9f / 16).toInt()
    Picasso.with(this.context).load(url).resize(layoutParams.width, layoutParams.height).centerCrop().into(this)
}

@BindingAdapter("mainPosterImageUrl", "spanCount")
fun ImageView.loadMainPosterImageUrl(url: String?, spanCount: Int = 3) {
    if (TextUtils.isEmpty(url)) {
        return
    }
    val res = context.resources
    layoutParams.width = res.displayMetrics.widthPixels / spanCount
    layoutParams.height = (layoutParams.width / 0.667f).toInt()
    Picasso.with(this.context).load(url).resize(layoutParams.width, layoutParams.height).centerCrop().into(this)
}

inline fun ImageView.loadUrl(url: String, callback: PicassoCallback.() -> Unit) {
    Picasso.with(this.context).load(url).intoWithCallback(this, callback)
}

inline fun RequestCreator.intoWithCallback(target: ImageView, callback: PicassoCallback.() -> Unit) {
    this.into(target, PicassoCallback().apply(callback))
}

class PicassoCallback : Callback {

    private var onSuccess: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    override fun onSuccess() {
        onSuccess?.invoke()
    }

    override fun onError() {
        onError?.invoke()
    }

    fun onSuccess(function: () -> Unit) {
        this.onSuccess = function
    }

    fun onError(function: () -> Unit) {
        this.onError = function
    }
}