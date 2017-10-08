package com.blackbelt.careemkotlin.view.details.viewmodel

import com.blackbelt.careemkotlin.api.model.Image
import com.blackbelt.careemkotlin.pictures.IPictureManager
import com.blackbelt.careemkotlin.view.misc.viewmodel.BaseMovieViewModel

class ImageViewModel(image : Image, pictureManager: IPictureManager) : BaseMovieViewModel() {

    var mImage = image
    val mPictureManager = pictureManager

    fun getImageUrl() : String {
        return mPictureManager.getBackdropUrl(mImage.filePath)
    }
}