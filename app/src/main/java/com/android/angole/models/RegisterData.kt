package com.android.angole.models

data class RegisterData (
    var subCode: Int,
    var message: String,
    var items: RegisterDetails?
)

data class RegisterDetails(
    var reqId: String
)