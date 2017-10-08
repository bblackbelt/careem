package com.blackbelt.careemkotlin.bindable

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.calculateDiff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blackbelt.careemkotlin.util.ItemSourceDiffCallback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.util.*


class AndroidBindableRecyclerViewAdapter(templates: Map<Class<*>, AndroidBaseItemBinder>) : RecyclerView.Adapter<AndroidBindableRecyclerViewAdapter.BindableViewHolder>() {

    var mDataSet: ObservableList<Any> = ObservableArrayList<Any>()

    var mTemplatesForObjects: Map<Class<*>, AndroidBaseItemBinder>? = templates

    var mSetValuesDisposable = Disposables.disposed()

    private val pendingUpdates = ArrayDeque<List<Any>>()

    class BindableViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val mViewDataBinding: ViewDataBinding = binding;
    }

    override fun onBindViewHolder(holder: BindableViewHolder?, position: Int) {
        val item: Any = mDataSet[position]
        val clazz = item.javaClass
        val itemBinder: AndroidBaseItemBinder = mTemplatesForObjects?.get(clazz) ?: throw RuntimeException()
        holder?.mViewDataBinding?.setVariable(itemBinder.brVariable, item)
        holder?.mViewDataBinding?.executePendingBindings()
    }

    fun setDataSet(value: List<Any>) {
        val oldItems = ArrayList(mDataSet)
        mSetValuesDisposable.dispose()
        mSetValuesDisposable = Observable.just(value)
                .subscribeOn(Schedulers.computation())
                .map<Pair<List<*>, DiffUtil.DiffResult>> { newList -> Pair(newList, calculateDiff(ItemSourceDiffCallback(oldItems, value))) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resultPair -> applyDiffResult(resultPair) }) { throwable -> throwable.printStackTrace() }
    }

    private fun applyDiffResult(resultPair: Pair<List<*>, DiffUtil.DiffResult>) {
        var firstStart = true

        if (!pendingUpdates.isEmpty()) {
            pendingUpdates.remove()
        }

        if (mDataSet.size > 0) {
            mDataSet.clear()
            firstStart = false
        }

        if (resultPair.first != null) {
            mDataSet.addAll(java.util.ArrayList<Any>(resultPair.first))
        }

        //if we call DiffUtil.DiffResult.dispatchUpdatesTo() on an empty adapter, it will crash - we have to call notifyDataSetChanged()!
        if (firstStart) {
            notifyDataSetChanged()
        } else {
            resultPair.second.dispatchUpdatesTo(this)
        }

        if (pendingUpdates.size > 0) {
            setDataSet(pendingUpdates.peek())
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindableViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent?.context),
                viewType, parent, false)
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val clazz = mDataSet[position].javaClass
        val itemBinder: AndroidBaseItemBinder = mTemplatesForObjects?.get(clazz) ?: throw RuntimeException()
        return itemBinder.layoutId
    }

    override fun getItemCount(): Int = mDataSet.size
}