package com.blackbelt.careemkotlin.view.misc

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.view.adapters.MainFragmentStatePagerAdapter
import com.blackbelt.careemkotlin.view.adapters.model.FragmentModel
import com.blackbelt.careemkotlin.view.movies.MoviesFragment
import java.util.*

const val MOVIE_PAGE_KEY = "MOVIE_PAGE_KEY"

class MainActivity : BaseInjectableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<ViewPager>(R.id.main_view_pager)

        val fragmentModel = ArrayList<FragmentModel>()
        var bundle = Bundle()
        bundle.putString(MOVIE_PAGE_KEY, MoviePage.Movie.name)
        fragmentModel.add(FragmentModel(resources.getString(R.string.movies),
                MoviesFragment::class.java, bundle))

        bundle = Bundle()
        bundle.putString(MOVIE_PAGE_KEY, MoviePage.TvShows.name)

        fragmentModel.add(FragmentModel(resources.getString(R.string.tvshows),
                MoviesFragment::class.java, bundle))

        viewPager.adapter = MainFragmentStatePagerAdapter(this, fragmentModel)

        val tabLayout = findViewById<TabLayout>(R.id.tablayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
