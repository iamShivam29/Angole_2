package com.android.angole.repositories

import com.android.angole.models.LoginData
import com.android.angole.models.RegisterData
import com.android.angole.models.VerifyData
import com.android.angole.networks.RetrofitBuilder
import com.android.angole.utils.Resource
import com.android.angole.utils.SafeApiRequest
import retrofit2.http.Body

class UserRepo: SafeApiRequest() {
    suspend fun doLogin(userDetails: HashMap<String, String>): Resource<LoginData>{
        return apiRequest { RetrofitBuilder().userService().doLogin(userDetails) }
    }

    suspend fun registerUser(registerDetails: HashMap<String, String>): Resource<RegisterData>{
        return apiRequest { RetrofitBuilder().userService().registerUser(registerDetails) }
    }

    suspend fun sendOtp(requestOtpDetails: HashMap<String, String>): Resource<RegisterData>{
        return apiRequest { RetrofitBuilder().userService().sendOtp(requestOtpDetails) }
    }

    suspend fun verifyOtp(verifyOtp: HashMap<String, String>): Resource<VerifyData>{
        return apiRequest { RetrofitBuilder().userService().verifyOtp(verifyOtp) }
    }

    suspend fun resetPasswordBeforeLogin(authToken: String, passwordDetails: HashMap<String, String>): Resource<VerifyData>{
        return apiRequest { RetrofitBuilder().userService().resetPasswordBeforeLogin(authToken, passwordDetails) }
    }
}