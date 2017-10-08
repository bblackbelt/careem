package com.blackbelt.careemkotlin.pictures

interface IPictureManager {
    fun getPosterUrl(path: String?): String

    fun getPosterUrl(path: String?, size: Int): String

    fun getBackdropUrl(path: String?): String

    fun getBackdropUrl(path: String?, size: Int): String
}