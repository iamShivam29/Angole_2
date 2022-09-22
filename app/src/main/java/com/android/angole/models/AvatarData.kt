package com.android.angole.models

data class AvatarData (
    val status: Boolean,
    val subCode: Int,
    val message: String,
    val items: List<AvatarItems>?
)

data class AvatarItems(
    val _id: String?,
    val url: String?
)