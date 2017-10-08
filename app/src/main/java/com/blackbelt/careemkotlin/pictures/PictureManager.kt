package com.blackbelt.careemkotlin.pictures

import android.net.Uri
import android.text.TextUtils

class PictureManager(configurationManager: IConfigurationManager) : IPictureManager {

    val mConfigurationManager = configurationManager;

    override fun getPosterUrl(path: String?): String {
        return getImageUrlFrom(path, mConfigurationManager.getMaxPosterBucket())
    }

    override fun getPosterUrl(path: String?, size: Int): String {
        return getImageUrlFrom(path, mConfigurationManager.getPosterBucket(size))
    }

    override fun getBackdropUrl(path: String?): String {
        return getImageUrlFrom(path, mConfigurationManager.getMaxBackdropBucket())
    }

    override fun getBackdropUrl(path: String?, size: Int): String {
        return getImageUrlFrom(path, mConfigurationManager.getBackdropBucket(size))
    }

    private fun getImageUrlFrom(path: String?, bucketSize: String): String {
        if (TextUtils.isEmpty(path)) {
            return ""
        }
        return Uri.parse(mConfigurationManager.getBaseUrl())
                .buildUpon()
                .appendEncodedPath(bucketSize)
                .appendEncodedPath(path)
                .toString()
    }

}