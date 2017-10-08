package com.blackbelt.careemkotlin.bindable

import android.content.Context
import android.databinding.InverseBindingMethod
import android.databinding.InverseBindingMethods
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.MotionEvent

@InverseBindingMethods(value = InverseBindingMethod(type = PageDescriptor::class, attribute = "pageDescriptor"))
class ClickableRecyclerView(context: Context, attributes: AttributeSet) : RecyclerView(context, attributes), RecyclerView.OnItemTouchListener {

    var mRecyclerViewClickListener: RecyclerViewClickListener? = null
        set(value) {
            field = value
            mRecyclerViewGestureListener.mRecyclerViewListener = field
        }

    private class PageScrollListener(pageDescriptor: PageDescriptor = PageDescriptor()) : RecyclerView.OnScrollListener() {

        private var mVisiblePosition: IntArray? = null

        var mOnPageChageListener: OnPageChangeListener? = null

        var mPageDescriptor = pageDescriptor

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            val layoutManager = recyclerView?.layoutManager ?: return
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = getLastVisibleItemPosition(layoutManager)
            if ((totalItemCount - lastVisibleItem) <= mPageDescriptor.mThreshold) {
                if (mPageDescriptor.mCurrentPage < 1 + (totalItemCount / mPageDescriptor.mPageSize)) {
                    mPageDescriptor.mCurrentPage = (1 + (totalItemCount / mPageDescriptor.mPageSize))
                    updatePageChange(recyclerView as ClickableRecyclerView, mPageDescriptor)
                }
            }
        }

        fun updatePageChange(recyclerView: ClickableRecyclerView, pageDescriptor: PageDescriptor) {
            mOnPageChageListener?.onPageChanged(recyclerView, pageDescriptor)
        }

        private fun getLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager): Int {
            if (layoutManager is LinearLayoutManager) {
                return layoutManager.findLastCompletelyVisibleItemPosition()
            } else if (layoutManager is StaggeredGridLayoutManager) {
                if (mVisiblePosition == null) {
                    mVisiblePosition = IntArray(layoutManager.spanCount)
                }
                return layoutManager
                        .findLastCompletelyVisibleItemPositions(mVisiblePosition)[0]
            }
            return 0
        }
    }

    private var mOnPageChangeListener: OnPageChangeListener? = null

    private var mPageScrollListener: PageScrollListener? = null

    val mRecyclerViewGestureListener = RecyclerViewGestureListener(this)

    val mGestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, mRecyclerViewGestureListener)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnItemTouchListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnItemTouchListener(this)
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    fun setPageDescriptor(pageDescriptor: PageDescriptor) {
        if (mPageScrollListener != null) {
            return
        }
        mPageScrollListener = PageScrollListener(pageDescriptor)
        addOnScrollListener(mPageScrollListener)
    }

    fun getPageDescriptor(): PageDescriptor? {
        return mPageScrollListener?.mPageDescriptor
    }

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener?) {
        mPageScrollListener?.mOnPageChageListener = onPageChangeListener
    }

    fun getOnPageChangeListener(): OnPageChangeListener? {
        return mPageScrollListener?.mOnPageChageListener
    }
}