package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityChangeDetailsBinding
import com.android.angole.viewmodels.UserViewModel

class ChangeDetailsActivity : AppCompatActivity() {
    private var binding: ActivityChangeDetailsBinding? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.ibBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnSave?.setOnClickListener {
            if (validateInput()){
                resetPassword()
            }
        }
    }

    private fun resetPassword(){
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val authConfig = AuthConfig(this)
        val authToken = "Bearer " + authConfig.getToken()
        val passwordDetails = HashMap<String, String>()
        passwordDetails["oldPassword"] = binding?.etCurrentPassword?.text.toString()
        passwordDetails["newPassword"] = binding?.etNewPassword?.text.toString()

        userViewModel?.resetPasswordAfterLogin(authToken, passwordDetails)
        userViewModel?.passwordData?.observe(this){
            it?.let {
                if (it.data != null) {
                    val message = it.message
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                } else {
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateInput(): Boolean{
        var isValid = true
        val oldPassword = binding?.etCurrentPassword?.text.toString()
        val newPassword = binding?.etNewPassword?.text.toString()
        val confirmNewPassword = binding?.etConfirmNewPassword?.text.toString()

        if (oldPassword.isEmpty()){
            isValid = false
            binding?.etCurrentPassword?.error = "Password is required"
        }

        if (newPassword.isNullOrEmpty()){
            isValid = false
            binding?.etNewPassword?.error = "Password is required"
        }else if (newPassword != confirmNewPassword){
            isValid = false
            binding?.etNewPassword?.error = "Password should match"
        }

        if (confirmNewPassword.isNullOrEmpty()){
            isValid = false
            binding?.etConfirmNewPassword?.error = "Confirm Password is required"
        }else if (confirmNewPassword != newPassword){
            isValid = false
            binding?.etConfirmNewPassword?.error = "Password should match"
        }

        return isValid
    }
}