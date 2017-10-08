package com.blackbelt.careemkotlin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blackbelt.careemkotlin.api.model.Movie
import io.reactivex.Flowable

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(movies: List<Movie>)

    @Query("DELETE FROM Movie")
    fun deleteAll()
}