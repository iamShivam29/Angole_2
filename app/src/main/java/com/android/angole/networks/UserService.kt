package com.android.angole.networks

import com.android.angole.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("user/login/")
    suspend fun doLogin(@Body userDetails: HashMap<String, String>): Response<LoginData>

    @POST("user/change/password")
    suspend fun changePassword(@Header("Authorization") authToken: String, @Body passwordDetails: HashMap<String, String>)

    @POST("user/register/")
    suspend fun registerUser(@Body registerDetails: HashMap<String, String>): Response<RegisterData>

    @POST("user/otp/send/")
    suspend fun sendOtp(@Body requestOtpDetails: HashMap<String, String>): Response<RegisterData>

    @POST("user/otp/verify/")
    suspend fun verifyOtp(@Body verifyOtp: HashMap<String, String>): Response<VerifyData>

    @POST("user/change/password/")
    suspend fun resetPasswordBeforeLogin(@Header("Authorization") authToken: String, @Body passwordDetails: HashMap<String, String>): Response<VerifyData>

    @POST("user/change/password/")
    suspend fun resetPasswordAfterLogin(@Header("Authorization") authToken: String, @Body passwordDetails: HashMap<String, String>): Response<VerifyData>

    @GET("image/avatar/")
    suspend fun getAvatars(): Response<AvatarData>
}