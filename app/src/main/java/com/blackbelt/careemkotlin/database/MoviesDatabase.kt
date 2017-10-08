package com.blackbelt.careemkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.blackbelt.careemkotlin.api.model.Configuration
import com.blackbelt.careemkotlin.database.dao.ConfigurationDao

@Database(version = 1, entities = arrayOf(Configuration::class))
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getConfigurationDao() : ConfigurationDao

}