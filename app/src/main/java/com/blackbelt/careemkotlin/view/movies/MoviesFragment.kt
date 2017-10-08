package com.blackbelt.careemkotlin.view.movies

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.movies.MoviePage
import com.blackbelt.careemkotlin.view.misc.BaseInjectableFragment
import com.blackbelt.careemkotlin.view.misc.MOVIE_PAGE_KEY
import com.blackbelt.careemkotlin.view.movies.viewmodel.MoviesViewModel
import java.util.*
import javax.inject.Inject


class MoviesFragment : BaseInjectableFragment() {

    @Inject
    lateinit var mMoviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMoviesViewModel.moviePage =
                MoviePage.valueOf(arguments.getString(MOVIE_PAGE_KEY))
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onCreateBindableView(inflater, container, savedInstanceState, R.layout.fragment_movies, mMoviesViewModel)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movies_rv) ?: return
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (mMoviesViewModel.isProgressLoader(position)) {
                    return 3
                }
                return 1
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_date, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun showYearsDialog() {
        val datePicker = DatePickerDialog(activity,
                DatePickerDialog.OnDateSetListener { _, year, month, day -> mMoviesViewModel.updateDate(year, month, day) },
                mMoviesViewModel.mYear,
                mMoviesViewModel.mMonth,
                mMoviesViewModel.mDay);
        datePicker.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePicker.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_date) {
            showYearsDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}