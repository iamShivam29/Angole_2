package com.android.angole.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>): Resource<T>{
        return withContext(Dispatchers.IO){
            try {
                val response = call()
                if (response.isSuccessful){
                    Resource.Success(response.body()!!)
                }else{
                    Resource.Error("Something went wrong")
                }
            }catch (e: SocketTimeoutException){
                Resource.Error("Time out")
            }catch (e: IOException){
                Resource.Error("Please check your internet connection")
            }catch (e: Exception){
                Resource.Error("${e.localizedMessage}")
            }
        }
    }
}