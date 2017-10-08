package com.blackbelt.careemkotlin.database.dao

import android.arch.persistence.room.*
import com.blackbelt.careemkotlin.api.model.Configuration
import io.reactivex.Flowable

@Dao
interface ConfigurationDao {

    @get:Query("SELECT * FROM Configuration")
    val configuration: Flowable<List<Configuration>>

    @Update
    fun updateConfiguration(configuration: Configuration)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(configuration: Configuration)

    @Delete
    fun deleteConfiguration(configuration: Configuration)
}