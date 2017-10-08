package com.blackbelt.careemkotlin.util

import android.support.v7.util.DiffUtil

class ItemSourceDiffCallback(private val mOldList: List<Any>?, private val mNewList: List<Any>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return mNewList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return !(mOldList == null || mNewList == null) && mOldList[oldItemPosition] == mNewList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return !(mOldList == null || mNewList == null) && mOldList[oldItemPosition] == mNewList[newItemPosition]
    }
}