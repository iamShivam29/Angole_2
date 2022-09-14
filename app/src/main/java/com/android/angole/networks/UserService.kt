package com.android.angole.networks

import com.android.angole.models.LoginData
import com.android.angole.models.RegisterData
import com.android.angole.models.VerifyData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("login/")
    suspend fun doLogin(@Body userDetails: HashMap<String, String>): Response<LoginData>

    @POST("change/password")
    suspend fun changePassword(@Header("Authorization") authToken: String, @Body passwordDetails: HashMap<String, String>)

    @POST("register/")
    suspend fun registerUser(@Body registerDetails: HashMap<String, String>): Response<RegisterData>

    @POST("otp/send/")
    suspend fun sendOtp(@Body requestOtpDetails: HashMap<String, String>): Response<RegisterData>

    @POST("otp/verify/")
    suspend fun verifyOtp(@Body verifyOtp: HashMap<String, String>): Response<VerifyData>

    @POST("change/password/")
    suspend fun resetPasswordBeforeLogin(@Header("Authorization") authToken: String, @Body passwordDetails: HashMap<String, String>): Response<VerifyData>
}