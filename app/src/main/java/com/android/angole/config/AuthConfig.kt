package com.android.angole.config

import android.content.Context

class AuthConfig(val context: Context) {
    fun setProfile(token: String, isLogin: Boolean){
        val preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("token", token)
        editor.putBoolean("isLogin", isLogin)
        editor.apply()
    }

    fun getToken(): String?{
        val preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        return preferences.getString("token", "")
    }

    fun getIsLogin(): Boolean{
        val preferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        return preferences.getBoolean("isLogin", false)
    }
}