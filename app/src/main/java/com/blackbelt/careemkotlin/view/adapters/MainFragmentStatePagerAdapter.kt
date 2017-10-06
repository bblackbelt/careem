package com.blackbelt.careemkotlin.view.adapters


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.blackbelt.careemkotlin.view.adapters.model.FragmentModel
import java.lang.ref.WeakReference

class MainFragmentStatePagerAdapter(activity: AppCompatActivity, private val mDataSet: List<FragmentModel>)
    : FragmentPagerAdapter(activity.supportFragmentManager) {

    private var mActivity = activity

    private val mFragmentCache: SparseArray<WeakReference<Fragment>> = SparseArray()

    override fun getItem(position: Int): Fragment? {
        val fragment: Fragment
        if (mFragmentCache.get(position) == null
                || mFragmentCache.get(position).get() == null) {
            val (_, fragment1, bundle) = mDataSet[position]
            fragment = Fragment.instantiate(mActivity,
                    fragment1.name, bundle)
            mFragmentCache.put(position, WeakReference(fragment))
        }
        return mFragmentCache.get(position).get()
    }

    override fun getCount(): Int {
        return mDataSet.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mDataSet[position].title
    }
}