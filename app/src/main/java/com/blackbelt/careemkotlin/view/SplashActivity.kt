package com.blackbelt.careemkotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.blackbelt.careemkotlin.R
import com.blackbelt.careemkotlin.api.model.Configuration
import com.blackbelt.careemkotlin.database.MoviesDatabase
import com.blackbelt.careemkotlin.pictures.IConfigurationManager
import com.blackbelt.careemkotlin.view.misc.BaseInjectableActivity
import com.blackbelt.careemkotlin.view.misc.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SplashActivity : BaseInjectableActivity() {

    @Inject
    lateinit var mConfigurationManager: IConfigurationManager

    @Inject
    lateinit var mMovieDatabase: MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mConfigurationManager.getConfigurationObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ conf: Configuration ->
                    if (conf == Configuration.EMPTY) {
                        this@SplashActivity.findViewById<View>(R.id.progress_bar).visibility = View.GONE
                        this@SplashActivity.findViewById<View>(R.id.retry).visibility = View.VISIBLE
                        Toast.makeText(this@SplashActivity, R.string.oops_something_went_wrong, Toast.LENGTH_SHORT).show()
                        return@subscribe
                    }
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, { Toast.makeText(this@SplashActivity, R.string.oops_something_went_wrong, Toast.LENGTH_SHORT).show() })
    }
}