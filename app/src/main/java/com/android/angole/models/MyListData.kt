package com.android.angole.models

data class MyListData(
    val status: Boolean,
    val subCode: Int,
    val message: String,
    val items: List<CategoryItems>?
)