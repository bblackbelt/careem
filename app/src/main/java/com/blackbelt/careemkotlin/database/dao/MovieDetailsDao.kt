package com.blackbelt.careemkotlin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blackbelt.careemkotlin.api.model.MovieDetails
import io.reactivex.Single

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM MovieDetails where id = :arg0")
    fun get(movieId: Int): Single<MovieDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movies: MovieDetails)

    @Query("DELETE FROM MovieDetails")
    fun deleteAll()
}