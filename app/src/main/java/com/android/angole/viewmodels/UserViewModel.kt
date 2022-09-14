package com.android.angole.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.angole.models.LoginData
import com.android.angole.models.RegisterData
import com.android.angole.models.RegisterDetails
import com.android.angole.models.VerifyData
import com.android.angole.repositories.UserRepo
import com.android.angole.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    var loginData = MutableLiveData<Resource<LoginData>>()
    var registerData = MutableLiveData<Resource<RegisterData>>()
    var sendOtpData = MutableLiveData<Resource<RegisterData>>()
    var verifyData = MutableLiveData<Resource<VerifyData>>()
    var resetPasswordData = MutableLiveData<Resource<VerifyData>>()

    fun doLogin(userDetails: HashMap<String, String>){
        viewModelScope.launch {
            loginData.value = UserRepo().doLogin(userDetails)
        }
    }

    fun registerUser(registerDetails: HashMap<String, String>){
        viewModelScope.launch {
            registerData.value = UserRepo().registerUser(registerDetails)
        }
    }

    fun sendOtp(requestOtpDetails: HashMap<String, String>){
        viewModelScope.launch {
            sendOtpData.value = UserRepo().sendOtp(requestOtpDetails)
        }
    }

    fun verifyOtp(verifyOtp: HashMap<String, String>){
        viewModelScope.launch {
            verifyData.value = UserRepo().verifyOtp(verifyOtp)
        }
    }

    fun resetPasswordBeforeLogin(authToken: String, passwordDetails: HashMap<String, String>){
        viewModelScope.launch {
            resetPasswordData.value = UserRepo().resetPasswordBeforeLogin(authToken, passwordDetails)
        }
    }
}