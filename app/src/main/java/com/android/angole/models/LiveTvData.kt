package com.android.angole.models

data class LiveTvData(
    val subCode: Int,
    val message: String,
    var items:List<CategoryData>?
)
