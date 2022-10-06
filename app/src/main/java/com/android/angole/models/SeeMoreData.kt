package com.android.angole.models

data class SeeMoreData(
    val status: Boolean,
    val subCode: Int,
    val message: String,
    val items: SeeMoreItems?
)

data class SeeMoreItems(
    val data: List<CategoryItems>?,
    val totalPage: Int?
)
