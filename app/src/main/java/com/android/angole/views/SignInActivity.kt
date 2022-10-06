package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.VerifyEmailActivity
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivitySignInBinding
import com.android.angole.viewmodels.UserViewModel

class SignInActivity : AppCompatActivity() {
    private var binding: ActivitySignInBinding? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.btnSignIn?.setOnClickListener {
            if (validateInput()){
                it.isEnabled = false
                it.background = ContextCompat.getDrawable(this , R.color.gray)
                binding?.loadingProgress?.visibility = View.VISIBLE
                doLogin()
            }
        }

        binding?.btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding?.btnForget?.setOnClickListener {
            startActivity(Intent(this, VerifyEmailActivity::class.java))
        }
    }

    private fun validateInput(): Boolean{
        var isValid = true
        val userName = binding?.etUserName?.text.toString()
        val password = binding?.etPassword?.text.toString()

        if (userName.isEmpty()){
            binding?.etUserName?.error = "Username is required"
            isValid = false
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            binding?.etEmail?.error = "Email is not valid"
//            isValid = false
//        }

        if (password.isEmpty()){
            binding?.etPassword?.error = "Password is required"
            isValid = false
        }

        return isValid
    }

    private fun doLogin(){
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val username = binding?.etUserName?.text.toString()
        val password = binding?.etPassword?.text.toString()

        val userDetails = HashMap<String, String>()
        userDetails["username"] = username
        userDetails["password"] = password
        userViewModel?.doLogin(userDetails)

        userViewModel?.loginData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    val message = it.data.message
                    if (subCode == 200){
                        val loginItems = it.data.items
                        val token = loginItems?.token

                        val isVerified = it.data.items!!.isVerified
                        val reqId = it.data.items?.reqId
                        isVerified?.let {
                            if (isVerified) {
                                token?.let {tok ->
                                    val authConfig = AuthConfig(this)
                                    authConfig.setProfile(tok, true)
                                }

                                val intent = Intent(this, ProfileActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this, VerifyEmailActivity::class.java)
                                intent.putExtra("from", "Register")
                                intent.putExtra("reqId", reqId)
                                startActivity(intent)
                            }
                        }
                    }else{
                        if (message == "invalid credentials"){
                            Toast.makeText(this, "invalid details", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                binding?.btnSignIn?.isEnabled = true
                binding?.btnSignIn?.background = ContextCompat.getDrawable(this , R.drawable.button_main_background)
                binding?.loadingProgress?.visibility = View.GONE

                userViewModel?.loginData?.removeObservers(this)
                userViewModel?.loginData?.value = null
            }
        }
    }


}