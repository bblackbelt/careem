package com.blackbelt.careemkotlin.api.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Configuration(@PrimaryKey(autoGenerate = true) var key: Int = 0,
                         @Embedded @Expose @SerializedName("images") var imagesConfiguration: ImageConfiguration = ImageConfiguration.EMPTY) {
    companion object {
        val EMPTY = Configuration()
    }
}

data class ImageConfiguration(@Expose @SerializedName("base_url") var baseUrl: String = "",
                              @Expose @SerializedName("secure_base_url") var secureBaseUrl: String = "",
                              @Expose @SerializedName("backdrop_sizes") var backdropSizes: List<String> = ArrayList(),
                              @Expose @SerializedName("logo_sizes") var logoSizes: List<String> = ArrayList(),
                              @Expose @SerializedName("poster_sizes") var posterSizes: List<String> = ArrayList(),
                              @Expose @SerializedName("profile_sizes") var profileSizes: List<String> = ArrayList(),
                              @Expose @SerializedName("still_sizes") var stillSizes: List<String> = ArrayList()) {
    companion object {
        val EMPTY = ImageConfiguration()
    }
}