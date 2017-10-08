package com.blackbelt.careemkotlin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blackbelt.careemkotlin.api.model.Movie
import com.blackbelt.careemkotlin.api.model.TvShow
import io.reactivex.Flowable

@Dao
interface TvShowsDao {

    @Query("SELECT * FROM TvShow")
    fun getAll(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(movies: List<TvShow>)

    @Query("DELETE FROM TvShow")
    fun deleteAll()
}