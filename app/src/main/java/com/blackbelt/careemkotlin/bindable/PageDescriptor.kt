package com.blackbelt.careemkotlin.bindable

class PageDescriptor constructor(startPage: Int = 1, pageSize: Int = 20, threshold: Int = 5) {

    val mStartPage = startPage

    val mPageSize = pageSize

    val mThreshold = threshold

    var mCurrentPage = startPage

}