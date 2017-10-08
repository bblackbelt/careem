package com.blackbelt.careemkotlin.view.details

import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.TextView
import com.blackbelt.careemkotlin.BR
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.view.details.viewmodel.MovieDetailsViewModel
import com.blackbelt.careemkotlin.view.misc.BaseBindableActivity
import javax.inject.Inject

class MovieDetailsActivity : BaseBindableActivity() {

    companion object {
        val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        val MOVIE_ID_PAGE = "MOVIE_ID_PAGE"
        val MOVIE_TITLE = "MOVIE_TITLE"
    }

    @Inject
    lateinit var mMovieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(BR.movieDetailsViewModel, mMovieDetailsViewModel, R.layout.activity_movie_details)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<RecyclerView>(R.id.images_gallery_rv)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        PagerSnapHelper().attachToRecyclerView(recyclerView)

        val imageCounter = findViewById<TextView>(R.id.image_indicator)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.onDrawOver(c, parent, state)
                val itemCount = layoutManager.itemCount
                if (itemCount == 0) {
                    return
                }
                val first = layoutManager.findFirstVisibleItemPosition() + 1
                val indicator = "$first / $itemCount"

                imageCounter.text = indicator
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
