package com.android.angole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityVerifyEmailBinding
import com.android.angole.viewmodels.UserViewModel
import com.android.angole.views.WelcomeActivity

class VerifyEmailActivity : AppCompatActivity() {
    private var binding: ActivityVerifyEmailBinding? = null
    private var from: String? = null
    private var userViewModel: UserViewModel? = null
    private var reqId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        from = intent.getStringExtra("from")
        reqId = intent.getStringExtra("reqId")

        if (from == "Register"){
            binding?.flipper?.showNext()
        }

        binding?.btnVerify?.setOnClickListener {
            val otp = binding?.etOtp?.text
            if (otp?.isEmpty()!!){
                binding?.etOtp?.error = "Enter valid otp"
            }else{
                binding?.btnNext?.isEnabled = false
                binding?.btnNext?.background = ContextCompat.getDrawable(this, R.color.gray)
                binding?.progressBarOtp?.visibility = View.VISIBLE
                verifyOtp()
            }
        }

        binding?.btnNext?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            if (email.isEmpty()){
                binding?.etEmail?.error = "Email is required"
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding?.etEmail?.error = "Email is not valid"
            }else{
                binding?.btnNext?.isEnabled = false
                binding?.btnNext?.background = ContextCompat.getDrawable(this, R.color.gray)
                binding?.progressBarEmail?.visibility = View.VISIBLE
                sendOtp()
            }
        }

        binding?.btnContinue?.setOnClickListener {
            val password = binding?.etPassword?.text.toString()
            if (password.isEmpty()){
                binding?.etPassword?.error = "Password is required"
            }else{
                binding?.btnContinue?.isEnabled = false
                binding?.btnContinue?.background = ContextCompat.getDrawable(this, R.color.gray)
                binding?.progressBarPassword?.visibility = View.VISIBLE
                changePassword()
            }
        }
    }

    private fun sendOtp(){
        val email = binding?.etEmail?.text.toString()

        val sendDetails = HashMap<String, String>()
        sendDetails["email"] = email

        userViewModel?.sendOtp(sendDetails)

        userViewModel?.sendOtpData?.observe(this){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    val message = it.message
                    if (subCode == 200) {
                        reqId = it.data.items
                        binding?.flipper?.showNext()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                binding?.btnNext?.isEnabled = true
                binding?.btnNext?.background = ContextCompat.getDrawable(this, R.drawable.button_main_background)
                binding?.progressBarEmail?.visibility = View.GONE
            }
        }
    }

    private fun verifyOtp() {
        val otp = binding?.etOtp?.text.toString()
        val verifyData = HashMap<String, String>()
        verifyData["reqId"] = reqId!!
        verifyData["otp"] = otp

        userViewModel?.verifyOtp(verifyData)
        userViewModel?.verifyData?.observe(this){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    val message = it.message
                    if (subCode == 200) {
                        token = it.data.items
                        binding?.progressBarOtp?.visibility = View.GONE

                        if (from == "Register") {
                            val authConfig = AuthConfig(this)
                            authConfig.setProfile(token!!, true)

                            val intent = Intent(this, WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }else{
                            binding?.flipper?.showNext()
                        }
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                binding?.btnContinue?.isEnabled = true
                binding?.btnContinue?.background = ContextCompat.getDrawable(this, R.drawable.button_main_background)
                binding?.progressBarPassword?.visibility = View.GONE
            }
        }
    }

    private fun changePassword(){
        val authToken = "Bearer $token"

        val password = binding?.etPassword?.text.toString()
        val passwordDetails = HashMap<String, String>()
        passwordDetails["newPassword"] = password

        userViewModel?.resetPasswordBeforeLogin(authToken, passwordDetails)
        userViewModel?.resetPasswordData?.observe(this){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    val message = it.message
                    if (subCode == 200) {
                        val authConfig = AuthConfig(this)
                        authConfig.setProfile(token!!, true)

                        val intent = Intent(this, WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                binding?.btnContinue?.isEnabled = true
                binding?.btnContinue?.background = ContextCompat.getDrawable(this, R.drawable.button_main_background)
                binding?.progressBarPassword?.visibility = View.GONE
            }
        }
    }
}