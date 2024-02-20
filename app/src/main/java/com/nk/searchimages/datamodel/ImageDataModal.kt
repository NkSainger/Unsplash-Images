package com.nk.searchimages.datamodel

data class ImageDataModal(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)