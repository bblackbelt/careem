package com.blackbelt.careemkotlin.bindable

import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.lang.ref.WeakReference

open class RecyclerViewGestureListener(recyclerView: RecyclerView) : GestureDetector.SimpleOnGestureListener() {

    var mRecyclerViewListener: RecyclerViewClickListener? = null
        set

    val mRecyclerView = WeakReference<RecyclerView>(recyclerView)

    protected fun getChildAt(event: MotionEvent?): View? {
        if (event == null) {
            return null
        }
        return mRecyclerView.get()?.findChildViewUnder(event.x, event.y)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        val event = e ?: return false
        val recyclerView = mRecyclerView.get() ?: return false
        val childView = getChildAt(event) ?: return false
        val position = recyclerView.getChildAdapterPosition(childView)

        if (position == RecyclerView.NO_POSITION) {
            return false
        }
        var items: List<Any>? = null
        if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
            items = (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).mDataSet
        }

        if (items == null) {
            return false
        }

        if (mRecyclerViewListener != null) {
            mRecyclerViewListener!!.onItemClick(childView, items.get(position))
            return true
        }
        return false
    }
}