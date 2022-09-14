package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.VerifyEmailActivity
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivitySignUpBinding
import com.android.angole.viewmodels.UserViewModel

class SignUpActivity : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.btnSignUp?.setOnClickListener {
            if(validateInput()){
                doRegister()
            }
        }

        binding?.btnLogin?.setOnClickListener {
            finish()
        }
    }

    private fun doRegister(){
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val registerDetails = HashMap<String, String>()

        registerDetails["username"] = binding?.etUserName?.text.toString()
        registerDetails["fullName"] = binding?.etFirstName?.text.toString()
        registerDetails["phone"] = binding?.etPhoneNumber?.text.toString()
        registerDetails["email"] = binding?.etEmail?.text.toString()
        registerDetails["password"] = binding?.etPassword?.text.toString()

        userViewModel?.registerUser(registerDetails)
        userViewModel?.registerData?.observe(this){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    val message = it.message
                    if (subCode == 201) {
                        val reqId = it.data.items?.reqId

                        val intent = Intent(this, VerifyEmailActivity::class.java)
                        intent.putExtra("from", "Register")
                        intent.putExtra("reqId", reqId)
                        startActivity(intent)
                        finish()
                    } else {
                        if (message == "email and username already exists" || message == "username already exists" || message == "email already exists"){
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

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

        val userName = binding?.etUserName?.text.toString()
        val firstName = binding?.etFirstName?.text.toString()
        val phoneNumber = binding?.etPhoneNumber?.text.toString()
        val emailAddress = binding?.etEmail?.text.toString()
        val password = binding?.etPassword?.text.toString()
        val confirmPassword = binding?.etConfirmPassword?.text.toString()

        Log.i("SignUp", "Password +$password+ and confirmPassword +$confirmPassword+")

        if (userName.isNullOrEmpty()){
            isValid = false
            binding?.etUserName?.error = "User is required"
        }

        if (firstName.isNullOrEmpty()){
            isValid = false
            binding?.etUserName?.error = "First Name is required"
        }

        if (phoneNumber.isNullOrEmpty()){
            isValid = false
            binding?.etUserName?.error = "Phone Number is required"
        }

        if (emailAddress.isNullOrEmpty()){
            isValid = false
            binding?.etUserName?.error = "Email is required"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            binding?.etEmail?.error = "Email is not valid"
            isValid = false
        }

        if (password.isNullOrEmpty()){
            isValid = false
            binding?.etPassword?.error = "Password is required"
        }else if (password != confirmPassword){
            isValid = false
            binding?.etPassword?.error = "Password should match"
        }

        if (confirmPassword.isNullOrEmpty()){
            isValid = false
            binding?.etConfirmPassword?.error = "Confirm Password is required"
        }else if (confirmPassword != password){
            isValid = false
            binding?.etConfirmPassword?.error = "Password should match"
        }

        return isValid
    }
}