package com.blackbelt.careemkotlin.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun restoreList(listOfString: String): List<String> {
        return Gson().fromJson(listOfString, object : TypeToken<List<String>>() {

        }.type)
    }

    @TypeConverter
    fun saveList(listOfString: List<String>): String {
        return Gson().toJson(listOfString)
    }
}
