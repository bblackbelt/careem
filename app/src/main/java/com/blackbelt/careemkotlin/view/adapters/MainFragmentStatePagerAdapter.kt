package com.blackbelt.careemkotlin.view.adapters


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.blackbelt.careemkotlin.view.adapters.model.FragmentModel

class MainFragmentStatePagerAdapter(activity: AppCompatActivity, private val mDataSet: List<FragmentModel>)
    : FragmentPagerAdapter(activity.supportFragmentManager) {

    private var mActivity = activity

    private val mFragmentCache: SparseArray<Fragment> = SparseArray()

    override fun getItem(position: Int): Fragment? {
        if (mFragmentCache.get(position) == null) {
            val (_, fragment1, bundle) = mDataSet[position]
            val fragment = Fragment.instantiate(mActivity,
                    fragment1.name, bundle)
            mFragmentCache.put(position, fragment)
        }
        return mFragmentCache.get(position)
    }

    override fun getCount(): Int {
        return mDataSet.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mDataSet[position].title
    }
}