package com.blackbelt.careemkotlin.bindable

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.v7.widget.RecyclerView


private val KEY_ITEMS = -1024

@SuppressWarnings("unchecked")
@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<Any>) {
    recyclerView.setTag(KEY_ITEMS, items)
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).setDataSet(items)
    }
}

@SuppressWarnings("unchecked")
@BindingAdapter("itemViewBinder")
fun setItemViewBinder(recyclerView: RecyclerView, templates: Map<Class<*>, AndroidBaseItemBinder>) {
    val tmpItems: Collection<Any>? = recyclerView.getTag(KEY_ITEMS) as Collection<Any>?
    val items: ArrayList<Any>
    items = if (tmpItems != null) {
        java.util.ArrayList(tmpItems)
    } else {
        ArrayList()
    }
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        setItems(recyclerView, items)
        return
    }
    val adapter = AndroidBindableRecyclerViewAdapter(templates);
    recyclerView.isNestedScrollingEnabled = true
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter
    setItems(recyclerView, items);
}

@BindingAdapter("itemClickListener")
fun setItemClickListener(recyclerView: RecyclerView, listener: RecyclerViewClickListener) {
    if (recyclerView is ClickableRecyclerView) {
        recyclerView.mRecyclerViewClickListener = listener;
    }
}

@BindingAdapter("pageDescriptor")
fun setPageDescriptor(recyclerView: ClickableRecyclerView, pageDescriptor: PageDescriptor) {
    recyclerView.setPageDescriptor(pageDescriptor)
}

@InverseBindingAdapter(attribute = "pageDescriptor")
fun getPageDescriptor(recyclerView: ClickableRecyclerView): PageDescriptor? {
    return recyclerView.getPageDescriptor()
}

@BindingAdapter(value = *arrayOf("pageDescriptorAttrChanged"), requireAll = false)
fun setListeners(view: ClickableRecyclerView,
                 inverseBindingListener: InverseBindingListener?) {
    if (inverseBindingListener == null) {
        view.setOnPageChangeListener(null)
    } else {
        if (view.getOnPageChangeListener() == null) {
            view.setOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageChanged(recyclerView: ClickableRecyclerView, pageDescriptor: PageDescriptor) {
                    inverseBindingListener.onChange()
                }
            })
            inverseBindingListener.onChange()
        }
    }
}
