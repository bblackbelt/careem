package com.blackbelt.careemkotlin.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blackbelt.careemkotlin.api.model.TvShowDetails
import io.reactivex.Single

@Dao
interface TvShowDetailsDao {

    @Query("SELECT * FROM TvShowDetails where id = :arg0")
    fun get(tvShowId: Int): Single<TvShowDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: TvShowDetails)

    @Query("DELETE FROM TvShowDetails")
    fun deleteAll()
}